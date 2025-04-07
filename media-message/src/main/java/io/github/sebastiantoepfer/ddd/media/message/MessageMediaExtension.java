package io.github.sebastiantoepfer.ddd.media.message;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.BaseMedia;
import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Objects;

public abstract class MessageMediaExtension<T extends MessageMediaExtension<T>> implements BaseMedia<T>, Writeable {

    private final MessageMedia media;

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
    public final void writeTo(final OutputStream write) throws IOException {
        media.writeTo(write);
    }

    protected abstract T createWith(final MessageMedia message);
}
