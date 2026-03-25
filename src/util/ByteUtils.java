package util;

public class ByteUtils {

    public static int read16(byte[] d, int i) {
        if (d == null || i + 1 >= d.length) return 0;
        return ((d[i] & 0xFF) << 8) | (d[i+1] & 0xFF);
    }

    public static int read32(byte[] d, int i) {
        if (d == null || i + 3 >= d.length) return 0;

        return ((d[i] & 0xFF)) |
               ((d[i+1] & 0xFF) << 8) |
               ((d[i+2] & 0xFF) << 16) |
               ((d[i+3] & 0xFF) << 24);
    }
}