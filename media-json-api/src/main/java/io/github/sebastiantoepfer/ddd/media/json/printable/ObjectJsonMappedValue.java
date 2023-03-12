package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.json.JsonObjectPrintable;
import jakarta.json.JsonObject;

class ObjectJsonMappedValue implements JsonMappedValue {

    private final JsonObject json;

    public ObjectJsonMappedValue(final JsonObject json) {
        this.json = json;
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
    public Printable toValue() {
        return new JsonObjectPrintable(json);
    }
}
