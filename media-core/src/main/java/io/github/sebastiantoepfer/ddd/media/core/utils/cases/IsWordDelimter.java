package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

import java.util.List;
import java.util.function.Predicate;

final class IsWordDelimter implements Predicate<Character> {

    @Override
    public boolean test(final Character t) {
        return new IsChar(List.of(' ', '_')).test(t);
    }
}
