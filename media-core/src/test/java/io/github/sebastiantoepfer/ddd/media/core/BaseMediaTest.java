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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

import com.sun.net.httpserver.HttpServer;
import io.github.sebastiantoepfer.ddd.common.Media;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BaseMediaTest {

    @Test
    void should_write_localdate_as_iso_formated_string() {
        assertThat(
            new DefaultTestMedia().withValue("date", LocalDate.of(1982, Month.JUNE, 9)).values(),
            hasEntry("date", "1982-06-09")
        );
    }

    @Test
    void should_write_localtime_as_iso_formated_string_with_current_offset() {
        assertThat(
            new DefaultTestMedia().withValue("time", LocalTime.of(8, 25, 55)).values(),
            hasEntry(is("time"), hasToString(startsWith("08:25:55")))
        );
    }

    @Test
    void should_write_localdatetime_as_iso_formated_string_with_current_offset() {
        assertThat(
            new DefaultTestMedia().withValue("date", LocalDateTime.of(1982, Month.JUNE, 9, 8, 25, 55)).values(),
            hasEntry(is("date"), hasToString(startsWith("1982-06-09T08:25:55")))
        );
    }

    @Test
    void should_write_bytes_as_base64_encoded_string() {
        assertThat(
            new DefaultTestMedia().withValue("bytes", "I might be an image".getBytes(StandardCharsets.UTF_8)).values(),
            hasEntry("bytes", "SSBtaWdodCBiZSBhbiBpbWFnZQ==")
        );
    }

    @Test
    void should_write_reactive_bytes_as_base64_encoded_string() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext(
            "/strings",
            he -> {
                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                he.getResponseBody().write("I might be ".getBytes(StandardCharsets.UTF_8));
                he.getResponseBody().flush();
                he.getResponseBody().write("an image".getBytes(StandardCharsets.UTF_8));
                he.getResponseBody().flush();
                he.getResponseBody().close();
            }
        );
        server.start();

        final DefaultTestMedia response = HttpClient
            .newHttpClient()
            .sendAsync(
                HttpRequest.newBuilder(URI.create("http://localhost:8081/strings")).GET().build(),
                HttpResponse.BodyHandlers.fromSubscriber(
                    new DefaultTestMedia().byteValueSubscriber("bytes"),
                    Media.MediaAwareSubscriber::media
                )
            )
            .thenApply(HttpResponse::body)
            .join();
        server.stop(0);

        assertThat(response.values(), hasEntry("bytes", "SSBtaWdodCBiZSBhbiBpbWFnZQ=="));
    }

    static final class DefaultTestMedia implements BaseMedia<DefaultTestMedia> {

        private Map<String, Object> values;

        public DefaultTestMedia() {
            this(Map.of());
        }

        public DefaultTestMedia(final Map<String, Object> values) {
            this.values = new HashMap<>(values);
        }

        public Map<String, Object> values() {
            return Map.copyOf(values);
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
    }
}
