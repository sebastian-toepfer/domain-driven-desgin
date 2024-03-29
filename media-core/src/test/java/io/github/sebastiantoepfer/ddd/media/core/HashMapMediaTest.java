package io.github.sebastiantoepfer.ddd.media.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class HashMapMediaTest {

    @Test
    void should_add_string_value() {
        assertThat(new HashMapMedia().withValue("test", "test"), hasEntry("test", "test"));
    }

    @Test
    void should_add_integer_value() {
        assertThat(new HashMapMedia().withValue("test", 1), hasEntry("test", 1));
    }

    @Test
    void should_add_long_value() {
        assertThat(new HashMapMedia().withValue("test", 2L), hasEntry("test", 2L));
    }

    @Test
    void should_add_double_value() {
        assertThat(new HashMapMedia().withValue("test", 4.673), hasEntry("test", 4.673));
    }

    @Test
    void should_add_BigDecimal_value() {
        assertThat(new HashMapMedia().withValue("test", BigDecimal.TEN), hasEntry("test", BigDecimal.TEN));
    }

    @Test
    void should_add_BigInteger_value() {
        assertThat(new HashMapMedia().withValue("test", BigInteger.ONE), hasEntry("test", BigInteger.ONE));
    }

    @Test
    void should_add_boolean_value() {
        assertThat(new HashMapMedia().withValue("test", true), hasEntry("test", true));
    }

    @Test
    void should_add_localtime_value() {
        final LocalTime time = LocalTime.now();
        assertThat(new HashMapMedia().withValue("test", time), hasEntry("test", time));
    }

    @Test
    void should_add_localdate_value() {
        final LocalDate date = LocalDate.now();
        assertThat(new HashMapMedia().withValue("test", date), hasEntry("test", date));
    }

    @Test
    void should_add_localdatetime_value() {
        final LocalDateTime datetime = LocalDateTime.now();
        assertThat(new HashMapMedia().withValue("test", datetime), hasEntry("test", datetime));
    }

    @Test
    void should_add_offsetdatetime_value() {
        final OffsetDateTime datetime = OffsetDateTime.now();
        assertThat(new HashMapMedia().withValue("test", datetime), hasEntry("test", datetime));
    }

    @Test
    void should_add_offsettime_value() {
        final OffsetTime time = OffsetTime.now();
        assertThat(new HashMapMedia().withValue("test", time), hasEntry("test", time));
    }

    @Test
    void should_add_bytes_value() {
        final byte[] bytes = "Hello".getBytes(StandardCharsets.UTF_8);
        assertThat(new HashMapMedia().withValue("test", bytes), hasEntry("test", bytes));
    }

    @Test
    void should_add_printable() {
        assertThat(
            new HashMapMedia().withValue("name", new TestPrintable(Map.of("test", "hello"))),
            hasEntry(is("name"), (Matcher) hasEntry("test", "hello"))
        );
    }

    @Test
    void should_add_collection_value() {
        assertThat(new HashMapMedia().withValue("test", List.of(1, 2)), hasEntry(is("test"), (Matcher) contains(1, 2)));
    }

    @Test
    void should_add_another_hashmap_media() {
        assertThat(
            new HashMapMedia().withValue("test", new HashMapMedia().withValue("sub", "test")),
            hasEntry(is("test"), (Matcher) hasEntry("sub", "test"))
        );
    }

    @Test
    void sould_add_printables_from_collection_as_map_into_map() {
        assertThat(
            new HashMapMedia().withValue("test", List.of(new TestPrintable(Map.of("name", "value")))),
            hasEntry(is("test"), (Matcher) contains(hasEntry("name", "value")))
        );
    }

    @Test
    void should_return_default_subscriber() {
        assertThat(
            new HashMapMedia().byteValueSubscriber("test"),
            is(Matchers.instanceOf(DefaultMediaAwareSubscriber.class))
        );
    }
}
