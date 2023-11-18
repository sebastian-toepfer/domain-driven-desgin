package io.github.sebastiantoepfer.ddd.media.logging.jul;

import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JULogEntryMedia {

    public static LogEntryMedia<Logger> of(final NamedMessageFormat namedFormat, final Level level) {
        return of(namedFormat, new FixedLogLevel(level));
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat namedFormat,
        final LogLevelDecision<Logger> logLevelResolver
    ) {
        return new LogEntryMedia<>(namedFormat, logLevelResolver);
    }

    private JULogEntryMedia() {}
}
