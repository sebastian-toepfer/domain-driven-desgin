package io.github.sebastiantoepfer.ddd.media.logging;

import java.util.logging.Logger;

public interface LogEntry {
    void logTo(Logger logger);
}
