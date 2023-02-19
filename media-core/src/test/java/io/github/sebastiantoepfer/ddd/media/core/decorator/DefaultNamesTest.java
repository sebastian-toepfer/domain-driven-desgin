package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import org.junit.jupiter.api.Test;

class DefaultNamesTest {

    @Test
    void should_create_media_with_camelcase_names() {
        assertThat(
            DefaultNames.withCamelCaseNames(new HashMapMedia()).withValue("TEST_VALUE", "value").decoratedMedia(),
            hasEntry("testValue", "value")
        );
    }

    @Test
    void should_create_media_with_snakecase_names() {
        assertThat(
            DefaultNames.withSnakeCaseNames(new HashMapMedia()).withValue("testValue", "value").decoratedMedia(),
            hasEntry("test_value", "value")
        );
    }
}
