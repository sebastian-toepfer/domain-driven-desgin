package io.github.sebastiantoepfer.ddd.media.json;

import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

class ObjectToJsonValueMapper implements ToJsonValueMapper {

    private final Object o;

    public ObjectToJsonValueMapper(final Object o) {
        this.o = o;
    }

    @Override
    public JsonValue asJsonValue() {
        final JsonValue result;
        if (o instanceof String stringValue) {
            result = Json.createValue(stringValue);
        } else if (o instanceof Integer intValue) {
            result = Json.createValue(intValue);
        } else if (o instanceof Long longValue) {
            result = Json.createValue(longValue);
        } else if (o instanceof Double doubleValue) {
            result = Json.createValue(doubleValue);
        } else if (o instanceof Float floatValue) {
            result = Json.createValue(floatValue.doubleValue());
        } else if (o instanceof BigDecimal decimalValue) {
            result = Json.createValue(decimalValue);
        } else if (o instanceof BigInteger intValue) {
            result = Json.createValue(intValue);
        } else if (o instanceof Boolean boolValue) {
            result = Boolean.TRUE.equals(boolValue) ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (o instanceof Printable printable) {
            result = printable.printOn(new JsonObjectMedia());
        } else if (o instanceof Collection<?> col) {
            result = new CollectionToJsonValueMapper(col).asJsonValue();
        } else {
            result = JsonValue.NULL;
        }
        return result;
    }
}
