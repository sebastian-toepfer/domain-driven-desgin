package io.github.sebastiantoepfer.ddd.media.logging.slf4j.test;

import ch.qos.logback.classic.Level;
import java.util.Map;
import java.util.Objects;

public class LogRecord {

    private final Level level;
    private final String message;
    private final Map<String, String> mdc;

    public LogRecord(final Level level, final String message) {
        this(level, message, Map.of());
    }

    public LogRecord(final Level level, final String messaage, final Map<String, String> mdc) {
        this.level = Objects.requireNonNull(level);
        this.message = Objects.requireNonNull(messaage);
        this.mdc = Objects.requireNonNull(mdc);
    }

    @Override
    public String toString() {
        return "LogRecord{" + "level=" + level + ", message=" + message + ", mdc=" + mdc + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.level);
        hash = 11 * hash + Objects.hashCode(this.message);
        hash = 11 * hash + Objects.hashCode(this.mdc);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogRecord other = (LogRecord) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.level, other.level)) {
            return false;
        }
        return Objects.equals(this.mdc, other.mdc);
    }
}
