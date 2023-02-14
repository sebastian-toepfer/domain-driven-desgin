package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicLogLevelLogEntryMedia implements Media<DynamicLogLevelLogEntryMedia>, LogEntry {

    private final FixedLevelLogEntryMedia media;
    private final Level level;
    private final LogLevelDecisionMaker logLevelResolver;

    public DynamicLogLevelLogEntryMedia(
        final NamedMessageFormat namedFormat,
        final Level defaultLevel,
        final LogLevelDecisionMaker logLevelResolver
    ) {
        this(new FixedLevelLogEntryMedia(namedFormat), defaultLevel, logLevelResolver);
    }

    private DynamicLogLevelLogEntryMedia(
        final FixedLevelLogEntryMedia media,
        final Level level,
        final LogLevelDecisionMaker logLevelResolver
    ) {
        this.media = Objects.requireNonNull(media);
        this.level = Objects.requireNonNull(level);
        this.logLevelResolver = Objects.requireNonNull(logLevelResolver);
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final String value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final int value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final long value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final double value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final BigDecimal value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final BigInteger value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final boolean value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final Printable value) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, value),
            logLevelResolver.resolveLevel(name, value).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final Collection<?> values) {
        return new DynamicLogLevelLogEntryMedia(
            media.withValue(name, values),
            logLevelResolver.resolveLevel(name, values).orElse(level),
            logLevelResolver
        );
    }

    @Override
    public DynamicLogLevelLogEntryMedia withValue(final String name, final DynamicLogLevelLogEntryMedia value) {
        return this;
    }

    @Override
    public void logTo(final Logger logger) {
        media.atLevel(level).logTo(logger);
    }
}
