package model;

public class FiveTuple {
    public int srcIp, dstIp;
    public int srcPort, dstPort;
    public int protocol;

   @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FiveTuple)) return false;

    FiveTuple that = (FiveTuple) o;

    return srcIp == that.srcIp &&
           dstIp == that.dstIp &&
           srcPort == that.srcPort &&
           dstPort == that.dstPort &&
           protocol == that.protocol;
}

@Override
public int hashCode() {
    int result = srcIp;
    result = 31 * result + dstIp;
    result = 31 * result + srcPort;
    result = 31 * result + dstPort;
    result = 31 * result + protocol;
    return result;
}

    @Override
    public String toString() {
        return srcIp + ":" + srcPort + " -> " +
               dstIp + ":" + dstPort +
               " (proto=" + protocol + ")";
    }
}