package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.JsonArray;
import java.util.List;

class ArrayJsonMappedValue implements JsonMappedValue {

    private final JsonArray array;

    public ArrayJsonMappedValue(final JsonArray array) {
        this.array = array;
    }

    @Override
    public List<?> toValue() {
        return array
            .stream()
            .map(JsonMappedValues::new)
            .map(JsonMappedValues::toMappedValue)
            .map(JsonMappedValue::toValue)
            .toList();
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
