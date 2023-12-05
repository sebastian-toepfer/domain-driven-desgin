/*
 * The MIT License
 *
 * Copyright 2023 sebastian.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.sebastiantoepfer.ddd.media.core;

import io.github.sebastiantoepfer.ddd.common.Media;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Flow;

public final class DefaultMediaAwareSubscriber<T extends Media<T>> implements Media.MediaAwareSubscriber<T> {

    private final T media;
    private final String name;
    private final List<ByteBuffer> bytes;

    public DefaultMediaAwareSubscriber(final T media, final String name) {
        this.media = Objects.requireNonNull(media);
        this.name = Objects.requireNonNull(name);
        bytes = new ArrayList<>();
    }

    @Override
    public T media() {
        final int size = bytes.stream().mapToInt(ByteBuffer::capacity).sum();
        return media.withValue(name, bytes.stream().reduce(ByteBuffer.allocate(size), ByteBuffer::put).array());
    }

    @Override
    public void onSubscribe(final Flow.Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(final List<ByteBuffer> t) {
        bytes.addAll(t);
    }

    @Override
    public void onError(final Throwable thrwbl) {
        //will be ignored
    }

    @Override
    public void onComplete() {
        //will be ignored -> we use media(), with which we can return our media
    }
}
