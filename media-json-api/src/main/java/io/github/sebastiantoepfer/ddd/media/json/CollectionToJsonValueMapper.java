package io.github.sebastiantoepfer.ddd.media.json;

import static jakarta.json.stream.JsonCollectors.toJsonArray;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import java.util.Collection;
import java.util.List;

class CollectionToJsonValueMapper implements ToJsonValueMapper {

    private final Collection<Object> values;

    public CollectionToJsonValueMapper(final Collection<?> values) {
        this.values = List.copyOf(values);
    }

    @Override
    public JsonArray asJsonValue() {
        return values
            .stream()
            .map(ObjectToJsonValueMapper::new)
            .map(ToJsonValueMapper::asJsonValue)
            .filter(value -> value.getValueType() != JsonValue.ValueType.NULL)
            .collect(toJsonArray());
    }
}
