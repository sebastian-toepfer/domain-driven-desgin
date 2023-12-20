package io.github.sebastiantoepfer.ddd.media.json.util;

import static jakarta.json.stream.JsonCollectors.toJsonArray;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CollectionToJsonValueMapper implements ToJsonValueMapper {

    private final JsonProvider jsonProvider;
    private final Collection<Object> values;

    public CollectionToJsonValueMapper(final JsonProvider jsonProvider, final Collection<?> values) {
        this.jsonProvider = Objects.requireNonNull(jsonProvider);
        this.values = List.copyOf(values);
    }

    @Override
    public JsonArray asJsonValue() {
        return values
            .stream()
            .map(o -> new ObjectToJsonValueMapper(jsonProvider, o))
            .map(ToJsonValueMapper::asJsonValue)
            .filter(value -> value.getValueType() != JsonValue.ValueType.NULL)
            .collect(toJsonArray());
    }
}
