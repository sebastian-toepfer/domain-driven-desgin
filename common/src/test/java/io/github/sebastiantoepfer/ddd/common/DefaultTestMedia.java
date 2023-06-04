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
package io.github.sebastiantoepfer.ddd.common;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class DefaultTestMedia extends AbstractMap<String, Object> implements Media<DefaultTestMedia> {

    private Map<String, Object> values;

    public DefaultTestMedia() {
        this(Map.of());
    }

    public DefaultTestMedia(final Map<String, Object> values) {
        this.values = new HashMap<>(values);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return values.entrySet();
    }

    @Override
    public DefaultTestMedia withValue(final String name, final String value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final int value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final long value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final double value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final boolean value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final LocalTime value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final LocalDateTime value) {
        values.put(name, value);
        return this;
    }

    @Override
    public DefaultTestMedia withValue(final String name, final byte[] value) {
        values.put(name, value);
        return this;
    }

    @Override
    public MediaAwareSubscriber<DefaultTestMedia> byteValueSubscriber(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
