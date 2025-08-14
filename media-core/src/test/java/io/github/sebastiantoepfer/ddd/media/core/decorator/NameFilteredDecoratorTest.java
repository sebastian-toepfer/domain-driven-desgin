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
package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

class NameFilteredDecoratorTest {

    @Test
    void should_not_add_filtered_string_value_into_media() {
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", "testValue")
                .withValue("value", "testValue")
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", "testValue"))
        );
    }

    @Test
    void should_only_add_filtered_string_value_into_media() {
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", "testValue")
                .withValue("value", "testValue")
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", "testValue"))
        );
    }

    @Test
    void should_not_add_filtered_localtime_value_into_media() {
        final LocalTime time = LocalTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", time)
                .withValue("value", time)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", time))
        );
    }

    @Test
    void should_only_add_filtered_localtime_value_into_media() {
        final LocalTime time = LocalTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", time)
                .withValue("value", time)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", time))
        );
    }

    @Test
    void should_not_add_filtered_localdate_value_into_media() {
        final LocalDate date = LocalDate.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", date)
                .withValue("value", date)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", date))
        );
    }

    @Test
    void should_only_add_filtered_localdate_value_into_media() {
        final LocalDate date = LocalDate.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", date)
                .withValue("value", date)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", date))
        );
    }

    @Test
    void should_not_add_filtered_localdatetime_value_into_media() {
        final LocalDateTime datetime = LocalDateTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", datetime)
                .withValue("value", datetime)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", datetime))
        );
    }

    @Test
    void should_only_add_filtered_localdatetime_value_into_media() {
        final LocalDateTime datetime = LocalDateTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", datetime)
                .withValue("value", datetime)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", datetime))
        );
    }

    @Test
    void should_not_add_filtered_offsettime_value_into_media() {
        final OffsetTime time = OffsetTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", time)
                .withValue("value", time)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", time))
        );
    }

    @Test
    void should_only_add_filtered_offsettime_value_into_media() {
        final OffsetTime time = OffsetTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", time)
                .withValue("value", time)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", time))
        );
    }

    @Test
    void should_not_add_filtered_offsetdatetime_value_into_media() {
        final OffsetDateTime time = OffsetDateTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", time)
                .withValue("value", time)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", time))
        );
    }

    @Test
    void should_only_add_filtered_offsetdatetime_value_into_media() {
        final OffsetDateTime datetime = OffsetDateTime.now();
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", datetime)
                .withValue("value", datetime)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", datetime))
        );
    }

    @Test
    void should_not_add_filtered_bytes_value_into_media() {
        final byte[] bytes = "bytes :)".getBytes(StandardCharsets.UTF_16);
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", bytes)
                .withValue("value", bytes)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", bytes))
        );
    }

    @Test
    void should_only_add_filtered_bytes_value_into_media() {
        final byte[] bytes = "bytes :)".getBytes(StandardCharsets.UTF_16);
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", bytes)
                .withValue("value", bytes)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", bytes))
        );
    }

    @Test
    void should_not_add_filtered_int_value_into_media() {
        final int value = 5;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_int_value_into_media() {
        final int value = 5;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_long_value_into_media() {
        final long value = 5L;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_long_value_into_media() {
        final long value = 5L;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_double_value_into_media() {
        final double value = 3.14;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_double_value_into_media() {
        final double value = 3.14;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_bigint_value_into_media() {
        final BigInteger value = BigInteger.TEN;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_bigint_value_into_media() {
        final BigInteger value = BigInteger.TEN;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_bigdec_value_into_media() {
        final BigDecimal value = BigDecimal.ZERO;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_bigdec_value_into_media() {
        final BigDecimal value = BigDecimal.TEN;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_true_value_into_media() {
        final boolean value = true;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_true_value_into_media() {
        final boolean value = true;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_false_value_into_media() {
        final boolean value = false;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_only_add_filtered_false_value_into_media() {
        final boolean value = false;
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", value))
        );
    }

    @Test
    void should_not_add_filtered_printable_value_into_media() {
        final Printable value = new TestPrintable(Map.of("name", "jane"));
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("value"), hasEntry("name", "jane")))
        );
    }

    @Test
    void should_only_add_filtered_printable_value_into_media() {
        final Printable value = new TestPrintable(Map.of("value", "jane"));
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("value"), hasEntry("value", "jane")))
        );
    }

    @Test
    void should_not_add_filtered_value_from_printable_value_into_media() {
        final Printable value = new TestPrintable(Map.of("test", "hello!"));
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("value", value)
                .decoratedMedia(),
            (Matcher) hasEntry(is("value"), not(hasKey("test")))
        );
    }

    @Test
    void should_not_add_filtered_collection_value_into_media() {
        final Collection<?> value = List.of("a", "b");
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("values", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("values"), contains("a", "b")))
        );
    }

    @Test
    void should_only_add_filtered_collection_value_into_media() {
        final Collection<?> value = List.of(new TestPrintable(Map.of("value", "jane", "test", "hello")));
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("value"), contains(hasEntry("value", "jane"))))
        );
    }

    @Test
    void should_not_add_filtered_submedia_value_into_media() {
        final NameFilteredDecorator<HashMapMedia> value = new NameFilteredDecorator<>(new HashMapMedia(), name ->
            true
        ).withValue("test", "test");
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> !name.equals("test"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("value"), hasEntry("test", "test")))
        );
    }

    @Test
    void should_only_add_filtered_submedia_value_into_media() {
        final NameFilteredDecorator<HashMapMedia> value = new NameFilteredDecorator<>(new HashMapMedia(), name ->
            true
        ).withValue("test", "test");
        assertThat(
            new NameFilteredDecorator<>(new HashMapMedia(), name -> name.equals("value"))
                .withValue("test", value)
                .withValue("value", value)
                .decoratedMedia(),
            both(not(hasKey("test"))).and((Matcher) hasEntry(is("value"), hasEntry("test", "test")))
        );
    }
}
