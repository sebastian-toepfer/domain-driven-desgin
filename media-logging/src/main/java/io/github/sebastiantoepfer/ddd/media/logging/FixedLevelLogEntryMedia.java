package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.message.MessageMedia;
import io.github.sebastiantoepfer.ddd.media.message.NamedMessageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.logging.Level;

public class FixedLevelLogEntryMedia implements Media<FixedLevelLogEntryMedia> {

    private final MessageMedia media;

    public FixedLevelLogEntryMedia(final NamedMessageFormat namedFormat) {
        this(new MessageMedia(namedFormat));
    }

    private FixedLevelLogEntryMedia(final MessageMedia media) {
        this.media = media;
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final String value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final int value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final long value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final double value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final BigDecimal value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final BigInteger value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final boolean value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final Printable value) {
        return new FixedLevelLogEntryMedia(media.withValue(name, value));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final Collection<?> values) {
        return new FixedLevelLogEntryMedia(media.withValue(name, values));
    }

    @Override
    public FixedLevelLogEntryMedia withValue(final String name, final FixedLevelLogEntryMedia value) {
        return this;
    }

    public LogEntry atLevel(final Level level) {
        return new FixedLevelLogEntry(level, media);
    }
}
