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
package io.github.sebastiantoepfer.ddd.printables.core.test;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class TestMedia extends AbstractMap<String, String> implements Media<TestMedia> {

    private final Map<String, String> values;

    public TestMedia() {
        this.values = new HashMap<>();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return values.entrySet();
    }

    @Override
    public TestMedia withValue(final String name, final LocalTime value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final LocalDateTime value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final LocalDate value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final OffsetTime value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final OffsetDateTime value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final BigInteger value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final BigDecimal value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final TestMedia value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final String value) {
        values.put(name, value);
        return this;
    }

    @Override
    public TestMedia withValue(final String name, final int value) {
        values.put(name, Integer.toString(value));
        return this;
    }

    @Override
    public TestMedia withValue(final String name, final long value) {
        values.put(name, Long.toString(value));
        return this;
    }

    @Override
    public TestMedia withValue(final String name, final double value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TestMedia withValue(final String name, final boolean value) {
        values.put(name, Boolean.toString(value));
        return this;
    }

    @Override
    public TestMedia withValue(final String name, final Collection<?> values) {
        this.values.put(
                name,
                values
                    .stream()
                    .map(Printable.class::cast)
                    .map(p -> p.printOn(new TestMedia()))
                    .collect(collectingAndThen(toList(), Object::toString))
            );
        return this;
    }

    @Override
    public TestMedia withValue(final String name, final Printable value) {
        values.put(name, value.printOn(new TestMedia()).toString());
        return this;
    }

    @Override
    public MediaAwareSubscriber<TestMedia> byteValueSubscriber(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
