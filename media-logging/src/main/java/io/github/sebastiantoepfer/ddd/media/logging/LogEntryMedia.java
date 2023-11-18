package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.BaseMedia;
import io.github.sebastiantoepfer.ddd.media.message.MessageMedia;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

public final class LogEntryMedia<T> implements BaseMedia<LogEntryMedia<T>>, LogEntry<T> {

    private final MessageMedia media;
    private final LogLevelDecision<T> logLevelResolver;

    public LogEntryMedia(final NamedMessageFormat namedFormat, final LogLevelDecision<T> logLevelResolver) {
        this(new MessageMedia(namedFormat), logLevelResolver);
    }

    private LogEntryMedia(final MessageMedia media, final LogLevelDecision<T> logLevelResolver) {
        this.media = Objects.requireNonNull(media);
        this.logLevelResolver = Objects.requireNonNull(logLevelResolver);
    }

    @Override
    public LogEntryMedia withValue(final String name, final String value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final int value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final long value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final double value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final BigDecimal value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final BigInteger value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final boolean value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final Printable value) {
        return new LogEntryMedia<>(media.withValue(name, value), logLevelResolver.resolveLogLevelDecision(name, value));
    }

    @Override
    public LogEntryMedia withValue(final String name, final Collection<?> values) {
        return new LogEntryMedia<>(
            media.withValue(name, values),
            logLevelResolver.resolveLogLevelDecision(name, values)
        );
    }

    @Override
    public LogEntryMedia withValue(final String name, final LogEntryMedia value) {
        return this;
    }

    @Override
    public void logTo(final T logger) {
        logLevelResolver.logEnty(media).logTo(logger);
    }
}
