package io.github.sebastiantoepfer.ddd.media.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.message.Placeholder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PlaceholderTest {

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Placeholder.class).verify();
    }

    @Test
    void should_replace_with_simple_index() {
        assertThat(new Placeholder("${name}").replaceWithIndex(1).inside("Hello ${name}"), is("Hello {1}"));
    }

    @Test
    void should_replace_with_styled_index() {
        assertThat(
            new Placeholder("${name, number}").replaceWithIndex(1).inside("Hello ${name, number}"),
            is("Hello {1, number}")
        );
    }

    @Test
    void should_return_length() {
        assertThat(new Placeholder("name").length(), is(4));
    }
}
