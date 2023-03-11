package io.github.sebastiantoepfer.ddd.media.logging;

public class CanNotLog extends RuntimeException {

    public CanNotLog(final Throwable cause) {
        super(cause);
    }
}
