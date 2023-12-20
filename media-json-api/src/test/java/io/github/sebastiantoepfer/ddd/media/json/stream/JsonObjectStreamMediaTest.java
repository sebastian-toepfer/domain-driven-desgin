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
import jakarta.json.Json;
import jakarta.json.spi.JsonProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonObjectStreamMediaTest {

    private Media<JsonObjectStreamMedia> media;
    private ByteArrayOutputStream baos;

    @BeforeEach
    void initMedia() {
        baos = new ByteArrayOutputStream();
        media = new JsonObjectStreamMedia(JsonProvider.provider(), baos);
    }

    @Test
    void should_write_jsonobject_with_string_property() throws Exception {
        media.withValue("test", "test").end().flush();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", "test").build())
        );
    }

    @Test
    void should_write_jsonobject_with_int_property() throws Exception {
        media.withValue("test", 1).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", 1).build())
        );
    }

    @Test
    void should_write_jsonobject_with_long_property() throws Exception {
        media.withValue("test", 1L).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", 1).build())
        );
    }

    @Test
    void should_write_jsonobject_with_double_property() throws Exception {
        media.withValue("test", 3.14).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", 3.14).build())
        );
    }

    @Test
    void should_write_jsonobject_with_true_property() throws Exception {
        media.withValue("test", true).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", true).build())
        );
    }

    @Test
    void should_write_jsonobject_with_false_property() throws Exception {
        media.withValue("test", false).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", false).build())
        );
    }

    @Test
    void should_write_jsonobject_with_int_array_property() throws Exception {
        media.withValue("test", List.of(1, 2)).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("test", Json.createArrayBuilder().add(1).add(2)).build())
        );
    }

    @Test
    void should_write_jsonobject_with_object_object_property() throws Exception {
        media.withValue("value", testPrintable("ernie")).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(Json.createObjectBuilder().add("value", Json.createObjectBuilder().add("name", "ernie")).build())
        );
    }

    @Test
    void should_write_jsonobject_with_object_array_property() throws Exception {
        media.withValue("test", List.of(testPrintable("1"), testPrintable("2"))).close();
        assertThat(
            Json.createReader(new ByteArrayInputStream(baos.toByteArray())).readObject(),
            is(
                Json
                    .createObjectBuilder()
                    .add(
                        "test",
                        Json
                            .createArrayBuilder()
                            .add(Json.createObjectBuilder().add("name", "1"))
                            .add(Json.createObjectBuilder().add("name", "2"))
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
