package pipeline;

import java.util.concurrent.BlockingQueue;

import engine.DPIProcessor;
import model.ParsedPacket;
import model.RawPacket;
import parser.PacketParser;

public class ProcessorThread implements Runnable {

    private int totalPackets = 0;
    private int httpsPackets = 0;

    private BlockingQueue<RawPacket> inputQueue;
    private BlockingQueue<RawPacket> outputQueue;
    private PacketParser parser;
    private DPIProcessor processor;

    public ProcessorThread(BlockingQueue<RawPacket> in,
                           BlockingQueue<RawPacket> out,
                           PacketParser parser,
                           DPIProcessor processor) {
        this.inputQueue = in;
        this.outputQueue = out;
        this.parser = parser;
        this.processor = processor;
    }

    @Override
    public void run() {

        try {
            while (true) {
                RawPacket pkt = inputQueue.take();

                // End signal
                if (pkt == null || pkt.data == null) {
                    outputQueue.put(pkt);
                    break;
                }

                totalPackets++;

                ParsedPacket parsed = new ParsedPacket();

                if (!parser.parse(pkt, parsed)) {
                    continue;
                }

                if (parsed.dstPort == 443) {
                    httpsPackets++;
                }

                if (parsed.dstPort == 443 || parsed.dstPort == 80) {
                    System.out.println("Parsed packet → dstPort: " + parsed.dstPort);
                }

                if (processor.process(pkt, parsed)) {
                    outputQueue.put(pkt);
                }
            }

            // ✅ Print summary once
            System.out.println("Total packets processed: " + totalPackets);
            System.out.println("HTTPS packets: " + httpsPackets);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}