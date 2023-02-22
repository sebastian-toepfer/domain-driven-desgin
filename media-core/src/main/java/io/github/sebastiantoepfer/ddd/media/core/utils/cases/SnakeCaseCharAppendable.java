package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.function.Predicate;

class SnakeCaseCharAppendable {

    private final char currentChar;

    public SnakeCaseCharAppendable(final char currentChar) {
        this.currentChar = currentChar;
    }

    public StringBuilder appendTo(final StringBuilder appendable) {
        if (shouldBeReplaceWithUnderscore()) {
            appendable.append('_');
        } else {
            if (shouldInsertUnderscore(appendable)) {
                appendable.append('_');
            }
            appendable.append(Character.toLowerCase(currentChar));
        }
        return appendable;
    }

    private boolean shouldBeReplaceWithUnderscore() {
        return currentChar == ' ';
    }

    private boolean shouldInsertUnderscore(final StringBuilder result) {
        return Character.isUpperCase(currentChar) && !result.isEmpty() && Predicate.not(new EndsWith('_')).test(result);
    }
}
