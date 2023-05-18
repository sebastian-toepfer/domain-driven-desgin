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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    void should_write_biginteger_as_long() {
        assertThat(
            new DefaultTestMedia().withValue("biginteger", BigInteger.TEN).values(),
            hasEntry("biginteger", 10L)
        );
    }

    @Test
    void should_write_bigdecimal_as_double() {
        assertThat(
            new DefaultTestMedia().withValue("bigdecimal", BigDecimal.valueOf(3.14)).values(),
            hasEntry("bigdecimal", 3.14)
        );
    }

    @Test
    void should_write_return_this_for_printables() {
        final DefaultTestMedia media = new DefaultTestMedia();
        assertThat(media.withValue("printable", new Empty()), Matchers.sameInstance(media));
    }

    @Test
    void should_write_return_this_for_submedia() {
        final DefaultTestMedia media = new DefaultTestMedia();
        assertThat(media.withValue("media", new DefaultTestMedia()), Matchers.sameInstance(media));
    }

    @Test
    void should_write_return_this_for_collection() {
        final DefaultTestMedia media = new DefaultTestMedia();
        assertThat(media.withValue("media", List.of()), Matchers.sameInstance(media));
    }

    @Test
    void should_write_localdate_as_iso_formated_string() {
        assertThat(
            new DefaultTestMedia().withValue("date", LocalDate.of(1982, Month.JUNE, 9)).values(),
            hasEntry("date", "1982-06-09")
        );
    }

    @Test
    void should_write_offsettime_as_iso_formated_string() {
        assertThat(
            new DefaultTestMedia()
                .withValue("time", OffsetTime.of(LocalTime.of(8, 25, 55), ZoneOffset.ofHours(2)))
                .values(),
            hasEntry("time", "08:25:55+02:00")
        );
    }

    @Test
    void should_write_offsetdatetime_as_iso_formated_string() {
        assertThat(
            new DefaultTestMedia()
                .withValue(
                    "date",
                    OffsetDateTime.of(LocalDateTime.of(1982, Month.JUNE, 9, 8, 25, 55), ZoneOffset.ofHours(2))
                )
                .values(),
            hasEntry("date", "1982-06-09T08:25:55+02:00")
        );
    }

    static final class DefaultTestMedia implements Media<DefaultTestMedia> {

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
    }

    static final class Empty implements Printable {

        @Override
        public <T extends Media<T>> T printOn(final T media) {
            return media;
        }
    }
}
