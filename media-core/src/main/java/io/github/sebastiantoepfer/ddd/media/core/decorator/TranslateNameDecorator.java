package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.utils.PrintableReplacer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

public class TranslateNameDecorator<T extends Media<T>> extends MediaDecorator<T, TranslateNameDecorator<T>> {

    private final Translator translator;

    public TranslateNameDecorator(final T decoratedMedia, final Translator translate) {
        super(decoratedMedia);
        this.translator = translate;
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final String value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final int value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final long value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final double value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final BigDecimal value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final BigInteger value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final boolean value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final Printable value) {
        return new TranslateNameDecorator<>(
            decoratedMedia().withValue(translate(name), new TranslatedNamePrintableDecorator(value)),
            translator
        );
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final Collection<?> values) {
        return new TranslateNameDecorator<>(
            decoratedMedia()
                .withValue(
                    translate(name),
                    new PrintableReplacer(values).replacePrintables(TranslatedNamePrintableDecorator::new)
                ),
            translator
        );
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final TranslateNameDecorator<T> value) {
        return new TranslateNameDecorator<>(
            decoratedMedia().withValue(translate(name), value.decoratedMedia()),
            translator
        );
    }

    private String translate(final String name) {
        return translator.translate(name).orElse(name);
    }

    private class TranslatedNamePrintableDecorator implements Printable {

        private final Printable delegate;

        public TranslatedNamePrintableDecorator(final Printable delegate) {
            this.delegate = Objects.requireNonNull(delegate);
        }

        @Override
        public <T extends Media<T>> T printOn(final T media) {
            return delegate.printOn(new TranslateNameDecorator<>(media, translator)).decoratedMedia();
        }
    }
}
