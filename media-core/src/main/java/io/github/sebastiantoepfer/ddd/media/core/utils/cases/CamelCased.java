package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.function.UnaryOperator;

public class CamelCased implements Cased {

    private final String value;

    public CamelCased(final String value) {
        this.value = value;
    }

    @Override
    public String toCase() {
        final String result;
        if (isAlreadyCamelCase()) {
            result = convert();
        } else {
            result = value;
        }
        return result;
    }

    private boolean isAlreadyCamelCase() {
        return new ContainsChar(new IsWordDelimter()).test(value);
    }

    private String convert() {
        final StringBuilder result = new StringBuilder();
        UnaryOperator<Character> transform = Character::toLowerCase;
        for (int i = 0; i < value.length(); i++) {
            char currentChar = value.charAt(i);
            if (new IsWordDelimter().test(currentChar)) {
                transform = Character::toUpperCase;
            } else {
                result.append(transform.apply(currentChar));
                transform = Character::toLowerCase;
            }
        }
        return result.toString();
    }
}
