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

import static java.util.function.Predicate.not;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.json.util.ObjectToJsonValueMapper;
import io.github.sebastiantoepfer.ddd.media.json.util.ToJsonValueMapper;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

public final class JsonObjectStreamMedia implements TerminableMedia<JsonObjectStreamMedia>, AutoCloseable, Flushable {

    private final JsonGenerator generator;

    public JsonObjectStreamMedia(final OutputStream os) {
        this(Json.createGenerator(os));
    }

    JsonObjectStreamMedia(final JsonGenerator generator) {
        this.generator = Objects.requireNonNull(generator).writeStartObject();
    }

    @Override
    public void flush() throws IOException {
        generator.flush();
    }

    @Override
    public void close() throws IOException {
        end();
        generator.close();
    }

    @Override
    public JsonObjectStreamMedia end() {
        generator.writeEnd();
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final String value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final int value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final long value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final double value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final boolean value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final BigInteger value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final BigDecimal value) {
        generator.write(name, value);
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final Printable value) {
        generator.writeStartObject(name);
        value.printOn(this);
        generator.writeEnd();
        return this;
    }

    @Override
    public JsonObjectStreamMedia withValue(final String name, final Collection<?> values) {
        generator.writeStartArray(name);
        values
            .stream()
            .map(ObjectToJsonValueMapper::new)
            .map(ToJsonValueMapper::asJsonValue)
            .filter(not(value -> value.getValueType() == JsonValue.ValueType.NULL))
            .forEach(generator::write);
        generator.writeEnd();
        return this;
    }
}
