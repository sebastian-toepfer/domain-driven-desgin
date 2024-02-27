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
package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.utils.PrintableToObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public final class NameFilteredDecorator<T extends Media<T>> implements MediaDecorator<T, NameFilteredDecorator<T>> {

    private final Predicate<String> namePredicate;
    private final T decoratedMedia;

    public NameFilteredDecorator(final T decoratedMedia, final Predicate<String> namePredicate) {
        this.decoratedMedia = Objects.requireNonNull(decoratedMedia);
        this.namePredicate = Objects.requireNonNull(namePredicate);
    }

    @Override
    public T decoratedMedia() {
        return decoratedMedia;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final LocalTime value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final LocalDate value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final LocalDateTime value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final OffsetTime value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final OffsetDateTime value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final byte[] bytes) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, bytes), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final String value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final int value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final long value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final double value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final BigInteger value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final BigDecimal value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final boolean value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(decoratedMedia().withValue(name, value), namePredicate);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final Printable value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(
                decoratedMedia().withValue(name, new FilteredPrintable(value)),
                namePredicate
            );
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final Collection<?> values) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = values
                .stream()
                .map(PrintableToObjectMapper::new)
                .map(mapper -> mapper.toValue(FilteredPrintable::new))
                .collect(
                    collectingAndThen(
                        toList(),
                        mappedValues ->
                            new NameFilteredDecorator<>(decoratedMedia.withValue(name, mappedValues), namePredicate)
                    )
                );
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public NameFilteredDecorator<T> withValue(final String name, final NameFilteredDecorator<T> value) {
        final NameFilteredDecorator<T> result;
        if (namePredicate.test(name)) {
            result = new NameFilteredDecorator<>(
                decoratedMedia().withValue(name, value.decoratedMedia()),
                namePredicate
            );
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public MediaAwareSubscriber<NameFilteredDecorator<T>> byteValueSubscriber(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private final class FilteredPrintable implements Printable {

        private final Printable delegate;

        public FilteredPrintable(final Printable delegate) {
            this.delegate = Objects.requireNonNull(delegate);
        }

        @Override
        public <M extends Media<M>> M printOn(final M media) {
            return delegate.printOn(new NameFilteredDecorator<>(media, namePredicate)).decoratedMedia();
        }
    }
}
