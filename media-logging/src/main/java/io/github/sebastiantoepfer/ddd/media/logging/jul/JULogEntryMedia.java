package io.github.sebastiantoepfer.ddd.media.logging.jul;

import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JULogEntryMedia extends LogEntryMedia<Logger> {

    public JULogEntryMedia(final NamedMessageFormat namedFormat, final Level level) {
        this(namedFormat, new FixedLogLevel(level));
    }

    public JULogEntryMedia(final NamedMessageFormat namedFormat, final LogLevelDecision<Logger> logLevelResolver) {
        super(namedFormat, logLevelResolver);
    }
}
