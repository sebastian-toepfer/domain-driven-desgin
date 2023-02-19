package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.core.utils.cases.CamelCased;
import org.junit.jupiter.api.Test;

class CamelCasedTest {

    @Test
    void should_not_modfiy_already_camelcased_value() {
        assertThat(new CamelCased("givenName").toCase(), is("givenName"));
    }

    @Test
    void should_replace_character_after_underscore_with_uppercase_and_remove_underscore() {
        assertThat(new CamelCased("GIVEN_name").toCase(), is("givenName"));
    }

    @Test
    void should_replace_character_after_space_with_uppercase_and_remove_space() {
        assertThat(new CamelCased("GIVEN name").toCase(), is("givenName"));
    }
}
