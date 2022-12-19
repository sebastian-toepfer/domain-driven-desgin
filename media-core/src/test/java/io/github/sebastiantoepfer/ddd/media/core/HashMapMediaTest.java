package io.github.sebastiantoepfer.ddd.media.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hamcrest.Matcher;
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
    void should_add_printable() {
        assertThat(
            new HashMapMedia()
                .withValue(
                    "name",
                    new Printable() {
                        @Override
                        public <T extends Media<T>> T printOn(final T media) {
                            return media.withValue("test", "hello");
                        }
                    }
                ),
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
}
