package io.github.sebastiantoepfer.ddd.media.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PlaceholderExtractorTest {

    @Test
    void should_return_empty_without_any_placeholder() {
        assertThat(new PlaceholderExtractor("No placeholder!").findPlaceholders(), is(empty()));
    }

    @Test
    void should_throw_illegalargumentexception_with_incomplete_placeholder() {
        final var extractor = new PlaceholderExtractor("Hallo ${tes");
        assertThrows(IllegalArgumentException.class, () -> extractor.findPlaceholders());
    }

    @Test
    void should_find_one_named_placeholder() {
        assertThat(new PlaceholderExtractor("Hello ${name}!").findPlaceholders(), contains(new Placeholder("${name}")));
    }

    @Test
    void should_find_all_named_placeholder() {
        assertThat(
            new PlaceholderExtractor("Hello ${firstName} ${last_name}!").findPlaceholders(),
            contains(new Placeholder("${firstName}"), new Placeholder("${last_name}"))
        );
    }

    @Test
    void should_find_named_placeholder_with_dots() {
        assertThat(
            new PlaceholderExtractor("Hello ${person.name.first} ${person.name.last}!").findPlaceholders(),
            contains(new Placeholder("${person.name.first}"), new Placeholder("${person.name.last}"))
        );
    }

    @Test
    void sould_find_placeholders_with_styles() {
        assertThat(
            new PlaceholderExtractor("Hello I am ${age, number, integer} years old").findPlaceholders(),
            contains(new Placeholder("${age, number, integer}"))
        );
    }
}
