package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class Slf4JLogEntryMedia extends LogEntryMedia<Logger> {

    public Slf4JLogEntryMedia(final NamedMessageFormat format, final Level level) {
        this(format, new FixedLogLevel(level));
    }

    public Slf4JLogEntryMedia(final NamedMessageFormat format, final LogLevelDecision<Logger> logLevelResolver) {
        super(format, logLevelResolver);
    }
}
