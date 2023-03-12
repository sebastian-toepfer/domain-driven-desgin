package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseLogLevelDecision<T, L> implements LogLevelDecision<T> {

    private final LogLevelDecision<T> level;
    private final String name;
    private final Function<Object, L> decide;

    protected BaseLogLevelDecision(
        final LogLevelDecision<T> level,
        final String name,
        final Function<Object, L> decide
    ) {
        this.level = Objects.requireNonNull(level);
        this.name = Objects.requireNonNull(name);
        this.decide = Objects.requireNonNull(decide);
    }

    @Override
    public LogLevelDecision<T> resolveLogLevelDecision(final String name, final Object value) {
        final LogLevelDecision<T> result;
        if (this.name.equals(name)) {
            result = create(decide.apply(value), name, decide);
        } else {
            result = this;
        }
        return result;
    }

    @Override
    public LogEntry<T> logEnty(final Writeable writeable) {
        return level.logEnty(writeable);
    }

    protected abstract LogLevelDecision<T> create(L newLevel, String name, Function<Object, L> decide);
}
