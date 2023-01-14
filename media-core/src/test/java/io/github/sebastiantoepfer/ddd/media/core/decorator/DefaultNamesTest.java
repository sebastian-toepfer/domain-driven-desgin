package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;

import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class DefaultNamesTest {

    @Test
    void should_create_media_with_camelcase_names() {
        assertThat(
            DefaultNames.withCamelCaseNames(new HashMapMedia()).withValue("TEST_VALUE", "value").decoratedMedia(),
            Matchers.hasEntry("testValue", "value")
        );
    }

    @Test
    void should_create_media_with_snakecase_names() {
        assertThat(
            DefaultNames.withSnakeCaseNames(new HashMapMedia()).withValue("testValue", "value").decoratedMedia(),
            Matchers.hasEntry("test_value", "value")
        );
    }
}
