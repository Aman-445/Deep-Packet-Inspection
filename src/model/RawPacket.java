package model;

public class RawPacket {
    public byte[] data;
    public int tsSec, tsUsec;

    // Empty constructor (used as termination signal)
    public RawPacket() {
        this.data = null;
    }

    // Normal packet constructor
    public RawPacket(byte[] data, int tsSec, int tsUsec) {
        this.data = data;
        this.tsSec = tsSec;
        this.tsUsec = tsUsec;
    }

    @Override
    public String toString() {
        return "RawPacket{size=" + (data != null ? data.length : 0) + "}";
    }
}