package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

final class ContainsChar implements Predicate<CharSequence> {

    private final IntPredicate contains;

    public ContainsChar(final Predicate<Character> contains) {
        this.contains = new CharIntPredicate(contains);
    }

    @Override
    public boolean test(final CharSequence sequence) {
        return sequence.chars().anyMatch(contains);
    }
}
