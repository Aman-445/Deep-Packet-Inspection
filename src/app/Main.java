package app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.DPIProcessor;
import engine.RuleManager;
import model.AppType;
import model.RawPacket;
import parser.PacketParser;
import parser.PcapReader;
import parser.PcapWriter;
import pipeline.ProcessorThread;
import pipeline.ReaderThread;
import pipeline.WriterThread;

public class Main {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java app.Main <input.pcap> <output.pcap>");
            return;
        }

        String input = args[0];
        String output = args[1];

        try {
            System.out.println("Starting DPI Engine...");

            // ✅ Reader
            PcapReader reader = new PcapReader();
            reader.open(input);

            // ✅ Writer
            PcapWriter writer = new PcapWriter(output, reader.getGlobalHeader());

            // ✅ Core components (ORDER FIXED)
            PacketParser parser = new PacketParser();

            RuleManager rules = new RuleManager();
            rules.blockApp(AppType.YOUTUBE);

            DPIProcessor processor = new DPIProcessor(rules);

            // ✅ Bounded queues
            BlockingQueue<RawPacket> q1 = new LinkedBlockingQueue<>(1000);
            BlockingQueue<RawPacket> q2 = new LinkedBlockingQueue<>(1000);

            // ✅ Threads
            Thread readerThread = new Thread(new ReaderThread(reader, q1), "Reader");
            Thread processorThread = new Thread(new ProcessorThread(q1, q2, parser, processor), "Processor");
            Thread writerThread = new Thread(new WriterThread(q2, writer), "Writer");

            // ✅ Start
            readerThread.start();
            processorThread.start();
            writerThread.start();

            System.out.println("Threads started...");
            System.out.println("Waiting for threads...");

            // ✅ Wait
            readerThread.join();
            processorThread.join();
            writerThread.join();

            // ✅ Final summary
            System.out.println("Total flows: " + processor.getFlowCount());
            System.out.println("Processing completed.");

            // ✅ Close
            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}