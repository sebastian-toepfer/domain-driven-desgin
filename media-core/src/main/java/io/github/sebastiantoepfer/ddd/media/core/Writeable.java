package io.github.sebastiantoepfer.ddd.media.core;

import java.io.IOException;
import java.io.OutputStream;

public interface Writeable {
    void writeTo(OutputStream output) throws IOException;

    default String asString() throws IOException {
        return new StringWriteable(this).asString();
    }

    default byte[] asBytes() throws IOException {
        return new BytesWritebale(this).asBytes();
    }
}
