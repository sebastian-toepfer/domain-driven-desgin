package io.github.sebastiantoepfer.ddd.media.logging.jul;

import io.github.sebastiantoepfer.ddd.media.logging.BaseLogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultLogLevelDecision extends BaseLogLevelDecision<Logger, Level> {

    public DefaultLogLevelDecision(final Level level, final String name, final Function<Object, Level> decide) {
        this(new FixedLogLevel(level), name, decide);
    }

    private DefaultLogLevelDecision(
        final FixedLogLevel level,
        final String name,
        final Function<Object, Level> decide
    ) {
        super(level, name, decide);
    }

    @Override
    protected LogLevelDecision<Logger> create(
        final Level newLevel,
        final String name,
        final Function<Object, Level> decide
    ) {
        return new DefaultLogLevelDecision(new FixedLogLevel(newLevel), name, decide);
    }
}
