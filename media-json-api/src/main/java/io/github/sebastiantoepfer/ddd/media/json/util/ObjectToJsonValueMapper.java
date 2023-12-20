package io.github.sebastiantoepfer.ddd.media.json.util;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.json.JsonObjectMedia;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public class ObjectToJsonValueMapper implements ToJsonValueMapper {

    private final JsonProvider jsonProvider;
    private final Object o;

    public ObjectToJsonValueMapper(final JsonProvider jsonProvider, final Object o) {
        this.jsonProvider = jsonProvider;
        this.o = o;
    }

    @Override
    public JsonValue asJsonValue() {
        final JsonValue result;
        if (o instanceof String stringValue) {
            result = jsonProvider.createValue(stringValue);
        } else if (o instanceof Integer intValue) {
            result = jsonProvider.createValue(intValue);
        } else if (o instanceof Long longValue) {
            result = jsonProvider.createValue(longValue);
        } else if (o instanceof Double doubleValue) {
            result = jsonProvider.createValue(doubleValue);
        } else if (o instanceof Float floatValue) {
            result = jsonProvider.createValue(floatValue.doubleValue());
        } else if (o instanceof BigDecimal decimalValue) {
            result = jsonProvider.createValue(decimalValue);
        } else if (o instanceof BigInteger intValue) {
            result = jsonProvider.createValue(intValue);
        } else if (o instanceof Boolean boolValue) {
            result = Boolean.TRUE.equals(boolValue) ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (o instanceof Printable printable) {
            result = printable.printOn(new JsonObjectMedia());
        } else if (o instanceof Collection<?> col) {
            result = new CollectionToJsonValueMapper(jsonProvider, col).asJsonValue();
        } else {
            result = JsonValue.NULL;
        }
        return result;
    }
}
