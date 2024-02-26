package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.BaseMedia;
import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.core.utils.CopyMap;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntry;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.event.Level;

public final class Slf4JLogEntryMedia {

    public static LogEntryMedia<Logger> of(final NamedMessageFormat format, final Level level) {
        return of(format, level, List.of());
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat format,
        final Level level,
        final List<String> mdcs
    ) {
        return of(format, new FixedLogLevel(level), mdcs);
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat format,
        final Level level,
        final Map<String, String> mdcs
    ) {
        return of(format, new FixedLogLevel(level), mdcs);
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat format,
        final LogLevelDecision<Logger> logLevelDecision
    ) {
        return of(format, logLevelDecision, List.of());
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat format,
        final LogLevelDecision<Logger> logLevelDecision,
        final List<String> mdcs
    ) {
        return of(format, logLevelDecision, mdcs.stream().collect(toMap(Function.identity(), Function.identity())));
    }

    public static LogEntryMedia<Logger> of(
        final NamedMessageFormat format,
        final LogLevelDecision<Logger> logLevelDecision,
        final Map<String, String> mdcs
    ) {
        return new LogEntryMedia<>(
            format,
            new MDCLogLevelDecision(
                logLevelDecision,
                mdcs
                    .entrySet()
                    .stream()
                    .map(e -> Map.entry(new PropertyPath(e.getKey()), e.getValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
            )
        );
    }

    private Slf4JLogEntryMedia() {}

    private static class MDCLogLevelDecision implements LogLevelDecision<Logger> {

        private final LogLevelDecision<Logger> decsision;
        private final Map<PropertyPath, String> mdcNames;
        private final CopyMap<String, String> mdcValues;

        public MDCLogLevelDecision(final LogLevelDecision<Logger> decsision, final Map<PropertyPath, String> mdcNames) {
            this(decsision, mdcNames, Map.of());
        }

        public MDCLogLevelDecision(
            final LogLevelDecision<Logger> decsision,
            final Map<PropertyPath, String> mdcNames,
            final Map<String, String> mdcValues
        ) {
            this.decsision = Objects.requireNonNull(decsision);
            this.mdcNames = Map.copyOf(mdcNames);
            this.mdcValues = new CopyMap<>(mdcValues);
        }

        @Override
        public LogLevelDecision<Logger> resolveLogLevelDecision(final String name, final Object value) {
            return new MDCLogLevelDecision(
                decsision.resolveLogLevelDecision(name, value),
                mdcNames,
                newMdcValues(name, value)
            );
        }

        private Map<String, String> newMdcValues(final String name, final Object value) {
            final Map<String, String> newMdcValues;
            if (value instanceof Printable p) {
                newMdcValues = new MDCMedia(mdcNames, mdcValues).withValue(name, p).mdcValues();
            } else {
                newMdcValues = new MDCMedia(mdcNames, mdcValues).withValue(name, String.valueOf(value)).mdcValues();
            }
            return newMdcValues;
        }

        @Override
        public LogEntry<Logger> logEnty(final Writeable writeable) {
            return new MDCAwareLogEntry(decsision.logEnty(writeable), mdcValues);
        }

        private static class MDCMedia implements BaseMedia<MDCMedia> {

            private final Map<PropertyPath, String> mdcNames;
            private final CopyMap<String, String> mdcValues;

            public MDCMedia(final Map<PropertyPath, String> mdcNames) {
                this(mdcNames, new CopyMap<>(Map.of()));
            }

            MDCMedia(final Map<PropertyPath, String> mdcNames, final CopyMap<String, String> map) {
                this.mdcNames = mdcNames;
                this.mdcValues = map;
            }

            @Override
            public MDCMedia withValue(final String name, final String value) {
                return withValue(new PropertyPath(name), value);
            }

            public MDCMedia withValue(final PropertyPath path, final String value) {
                final MDCMedia result;
                if (mdcNames.containsKey(path) && value != null) {
                    result = new MDCMedia(mdcNames, mdcValues.withNewValue(mdcNames.get(path), value));
                } else {
                    result = this;
                }
                return result;
            }

            @Override
            public MDCMedia withValue(final String name, final Printable value) {
                return new MDCMedia(
                    mdcNames,
                    new CopyMap.MergeOperator<String, String>().apply(
                        mdcValues,
                        new CopyMap<>(printValue(name, value).mdcValues())
                    )
                );
            }

            private MDCMedia printValue(final String name, final Printable value) {
                final MDCMedia media = mdcNames
                    .entrySet()
                    .stream()
                    .filter(mdcName -> mdcName.getKey().startsWith(name))
                    .map(entry -> Map.entry(entry.getKey().subPath(name), entry.getValue()))
                    .collect(collectingAndThen(toMap(Map.Entry::getKey, Map.Entry::getValue), MDCMedia::new));
                return value.printOn(media);
            }

            @Override
            public MDCMedia withValue(final String name, final int value) {
                return withValue(name, String.valueOf(value));
            }

            @Override
            public MDCMedia withValue(final String name, final long value) {
                return withValue(name, String.valueOf(value));
            }

            @Override
            public MDCMedia withValue(final String name, final double value) {
                return withValue(name, String.valueOf(value));
            }

            @Override
            public MDCMedia withValue(final String name, final boolean value) {
                return withValue(name, String.valueOf(value));
            }

            private Map<String, String> mdcValues() {
                return mdcValues;
            }
        }

        private static class MDCAwareLogEntry implements LogEntry<Logger> {

            private final LogEntry<Logger> logger;
            private final Map<String, String> mdc;

            public MDCAwareLogEntry(final LogEntry<Logger> logger, final Map<String, String> mdc) {
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
