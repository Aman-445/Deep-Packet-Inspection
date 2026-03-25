package parser;

import model.RawPacket;

import java.io.FileOutputStream;
import java.io.IOException;

public class PcapWriter {
    private FileOutputStream fos;

    public PcapWriter(String file, byte[] header) throws IOException {
        fos = new FileOutputStream(file);
        fos.write(header); // write global header
    }

    public void write(RawPacket pkt) throws IOException {

        if (pkt == null || pkt.data == null) return;

        byte[] header = new byte[16];
        int len = pkt.data.length;

        // timestamp seconds
        header[0] = (byte)(pkt.tsSec & 0xFF);
        header[1] = (byte)((pkt.tsSec >> 8) & 0xFF);
        header[2] = (byte)((pkt.tsSec >> 16) & 0xFF);
        header[3] = (byte)((pkt.tsSec >> 24) & 0xFF);

        // timestamp microseconds
        header[4] = (byte)(pkt.tsUsec & 0xFF);
        header[5] = (byte)((pkt.tsUsec >> 8) & 0xFF);
        header[6] = (byte)((pkt.tsUsec >> 16) & 0xFF);
        header[7] = (byte)((pkt.tsUsec >> 24) & 0xFF);

        // included length
        header[8]  = (byte)(len & 0xFF);
        header[9]  = (byte)((len >> 8) & 0xFF);
        header[10] = (byte)((len >> 16) & 0xFF);
        header[11] = (byte)((len >> 24) & 0xFF);

        // original length
        header[12] = header[8];
        header[13] = header[9];
        header[14] = header[10];
        header[15] = header[11];

        fos.write(header);
        fos.write(pkt.data);
    }

    public void close() throws IOException {
        if (fos != null) fos.close();
    }
}