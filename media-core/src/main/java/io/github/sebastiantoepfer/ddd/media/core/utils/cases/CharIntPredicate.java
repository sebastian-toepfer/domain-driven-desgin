package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

final class CharIntPredicate implements IntPredicate {

    private final Predicate<Character> contains;

    public CharIntPredicate(final Predicate<Character> contains) {
        this.contains = Objects.requireNonNull(contains);
    }

    @Override
    public boolean test(final int i) {
        return contains.test((char) i);
    }
}
