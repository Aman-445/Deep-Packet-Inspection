package pipeline;

import java.util.concurrent.BlockingQueue;

import model.RawPacket;
import parser.PcapReader;

public class ReaderThread implements Runnable {

    private PcapReader reader;
    private BlockingQueue<RawPacket> queue;

    public ReaderThread(PcapReader reader, BlockingQueue<RawPacket> queue) {
        this.reader = reader;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RawPacket pkt = new RawPacket();

                if (!reader.readNextPacket(pkt)) break;

                RawPacket copy = new RawPacket();
                copy.data = pkt.data.clone();

                queue.put(copy);
            }

            // End signal
            RawPacket end = new RawPacket();
            end.data = null;
            queue.put(end);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}