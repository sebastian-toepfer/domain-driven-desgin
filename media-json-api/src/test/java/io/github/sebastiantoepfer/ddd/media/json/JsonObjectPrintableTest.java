package io.github.sebastiantoepfer.ddd.media.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import jakarta.json.Json;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

class JsonObjectPrintableTest {

    @Test
    void should_print_string_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().add("name", "Jane").build()).printOn(new HashMapMedia()),
            hasEntry("name", "Jane")
        );
    }

    @Test
    void should_print_number_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().add("name", 234).build()).printOn(new HashMapMedia()),
            hasEntry("name", BigInteger.valueOf(234L))
        );
    }

    @Test
    void should_print_decimalnumber_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().add("name", 234.27261).build()).printOn(
                new HashMapMedia()
            ),
            hasEntry("name", BigDecimal.valueOf(234.27261))
        );
    }

    @Test
    void should_print_array_property() {
        assertThat(
            new JsonObjectPrintable(
                Json.createObjectBuilder().add("name", Json.createArrayBuilder().add("Test")).build()
            ).printOn(new HashMapMedia()),
            (Matcher) hasEntry(is("name"), contains("Test"))
        );
    }

    @Test
    void should_print_true_boolean_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().add("name", true).build()).printOn(new HashMapMedia()),
            (Matcher) hasEntry(is("name"), is(true))
        );
    }

    @Test
    void should_print_false_boolean_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().add("name", false).build()).printOn(new HashMapMedia()),
            (Matcher) hasEntry(is("name"), is(false))
        );
    }

    @Test
    void should_print_object_property() {
        assertThat(
            new JsonObjectPrintable(
                Json.createObjectBuilder().add("name", Json.createObjectBuilder().add("test", BigDecimal.ONE)).build()
            ).printOn(new HashMapMedia()),
            (Matcher) hasEntry(is("name"), hasEntry("test", BigInteger.valueOf(1L)))
        );
    }

    @Test
    void should_not_print_null_property() {
        assertThat(
            new JsonObjectPrintable(Json.createObjectBuilder().addNull("test").build())
                .printOn(new HashMapMedia())
                .entrySet(),
            is(empty())
        );
    }
}
