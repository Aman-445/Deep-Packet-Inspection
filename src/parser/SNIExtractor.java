package parser;

import java.util.Optional;

public class SNIExtractor {

    public static Optional<String> extract(byte[] data) {

        String payload = new String(data);

        // 🔥 Simple pattern matching
        if (payload.contains("youtube")) {
            return Optional.of("youtube.com");
        }

        if (payload.contains("googlevideo")) {
            return Optional.of("googlevideo.com");
        }

        if (payload.contains("ytimg")) {
            return Optional.of("ytimg.com");
        }

        return Optional.empty();
    }
}