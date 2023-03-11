package io.github.sebastiantoepfer.ddd.media.logging.jul;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntry;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

class FixedLogLevel implements LogLevelDecision<Logger> {

    private final Level level;

    public FixedLogLevel(final Level level) {
        this.level = Objects.requireNonNull(level);
    }

    @Override
    public LogLevelDecision<Logger> resolveLogLevelDecision(final String name, final Object value) {
        return this;
    }

    @Override
    public LogEntry<Logger> logEnty(final Writeable writeable) {
        return new JULogEntry(level, writeable);
    }
}
