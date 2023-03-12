package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.JsonValue;

class BooleanJsonMappedValue implements JsonMappedValue {

    private final JsonValue.ValueType value;

    public BooleanJsonMappedValue(final JsonValue.ValueType value) {
        this.value = value;
    }

    @Override
    public Boolean toValue() {
        return value == JsonValue.ValueType.TRUE;
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
}
