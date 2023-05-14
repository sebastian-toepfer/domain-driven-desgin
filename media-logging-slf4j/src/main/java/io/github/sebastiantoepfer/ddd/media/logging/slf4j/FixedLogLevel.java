package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.logging.CanNotLog;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntry;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.event.Level;

class FixedLogLevel implements LogLevelDecision<Logger> {

    private final Level level;

    public FixedLogLevel(final Level level) {
        this.level = level;
    }

    @Override
    public LogLevelDecision<Logger> resolveLogLevelDecision(final String name, final Object value) {
        return this;
    }

    @Override
    public LogEntry<Logger> logEnty(final Writeable writeable) {
        return new FixedLogLevelEventEntry(level, writeable);
    }

    private static class FixedLogLevelEventEntry implements LogEntry<Logger> {

        private final Level level;
        private final Writeable writeable;

        public FixedLogLevelEventEntry(final Level level, final Writeable writeable) {
            this.level = level;
            this.writeable = writeable;
        }

        @Override
        public void logTo(final Logger logger) {
            logger.atLevel(level).log(this::logmessage);
        }

        private String logmessage() {
            try {
                return writeable.asString();
            } catch (IOException e) {
                throw new CanNotLog(e);
            }
        }
    }
}
