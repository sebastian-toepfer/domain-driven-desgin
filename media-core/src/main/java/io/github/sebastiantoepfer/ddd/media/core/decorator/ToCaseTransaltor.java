package io.github.sebastiantoepfer.ddd.media.core.decorator;

import io.github.sebastiantoepfer.ddd.media.core.utils.cases.Cased;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ToCaseTransaltor implements Translator {

    private final Function<String, Cased> toCase;

    public ToCaseTransaltor(final Function<String, Cased> toCase) {
        this.toCase = Objects.requireNonNull(toCase);
    }

    @Override
    public Optional<String> translate(final String translate) {
        return Optional.of(toCase.andThen(Cased::toCase).apply(translate));
    }
}
