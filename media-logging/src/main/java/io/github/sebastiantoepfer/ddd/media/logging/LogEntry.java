package io.github.sebastiantoepfer.ddd.media.logging;

public interface LogEntry<T> {
    void logTo(T logger);
}
