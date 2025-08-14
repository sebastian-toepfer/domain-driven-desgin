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
import jakarta.json.spi.JsonProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TerminableDecoratorTest {

    private Media<TerminableDecorator> media;
    private ByteArrayOutputStream baos;

    @BeforeEach
    void initMedia() {
        baos = new ByteArrayOutputStream();
        media = new TerminableDecorator(
            new TranslateNameDecorator<>(
                new JsonObjectStreamMedia(JsonProvider.provider(), baos),
                new Translator() {
                    @Override
                    public Optional<String> translate(final String translate) {
                        return Optional.of(translate)
                            .filter(Predicate.isEqual("name"))
                            .map(s -> "NAME");
                    }
                }
            )
        );
    }

    @Test
    void should_write_jsonobject_with_string_property() throws Exception {
        media.withValue("name", "test").end().flush();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", "test").build())
        );
    }

    @Test
    void should_write_jsonobject_with_int_property() throws Exception {
        media.withValue("name", 1).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", 1).build())
        );
    }

    @Test
    void should_write_jsonobject_with_long_property() throws Exception {
        media.withValue("name", 1L).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", 1).build())
        );
    }

    @Test
    void should_write_jsonobject_with_double_property() throws Exception {
        media.withValue("name", 3.14).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", 3.14).build())
        );
    }

    @Test
    void should_write_jsonobject_with_bigDecimal_property() throws Exception {
        media.withValue("name", BigDecimal.ZERO).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", BigDecimal.ZERO).build())
        );
    }

    @Test
    void should_write_jsonobject_with_bigInt_property() throws Exception {
        media.withValue("name", BigInteger.TEN).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", BigInteger.TEN).build())
        );
    }

    @Test
    void should_write_jsonobject_with_true_property() throws Exception {
        media.withValue("name", true).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", true).build())
        );
    }

    @Test
    void should_write_jsonobject_with_false_property() throws Exception {
        media.withValue("name", false).end().flush();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", false).build())
        );
    }

    @Test
    void should_write_jsonobject_with_int_array_property() throws Exception {
        media.withValue("name", List.of(1, 2)).end().flush();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", Json.createArrayBuilder().add(1).add(2)).build())
        );
    }

    @Test
    void should_write_jsonobject_with_object_object_property() throws Exception {
        media.withValue("name", testPrintable("ernie")).end().flush();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("NAME", Json.createObjectBuilder().add("NAME", "ernie")).build())
        );
    }

    @Test
    void should_write_jsonobject_with_object_array_property() throws Exception {
        media.withValue("name", List.of(testPrintable("1"), testPrintable("2"))).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(
                Json.createObjectBuilder()
                    .add(
                        "NAME",
                        Json.createArrayBuilder()
                            .add(Json.createObjectBuilder().add("NAME", "1"))
                            .add(Json.createObjectBuilder().add("NAME", "2"))
                    )
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
