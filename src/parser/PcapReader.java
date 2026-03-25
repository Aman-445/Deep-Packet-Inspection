package parser;

import java.io.FileInputStream;
import java.io.IOException;

import model.RawPacket;

public class PcapReader {

    private FileInputStream fis;
    private byte[] globalHeader = new byte[24];

    public void open(String file) throws IOException {
        fis = new FileInputStream(file);

        // Read global header (24 bytes)
        if (fis.read(globalHeader) < 24) {
            throw new IOException("Invalid PCAP file");
        }
    }

    public byte[] getGlobalHeader() {
        return globalHeader;
    }

    public boolean readNextPacket(RawPacket pkt) throws IOException {

        byte[] header = new byte[16];

        // Read packet header (16 bytes)
        int read = fis.read(header);
        if (read < 16) return false;

        // 🔥 FIX: Correct 4-byte little-endian length
        int len = ((header[8] & 0xFF)) |
                  ((header[9] & 0xFF) << 8) |
                  ((header[10] & 0xFF) << 16) |
                  ((header[11] & 0xFF) << 24);

        // Safety check
        if (len <= 0 || len > 65535) {
            return false;
        }

        pkt.data = new byte[len];

        int bytesRead = fis.read(pkt.data);
        if (bytesRead < len) return false;

        return true;
    }

    public void close() throws IOException {
        if (fis != null) {
            fis.close();
        }
    }
}