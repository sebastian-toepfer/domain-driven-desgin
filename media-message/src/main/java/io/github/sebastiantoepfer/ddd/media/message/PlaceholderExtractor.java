package io.github.sebastiantoepfer.ddd.media.message;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class PlaceholderExtractor {

    private static final Logger LOGGER = Logger.getLogger(PlaceholderExtractor.class.getName());
    static final String START = "${";
    static final char END = '}';
    private final String format;

    public PlaceholderExtractor(final String format) {
        this.format = format;
    }

    public List<Placeholder> findPlaceholders() {
        LOGGER.entering(PlaceholderExtractor.class.getName(), "findPlaceholders");
        final List<Placeholder> result = new ArrayList<>();
        int i = 0;
        while (i < format.length()) {
            if (isStartpoint(i)) {
                final Placeholder newPlaceholder = createPlaceholderStartsAt(i);
                result.add(newPlaceholder);
                i += newPlaceholder.length();
            } else {
                i++;
            }
        }
        LOGGER.exiting(PlaceholderExtractor.class.getName(), "findPlaceholders", result);
        return result;
    }

    private Placeholder createPlaceholderStartsAt(final int start) {
        LOGGER.entering(PlaceholderExtractor.class.getName(), "createPlaceholderStartsAt", start);
        final StringBuilder sb = new StringBuilder();
        for (int i = start; i < format.length(); i++) {
            final char c = format.charAt(i);
            sb.append(c);
            if (c == END) {
                break;
            }
        }
        if (sb.charAt(sb.length() - 1) != END) {
            throw new IllegalArgumentException(String.format("Format contains invalid placeholder %s", sb));
        }
        final Placeholder result = new Placeholder(sb.toString());
        LOGGER.entering(PlaceholderExtractor.class.getName(), "createPlaceholderStartsAt", result);
        return result;
    }

    private boolean isStartpoint(final int i) {
        LOGGER.entering(PlaceholderExtractor.class.getName(), "isStartpoint", i);
        boolean result = true;
        for (int j = 0; j < START.length() && i + j < format.length(); j++) {
            result &= START.charAt(j) == format.charAt(i + j);
        }
        LOGGER.exiting(PlaceholderExtractor.class.getName(), "isStartpoint", result);
        return result;
    }
}
