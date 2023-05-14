package io.github.sebastiantoepfer.ddd.media.logging.jul;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.logging.CanNotLog;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntry;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class JULogEntry implements LogEntry<Logger> {

    private final Level level;
    private final Writeable message;

    public JULogEntry(final Level level, final Writeable message) {
        this.level = Objects.requireNonNull(level);
        this.message = message;
    }

    @Override
    public void logTo(final Logger logger) {
        try {
            final LogRecord logRecord = new LogRecord(level, message.asString());
            logRecord.setLoggerName(logger.getName());
            logRecord.setSourceClassName(logger.getName());
            logger.log(logRecord);
        } catch (IOException e) {
            throw new CanNotLog(e);
        }
    }
}
