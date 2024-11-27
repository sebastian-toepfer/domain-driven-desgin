package io.github.sebastiantoepfer.ddd.media.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DefaultNamedMessageFormatTest {

    @Test
    void should_return_all_know_placeholdernames() {
        assertThat(
            new DefaultNamedMessageFormat("${name} -> ${test}").placeholderNames(),
            containsInAnyOrder("test", "name")
        );
    }

    @Test
    void should_format_message_without_style() {
        assertThat(
            new DefaultNamedMessageFormat("My name is ${name} and I am ${age} years old.", Locale.CANADA).format(
                Map.of("name", "Jane", "age", 42.34)
            ),
            is("My name is Jane and I am 42.34 years old.")
        );
    }

    @Test
    void should_format_message_with_style() {
        assertThat(
            new DefaultNamedMessageFormat("My name is ${name} and I am ${age, number, integer} years old.").format(
                Map.of("name", "Jane", "age", 42.34)
            ),
            is("My name is Jane and I am 42 years old.")
        );
    }
}
