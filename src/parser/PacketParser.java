package parser;

import model.ParsedPacket;
import model.RawPacket;

public class PacketParser {

    public boolean parse(RawPacket raw, ParsedPacket p) {

    byte[] d = raw.data;

    if (d == null || d.length < 34) return false;

    int ethOffset = 14;

    // Check EtherType (bytes 12-13)
    int etherType = ((d[12] & 0xFF) << 8) | (d[13] & 0xFF);

    // Only process IPv4 (0x0800)
    if (etherType != 0x0800) {
        return false;
    }

    int ipOffset = ethOffset;

    // Extract IPs
    p.srcIp = (d[ipOffset+12]&0xFF)+"."+(d[ipOffset+13]&0xFF)+"."+ 
              (d[ipOffset+14]&0xFF)+"."+(d[ipOffset+15]&0xFF);

    p.dstIp = (d[ipOffset+16]&0xFF)+"."+(d[ipOffset+17]&0xFF)+"."+ 
              (d[ipOffset+18]&0xFF)+"."+(d[ipOffset+19]&0xFF);

    p.protocol = d[ipOffset+9] & 0xFF;

    int ipHeaderLength = (d[ipOffset] & 0x0F) * 4;
    int transportOffset = ipOffset + ipHeaderLength;

    // TCP
    if (p.protocol == 6) {
        if (d.length < transportOffset + 4) return false;

        p.srcPort = ((d[transportOffset]&0xFF)<<8) | (d[transportOffset+1]&0xFF);
        p.dstPort = ((d[transportOffset+2]&0xFF)<<8) | (d[transportOffset+3]&0xFF);

        p.hasTcp = true;
    }

    // UDP
    else if (p.protocol == 17) {
        if (d.length < transportOffset + 4) return false;

        p.srcPort = ((d[transportOffset]&0xFF)<<8) | (d[transportOffset+1]&0xFF);
        p.dstPort = ((d[transportOffset+2]&0xFF)<<8) | (d[transportOffset+3]&0xFF);

        p.hasUdp = true;
    }

    return true;
 }
}