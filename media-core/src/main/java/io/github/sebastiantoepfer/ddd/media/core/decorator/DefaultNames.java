package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.media.core.utils.cases.CamelCased;
import io.github.sebastiantoepfer.ddd.media.core.utils.cases.SnakeCased;

public final class DefaultNames {

    public static <T extends Media<T>> MediaDecorator<T, TranslateNameDecorator<T>> withCamelCaseNames(final T media) {
        return new TranslateNameDecorator<>(media, new ToCaseTransaltor(CamelCased::new));
    }

    public static <T extends Media<T>> MediaDecorator<T, TranslateNameDecorator<T>> withSnakeCaseNames(final T media) {
        return new TranslateNameDecorator<>(media, new ToCaseTransaltor(SnakeCased::new));
    }

    private DefaultNames() {}
}
