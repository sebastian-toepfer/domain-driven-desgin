package io.github.sebastiantoepfer.ddd.media.core.decorator;

import java.util.Optional;

public class ToSnakeCaseTranslator implements Translator {

    private static String convertToSnakeCase(final String value) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            appendCharTo(result, value.charAt(i));
        }
        return result.toString();
    }

    private static void appendCharTo(final StringBuilder result, final char currentChar) {
        if (shouldBeReplaceWithUnderscore(currentChar)) {
            result.append('_');
        } else {
            if (shouldInsertUnderscore(currentChar, result)) {
                result.append('_');
            }
            result.append(Character.toLowerCase(currentChar));
        }
    }

    private static boolean shouldBeReplaceWithUnderscore(final char currentChar) {
        return currentChar == ' ';
    }

    private static boolean shouldInsertUnderscore(final char currentChar, final StringBuilder result) {
        return Character.isUpperCase(currentChar) && previousIsNotUnderscore(result);
    }

    private static boolean previousIsNotUnderscore(final StringBuilder result) {
        return result.lastIndexOf("_") < result.length() - 1;
    }

    @Override
    public Optional<String> translate(final String translate) {
        return Optional.of(convertToSnakeCase(translate));
    }
}
