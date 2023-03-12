package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Printable;

interface JsonMappedValue {
    Object toValue();

    Printable toPrintable(String name);
}
