package io.github.sebastiantoepfer.ddd.media.logging;

import io.github.sebastiantoepfer.ddd.media.core.Writeable;

public interface LogLevelDecision<T> {
    LogLevelDecision<T> resolveLogLevelDecision(final String name, final Object value);

    LogEntry<T> logEnty(Writeable w);
}
