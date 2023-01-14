package io.github.sebastiantoepfer.ddd.media.core.decorator;

import java.util.Map;
import java.util.Optional;

public class DefaultTranslator implements Translator {

    private final Map<String, String> knowTraanslations;

    public DefaultTranslator(final Map<String, String> knowTranslations) {
        this.knowTraanslations = Map.copyOf(knowTranslations);
    }

    @Override
    public Optional<String> translate(final String translate) {
        return Optional.ofNullable(knowTraanslations.get(translate));
    }
}
