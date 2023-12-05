package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;

public interface MediaDecorator<D extends Media<D>, T extends MediaDecorator<D, T>> extends Media<T> {
    D decoratedMedia();
}
