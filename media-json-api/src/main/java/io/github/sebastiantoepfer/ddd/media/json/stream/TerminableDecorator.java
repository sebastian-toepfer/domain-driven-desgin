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
package io.github.sebastiantoepfer.ddd.media.json.stream;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.decorator.MediaDecorator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

public final class TerminableDecorator implements TerminableMedia<TerminableDecorator> {

    private final MediaDecorator<? extends TerminableMedia, ?> decorator;

    public TerminableDecorator(final MediaDecorator<? extends TerminableMedia, ?> decorator) {
        this.decorator = Objects.requireNonNull(decorator);
    }

    @Override
    public TerminableMedia end() {
        return decorator.decoratedMedia().end();
    }

    @Override
    public void close() throws Exception {
        decorator.decoratedMedia().close();
    }

    @Override
    public void flush() throws IOException {
        decorator.decoratedMedia().flush();
    }

    @Override
    public TerminableDecorator withValue(final String name, final String value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final int value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final BigInteger value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final long value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final BigDecimal value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final double value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final boolean value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final Printable value) {
        return new TerminableDecorator(decorator.withValue(name, value));
    }

    @Override
    public TerminableDecorator withValue(final String name, final Collection<?> values) {
        return new TerminableDecorator(decorator.withValue(name, values));
    }

    @Override
    public TerminableDecorator withValue(final String name, final TerminableDecorator value) {
        throw new UnsupportedOperationException();
    }
}
