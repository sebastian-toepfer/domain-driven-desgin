package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

final class IsChar implements Predicate<Character> {

    private final Collection<Character> chars;

    public IsChar(final Collection<Character> chars) {
        this.chars = Objects.requireNonNull(chars);
    }

    @Override
    public boolean test(final Character t) {
        return chars.contains(t);
    }
}
