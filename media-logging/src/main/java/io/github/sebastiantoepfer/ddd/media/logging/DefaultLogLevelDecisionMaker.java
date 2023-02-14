package io.github.sebastiantoepfer.ddd.media.logging;

import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;

public class DefaultLogLevelDecisionMaker implements LogLevelDecisionMaker {

    private final String name;
    private final Function<Object, Level> decide;

    public DefaultLogLevelDecisionMaker(final String name, final Function<Object, Level> decide) {
        this.name = name;
        this.decide = decide;
    }

    @Override
    public Optional<Level> resolveLevel(final String name, final Object value) {
        final Optional<Level> result;
        if (this.name.equals(name)) {
            result = Optional.of(decide.apply(value));
        } else {
            result = Optional.empty();
        }
        return result;
    }
}
