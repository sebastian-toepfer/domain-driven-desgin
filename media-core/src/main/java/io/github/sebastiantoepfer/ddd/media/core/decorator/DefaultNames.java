package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;

public final class DefaultNames {

    public static <T extends Media<T>> MediaDecorator<T, TranslateNameDecorator<T>> withCamelCaseNames(final T media) {
        return new TranslateNameDecorator<>(media, new ToCamelCaseTranslator());
    }

    public static <T extends Media<T>> MediaDecorator<T, TranslateNameDecorator<T>> withSnakeCaseNames(final T media) {
        return new TranslateNameDecorator<>(media, new ToSnakeCaseTranslator());
    }

    private DefaultNames() {}
}
