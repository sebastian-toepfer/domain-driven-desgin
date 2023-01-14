package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;

import com.github.npathai.hamcrestopt.OptionalMatchers;
import org.junit.jupiter.api.Test;

class ToSnakeCaseTranslatorTest {

    private final ToSnakeCaseTranslator translator;

    public ToSnakeCaseTranslatorTest() {
        translator = new ToSnakeCaseTranslator();
    }

    @Test
    void should_not_modfiy_already_snakecase_name() {
        assertThat(translator.translate("a_snake"), OptionalMatchers.isPresentAndIs("a_snake"));
    }

    @Test
    void should_insert_underscore_before_uppercase_and_convert_it_to_lower() {
        assertThat(translator.translate("ASnake"), OptionalMatchers.isPresentAndIs("a_snake"));
    }

    @Test
    void should_replace_space_with_underscore() {
        assertThat(translator.translate("a snake"), OptionalMatchers.isPresentAndIs("a_snake"));
    }

    @Test
    void should_not_insert_underscore_after_underscore() {
        assertThat(translator.translate("a_Snake"), OptionalMatchers.isPresentAndIs("a_snake"));
    }
}
