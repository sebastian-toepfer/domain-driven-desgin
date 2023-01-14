package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;
import java.util.Objects;

public abstract class MediaDecorator<D extends Media<D>, T extends MediaDecorator<D, T>> implements Media<T> {

    private final D decoratedMedia;

    protected MediaDecorator(final D decoratedMedia) {
        this.decoratedMedia = Objects.requireNonNull(decoratedMedia);
    }

    public final D decoratedMedia() {
        return decoratedMedia;
    }
}
