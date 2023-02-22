package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.function.Predicate;

final class EndsWith implements Predicate<CharSequence> {

    private final char endChar;

    public EndsWith(final char endChar) {
        this.endChar = endChar;
    }

    @Override
    public boolean test(final CharSequence sequence) {
        return !sequence.isEmpty() && sequence.charAt(sequence.length() - 1) == endChar;
    }
}
