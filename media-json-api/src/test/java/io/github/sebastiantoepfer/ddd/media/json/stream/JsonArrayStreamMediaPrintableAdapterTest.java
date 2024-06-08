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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.decorator.TranslateNameDecorator;
import io.github.sebastiantoepfer.ddd.media.core.decorator.Translator;
import jakarta.json.Json;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonArrayStreamMediaPrintableAdapterTest {

    private ByteArrayOutputStream baos;

    @BeforeEach
    void initMedia() {
        baos = new ByteArrayOutputStream();
    }

    @Test
    void should_create_array_with_printables() throws Exception {
        List.of(testPrintable("ernie"), testPrintable("bert"))
            .stream()
            .reduce(
                new JsonArrayStreamMediaPrintableAdapter(baos),
                JsonArrayStreamMediaPrintableAdapter::print,
                (l, r) -> null
            )
            .end()
            .flush();

        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readArray(),
            is(
                Json.createArrayBuilder()
                    .add(Json.createObjectBuilder().add("name", "ernie"))
                    .add(Json.createObjectBuilder().add("name", "bert"))
                    .build()
            )
        );
    }

    @Test
    void should_create_array_with_decorated_printables() throws Exception {
        List.of(testPrintable("ernie"), testPrintable("bert"))
            .stream()
            .reduce(
                new JsonArrayStreamMediaPrintableAdapter(
                    baos,
                    media ->
                        new TerminableDecorator(
                            new TranslateNameDecorator<>(
                                media,
                                new Translator() {
                                    @Override
                                    public Optional<String> translate(final String translate) {
                                        return Optional.of(translate)
                                            .filter(Predicate.isEqual("name"))
                                            .map(s -> "NAME");
                                    }
                                }
                            )
                        )
                ),
                JsonArrayStreamMediaPrintableAdapter::print,
                (l, r) -> null
            )
            .close();

        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readArray(),
            is(
                Json.createArrayBuilder()
                    .add(Json.createObjectBuilder().add("NAME", "ernie"))
                    .add(Json.createObjectBuilder().add("NAME", "bert"))
                    .build()
            )
        );
    }

    private Printable testPrintable(final String value) {
        return new Printable() {
            @Override
            public <T extends Media<T>> T printOn(final T media) {
                return media.withValue("name", value);
            }
        };
    }
}
