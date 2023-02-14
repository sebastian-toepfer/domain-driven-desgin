package io.github.sebastiantoepfer.ddd.media.logging;

import java.util.Optional;
import java.util.logging.Level;

public interface LogLevelDecisionMaker {
    Optional<Level> resolveLevel(final String name, final Object value);
}
