package model;

public class ParsedPacket {
    public String srcIp, dstIp;
    public int srcPort, dstPort;
    public int protocol;
    public boolean hasTcp, hasUdp;

    public void reset() {
        srcIp = null;
        dstIp = null;
        srcPort = 0;
        dstPort = 0;
        protocol = 0;
        hasTcp = false;
        hasUdp = false;
    }

    @Override
    public String toString() {
        return "ParsedPacket{" +
                "srcIp='" + srcIp + '\'' +
                ", dstIp='" + dstIp + '\'' +
                ", srcPort=" + srcPort +
                ", dstPort=" + dstPort +
                ", protocol=" + protocol +
                ", TCP=" + hasTcp +
                ", UDP=" + hasUdp +
                '}';
    }
}