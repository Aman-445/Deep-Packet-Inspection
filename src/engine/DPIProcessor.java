package engine;

import java.util.HashMap;

import model.AppType;
import model.FiveTuple;
import model.Flow;
import model.ParsedPacket;
import model.RawPacket;
import parser.SNIExtractor;

public class DPIProcessor {

    private RuleManager rules;
    private HashMap<FiveTuple, Flow> flows = new HashMap<>();

    public DPIProcessor(RuleManager rules) {
        this.rules = rules;
    }

    public boolean process(RawPacket raw, ParsedPacket parsed) {

        // 🔥 SAFETY CHECK (skip non-IP packets)
        if (parsed.srcIp == null || parsed.dstIp == null) {
            return true;
        }

        // ✅ Create flow key
        FiveTuple t = new FiveTuple();
        t.srcIp = parsed.srcIp.hashCode();
        t.dstIp = parsed.dstIp.hashCode();
        t.srcPort = parsed.srcPort;
        t.dstPort = parsed.dstPort;
        t.protocol = parsed.protocol;

        Flow f = flows.computeIfAbsent(t, k -> new Flow(k));

        // ✅ Basic protocol detection
        if (parsed.dstPort == 443) {
            f.app = AppType.HTTPS;
        } else if (parsed.dstPort == 80) {
            f.app = AppType.HTTP;
        }

        // ✅ SNI Extraction (ONLY for HTTPS traffic)
        if (parsed.dstPort == 443) {
            SNIExtractor.extract(raw.data).ifPresent(domain -> {

                f.sni = domain;

                // 🔥 CLEAN demo print
                System.out.println("Detected domain: " + domain);

                // ✅ YouTube detection logic
                if (domain.contains("youtube") ||
                    domain.contains("googlevideo") ||
                    domain.contains("ytimg")) {

                    f.app = AppType.YOUTUBE;

                    // 🚀 FINAL DEMO PRINT (IMPORTANT)
                    System.out.println("🚀 YouTube traffic detected via SNI: " + domain);
                    System.out.flush();
                }
            });
        }

        // ✅ Apply blocking rules
        if (rules.isBlocked(f.app)) {
            f.blocked = true;
        }

        return !f.blocked;
    }

    public int getFlowCount() {
        return flows.size();
    }
}