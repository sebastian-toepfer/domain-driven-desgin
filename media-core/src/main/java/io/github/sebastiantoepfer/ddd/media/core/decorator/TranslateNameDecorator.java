package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.utils.PrintableToObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.Objects;

public final class TranslateNameDecorator<T extends Media<T>> implements MediaDecorator<T, TranslateNameDecorator<T>> {

    private final T decoratedMedia;
    private final Translator translator;

    public TranslateNameDecorator(final T decoratedMedia, final Translator translate) {
        this.decoratedMedia = Objects.requireNonNull(decoratedMedia);
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
                    values
                        .stream()
                        .map(PrintableToObjectMapper::new)
                        .map(mapper -> mapper.toValue(TranslatedNamePrintableDecorator::new))
                        .toList()
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

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final LocalTime value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final LocalDateTime value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final LocalDate value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final OffsetTime value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final OffsetDateTime value) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), value), translator);
    }

    @Override
    public TranslateNameDecorator<T> withValue(final String name, final byte[] bytes) {
        return new TranslateNameDecorator<>(decoratedMedia().withValue(translate(name), bytes), translator);
    }

    @Override
    public MediaAwareSubscriber<TranslateNameDecorator<T>> byteValueSubscriber(final String name) {
        throw new UnsupportedOperationException();
    }

    private String translate(final String name) {
        return translator.translate(name).orElse(name);
    }

    @Override
    public T decoratedMedia() {
        return decoratedMedia;
    }

    private class TranslatedNamePrintableDecorator implements Printable {

        private final Printable delegate;

        public TranslatedNamePrintableDecorator(final Printable delegate) {
            this.delegate = Objects.requireNonNull(delegate);
        }

        @Override
        public <M extends Media<M>> M printOn(final M media) {
            return delegate.printOn(new TranslateNameDecorator<>(media, translator)).decoratedMedia();
        }
    }
}
