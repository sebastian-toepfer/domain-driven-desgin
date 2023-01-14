package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class ToCamelCaseTranslatorTest {

    private final ToCamelCaseTranslator translator;

    public ToCamelCaseTranslatorTest() {
        this.translator = new ToCamelCaseTranslator();
    }

    @Test
    void should_not_modfiy_already_camelcase_name() {
        assertThat(translator.translate("givenName"), isPresentAndIs("givenName"));
    }

    @Test
    void should_replace_character_after_underscore_with_uppercase_and_remove_underscore() {
        assertThat(translator.translate("GIVEN_name"), isPresentAndIs("givenName"));
    }

    @Test
    void should_replace_character_after_space_with_uppercase_and_remove_space() {
        assertThat(translator.translate("GIVEN name"), isPresentAndIs("givenName"));
    }
}
