package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.media.message.MessageMedia;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class FixedLevelLogEntry implements LogEntry {

    private final Level level;
    private final MessageMedia message;

    public FixedLevelLogEntry(final Level level, final MessageMedia message) {
        this.level = Objects.requireNonNull(level);
        this.message = message;
    }

    @Override
    public void logTo(final Logger logger) {
        try (final StringWriter sw = new StringWriter()) {
            final LogRecord logRecord = new LogRecord(level, message.writeTo(sw).toString());
            logRecord.setLoggerName(logger.getName());
            logRecord.setSourceClassName(logger.getName());
            logger.log(logRecord);
        } catch (IOException e) {
            throw new CanNotLog(e);
        }
    }

    private static class CanNotLog extends RuntimeException {

        public CanNotLog(final Throwable cause) {
            super(cause);
        }
    }
}
