package io.github.sebastiantoepfer.ddd.media.message;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

public abstract class MessageMediaExtension<T extends MessageMediaExtension<T>> implements Media<T>, Writeable {

    private final MessageMedia media;

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "false positiv. messagemedia is immutable!")
    protected MessageMediaExtension(final MessageMedia media) {
        this.media = Objects.requireNonNull(media);
    }

    @Override
    public final T withValue(final String name, final String value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final int value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final long value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final double value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final BigDecimal value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final BigInteger value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final boolean value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final Printable value) {
        return createWith(media.withValue(name, value));
    }

    @Override
    public final T withValue(final String name, final Collection<?> values) {
        return createWith(media.withValue(name, values));
    }

    @Override
    public final T withValue(final String name, final T value) {
        return createWith(media);
    }

    @Override
    public final Writer writeTo(final Writer write) throws IOException {
        return media.writeTo(write);
    }

    protected abstract T createWith(final MessageMedia message);
}
