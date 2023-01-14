package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

class TranslateNameDecoratorTest {

    @Test
    void should_add_string_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", "testValue")
                .decoratedMedia(),
            hasEntry("value", "testValue")
        );
    }

    @Test
    void should_add_string_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", "testValue")
                .decoratedMedia(),
            hasEntry("test", "testValue")
        );
    }

    @Test
    void should_add_int_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", 1)
                .decoratedMedia(),
            hasEntry("value", 1)
        );
    }

    @Test
    void should_add_int_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", 1)
                .decoratedMedia(),
            hasEntry("test", 1)
        );
    }

    @Test
    void should_add_long_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", 1L)
                .decoratedMedia(),
            hasEntry("value", 1L)
        );
    }

    @Test
    void should_add_long_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", 1L)
                .decoratedMedia(),
            hasEntry("test", 1L)
        );
    }

    @Test
    void should_add_double_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", 1.1)
                .decoratedMedia(),
            hasEntry("value", 1.1)
        );
    }

    @Test
    void should_add_double_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", 2.1)
                .decoratedMedia(),
            hasEntry("test", 2.1)
        );
    }

    @Test
    void should_add_bigdecimal_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", BigDecimal.TEN)
                .decoratedMedia(),
            hasEntry("value", BigDecimal.TEN)
        );
    }

    @Test
    void should_add_bigdecimal_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", BigDecimal.ONE)
                .decoratedMedia(),
            hasEntry("test", BigDecimal.ONE)
        );
    }

    @Test
    void should_add_biginteger_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", BigInteger.TEN)
                .decoratedMedia(),
            hasEntry("value", BigInteger.TEN)
        );
    }

    @Test
    void should_add_biginteger_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", BigInteger.ONE)
                .decoratedMedia(),
            hasEntry("test", BigInteger.ONE)
        );
    }

    @Test
    void should_add_boolean_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", Boolean.TRUE)
                .decoratedMedia(),
            hasEntry("value", Boolean.TRUE)
        );
    }

    @Test
    void should_add_boolean_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", Boolean.FALSE)
                .decoratedMedia(),
            hasEntry("test", Boolean.FALSE)
        );
    }

    @Test
    void should_add_sumbemedia_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "value")))
                .withValue("test", Boolean.TRUE)
                .decoratedMedia(),
            hasEntry("value", Boolean.TRUE)
        );
    }

    @Test
    void should_add_sumbmedia_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue(
                    "test",
                    new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                        .withValue("sub", "test")
                )
                .decoratedMedia(),
            hasEntry(is("test"), (Matcher) hasEntry("sub", "test"))
        );
    }

    @Test
    void should_add_sumbmedia_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "new_test")))
                .withValue(
                    "test",
                    new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                        .withValue("sub", "test")
                )
                .decoratedMedia(),
            hasEntry(is("new_test"), (Matcher) hasEntry("sub", "test"))
        );
    }

    @Test
    void should_add_printable_under_untranslated_name_into_decoraded_media_if_no_translation_is_avalible() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of()))
                .withValue("test", new TestPrintable())
                .decoratedMedia(),
            hasEntry(is("test"), isA(Map.class))
        );
    }

    @Test
    void should_add_printable_under_translated_name_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(new HashMapMedia(), new DefaultTranslator(Map.of("test", "new_test")))
                .withValue("test", new TestPrintable())
                .decoratedMedia(),
            hasEntry(is("new_test"), isA(Map.class))
        );
    }

    @Test
    void should_add_printable_with_translated_names_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(
                new HashMapMedia(),
                new DefaultTranslator(Map.of("test", "new_test", "sub", "new_sub"))
            )
                .withValue("test", new TestPrintable(Map.of("sub", "test")))
                .decoratedMedia(),
            hasEntry(is("new_test"), (Matcher) hasEntry("new_sub", "test"))
        );
    }

    @Test
    void should_add_collection_with_translated_names_into_decoraded_media() {
        assertThat(
            new TranslateNameDecorator<>(
                new HashMapMedia(),
                new DefaultTranslator(Map.of("test", "new_test", "sub", "new_sub"))
            )
                .withValue("test", List.of(new TestPrintable(Map.of("sub", "test"))))
                .decoratedMedia(),
            hasEntry(is("new_test"), (Matcher) contains(hasEntry("new_sub", "test")))
        );
    }
}
