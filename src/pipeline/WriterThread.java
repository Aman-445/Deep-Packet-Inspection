package pipeline;

import parser.PcapWriter;
import model.RawPacket;

import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {

    private BlockingQueue<RawPacket> queue;
    private PcapWriter writer;

    public WriterThread(BlockingQueue<RawPacket> queue, PcapWriter writer) {
        this.queue = queue;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RawPacket pkt = queue.take();

                // end signal
                if (pkt == null || pkt.data == null) {
                    break;
                }

                writer.write(pkt);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}