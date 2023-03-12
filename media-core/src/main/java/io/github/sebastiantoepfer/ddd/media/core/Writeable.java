package io.github.sebastiantoepfer.ddd.media.core;

import java.io.IOException;
import java.io.Writer;

public interface Writeable {
    Writer writeTo(Writer write) throws IOException;
}
