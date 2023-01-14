package io.github.sebastiantoepfer.ddd.media.core.decorator;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ToCamelCaseTranslator implements Translator {

    private static String replaceWordDelimiters(final String withWordDelimiters) {
        final StringBuilder result = new StringBuilder();
        Function<Character, Character> transform = Character::toLowerCase;
        for (int i = 0; i < withWordDelimiters.length(); i++) {
            char currentChar = withWordDelimiters.charAt(i);
            if (isWordDelimiter(currentChar)) {
                transform = Character::toUpperCase;
            } else {
                result.append(transform.apply(currentChar));
                transform = Character::toLowerCase;
            }
        }
        return result.toString();
    }

    private static boolean containsWordDelimiter(final String translate) {
        return translate.chars().anyMatch(i -> isWordDelimiter((char) i));
    }

    private static boolean isWordDelimiter(final char currentChar) {
        return List.of(' ', '_').contains(currentChar);
    }

    @Override
    public Optional<String> translate(final String translate) {
        final String result;
        if (containsWordDelimiter(translate)) {
            result = replaceWordDelimiters(translate);
        } else {
            result = translate;
        }
        return Optional.of(result);
    }
}
