package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.core.utils.CopyMap;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntry;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.event.Level;

public class Slf4JLogEntryMedia extends LogEntryMedia<Logger> {

    public Slf4JLogEntryMedia(final NamedMessageFormat format, final Level level) {
        this(format, level, List.of());
    }

    public Slf4JLogEntryMedia(final NamedMessageFormat format, final Level level, final List<String> mdcs) {
        this(format, new FixedLogLevel(level), mdcs);
    }

    public Slf4JLogEntryMedia(final NamedMessageFormat format, final LogLevelDecision<Logger> logLevelDecision) {
        this(format, logLevelDecision, List.of());
    }

    public Slf4JLogEntryMedia(
        final NamedMessageFormat format,
        final LogLevelDecision<Logger> logLevelDecision,
        final List<String> mdcs
    ) {
        super(format, new MDCLogLevelDecision(logLevelDecision, mdcs));
    }

    private static class MDCLogLevelDecision implements LogLevelDecision<Logger> {

        private final LogLevelDecision<Logger> decsision;
        private final List<String> mdcNames;
        private final CopyMap<String, String> mdcValues;

        public MDCLogLevelDecision(final LogLevelDecision<Logger> decsision, final List<String> mdcNames) {
            this(decsision, mdcNames, Map.of());
        }

        public MDCLogLevelDecision(
            final LogLevelDecision<Logger> decsision,
            final List<String> mdcNames,
            final Map<String, String> mdcValues
        ) {
            this.decsision = Objects.requireNonNull(decsision);
            this.mdcNames = List.copyOf(mdcNames);
            this.mdcValues = new CopyMap<>(mdcValues);
        }

        @Override
        public LogLevelDecision<Logger> resolveLogLevelDecision(final String name, final Object value) {
            final LogLevelDecision<Logger> newLogLevelDecision = decsision.resolveLogLevelDecision(name, value);
            final Map<String, String> newMdcValues;
            if (mdcNames.contains(name) && value != null) {
                newMdcValues = mdcValues.withNewValue(name, String.valueOf(value));
            } else {
                newMdcValues = mdcValues;
            }
            return new MDCLogLevelDecision(newLogLevelDecision, mdcNames, newMdcValues);
        }

        @Override
        public LogEntry<Logger> logEnty(final Writeable writeable) {
            return new MDCAwardLogEntry(decsision.logEnty(writeable), mdcValues);
        }

        private static class MDCAwardLogEntry implements LogEntry<Logger> {

            private final LogEntry<Logger> logger;
            private final Map<String, String> mdc;

            public MDCAwardLogEntry(final LogEntry<Logger> logger, final Map<String, String> mdc) {
                this.logger = Objects.requireNonNull(logger);
                this.mdc = Objects.requireNonNull(mdc);
            }

            @Override
            public void logTo(final Logger logger) {
                mdc.entrySet().forEach(entry -> MDC.put(entry.getKey(), entry.getValue()));
                try {
                    this.logger.logTo(logger);
                } finally {
                    mdc.keySet().forEach(MDC::remove);
                }
            }
        }
    }
}
