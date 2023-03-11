package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.JsonString;

class StringJsonMappedValue implements JsonMappedValue {

    private final JsonString value;

    public StringJsonMappedValue(final JsonString value) {
        this.value = value;
    }

    @Override
    public Printable toPrintable(final String name) {
        return new Printable() {
            @Override
            public <T extends Media<T>> T printOn(final T media) {
                return media.withValue(name, toValue());
            }
        };
    }

    @Override
    public String toValue() {
        return value.getString();
    }
}
