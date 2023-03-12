package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import io.github.sebastiantoepfer.ddd.media.logging.BaseLogLevelDecision;
import io.github.sebastiantoepfer.ddd.media.logging.LogLevelDecision;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class DefaultLogLevelDescision extends BaseLogLevelDecision<Logger, Level> {

    public DefaultLogLevelDescision(final Level level, final String name, final Function<Object, Level> decide) {
        this(new FixedLogLevel(level), name, decide);
    }

    private DefaultLogLevelDescision(
        final LogLevelDecision<Logger> level,
        final String name,
        final Function<? super Object, Level> decide
    ) {
        super(level, name, decide);
    }

    @Override
    protected LogLevelDecision<Logger> create(
        final Level newLevel,
        final String name,
        final Function<Object, Level> decide
    ) {
        return new DefaultLogLevelDescision(new FixedLogLevel(newLevel), name, decide);
    }
}
