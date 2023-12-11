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
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.Function;

public final class JsonArrayStreamMediaPrintableAdapter implements Flushable, AutoCloseable {

    private final JsonGenerator generator;
    private final Function<JsonObjectStreamMedia, TerminableMedia<?>> mediaDecorator;

    public JsonArrayStreamMediaPrintableAdapter(final OutputStream os) {
        this(os, m -> m);
    }

    public JsonArrayStreamMediaPrintableAdapter(
        final OutputStream os,
        final Function<JsonObjectStreamMedia, TerminableMedia<?>> mediaDecorator
    ) {
        this(Json.createGenerator(os), mediaDecorator);
    }

    private JsonArrayStreamMediaPrintableAdapter(
        final JsonGenerator generator,
        final Function<JsonObjectStreamMedia, TerminableMedia<?>> mediaDecorator
    ) {
        this.generator = Objects.requireNonNull(generator).writeStartArray();
        this.mediaDecorator = Objects.requireNonNull(mediaDecorator);
    }

    @Override
    public void flush() throws IOException {
        generator.flush();
    }

    @Override
    public void close() throws Exception {
        end();
        generator.close();
    }

    public JsonArrayStreamMediaPrintableAdapter end() {
        generator.writeEnd();
        return this;
    }

    public JsonArrayStreamMediaPrintableAdapter print(final Printable printable) {
        printable.printOn((TerminableMedia) mediaDecorator.apply(new JsonObjectStreamMedia(generator))).end();
        return this;
    }
}
