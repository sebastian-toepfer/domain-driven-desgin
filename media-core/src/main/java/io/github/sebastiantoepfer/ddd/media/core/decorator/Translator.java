package io.github.sebastiantoepfer.ddd.media.core.decorator;

import java.util.Optional;

public interface Translator {
    Optional<String> translate(String translate);
}
