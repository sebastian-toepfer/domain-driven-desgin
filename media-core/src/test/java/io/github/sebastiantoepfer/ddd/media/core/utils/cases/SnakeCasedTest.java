package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.core.utils.cases.SnakeCased;
import org.junit.jupiter.api.Test;

class SnakeCasedTest {

    @Test
    void should_not_modfiy_already_snakecase_name() {
        assertThat(new SnakeCased(("a_snake")).toCase(), is("a_snake"));
    }

    @Test
    void should_insert_underscore_before_uppercase_and_convert_it_to_lower() {
        assertThat(new SnakeCased(("ASnake")).toCase(), is("a_snake"));
    }

    @Test
    void should_replace_space_with_underscore() {
        assertThat(new SnakeCased(("a snake")).toCase(), is("a_snake"));
    }

    @Test
    void should_not_insert_underscore_after_underscore() {
        assertThat(new SnakeCased(("a_Snake")).toCase(), is("a_snake"));
    }
}
