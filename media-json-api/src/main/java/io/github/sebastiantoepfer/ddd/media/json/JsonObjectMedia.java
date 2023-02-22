package io.github.sebastiantoepfer.ddd.media.json;

import static jakarta.json.stream.JsonCollectors.toJsonArray;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public class JsonObjectMedia extends AbstractMap<String, JsonValue> implements Media<JsonObjectMedia>, JsonObject {

    private final JsonObject json;

    public JsonObjectMedia() {
        this(Json.createObjectBuilder());
    }

    private JsonObjectMedia(final JsonObjectBuilder builder) {
        this.json = builder.build();
    }

    @Override
    public JsonObjectMedia withValue(final String name, final String value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final int value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final long value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final double value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final BigDecimal value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final BigInteger value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final boolean value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final Printable value) {
        return withValue(name, value.printOn(new JsonObjectMedia()));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final Collection<?> values) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, asJsonArray(values)));
    }

    @Override
    public JsonObjectMedia withValue(final String name, final JsonObjectMedia value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
    }

    private static JsonArray asJsonArray(final Collection<?> values) {
        return values.stream().map(JsonObjectMedia::asJsonValue).filter(nonNull()).collect(toJsonArray());
    }

    private static Predicate<JsonValue> nonNull() {
        return value -> value.getValueType() != ValueType.NULL;
    }

    private static JsonValue asJsonValue(final Object o) {
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
            result = boolValue ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (o instanceof Printable printable) {
            result = printable.printOn(new JsonObjectMedia());
        } else {
            result = JsonValue.NULL;
        }
        return result;
    }

    @Override
    public String toString() {
        return json.toString();
    }

    @Override
    public JsonArray getJsonArray(final String string) {
        return json.getJsonArray(string);
    }

    @Override
    public JsonObject getJsonObject(final String string) {
        return json.getJsonObject(string);
    }

    @Override
    public JsonNumber getJsonNumber(final String string) {
        return json.getJsonNumber(string);
    }

    @Override
    public JsonString getJsonString(final String string) {
        return json.getJsonString(string);
    }

    @Override
    public String getString(final String string) {
        return json.getString(string);
    }

    @Override
    public String getString(final String string, final String string1) {
        return json.getString(string, string1);
    }

    @Override
    public int getInt(final String string) {
        return json.getInt(string);
    }

    @Override
    public int getInt(final String string, final int i) {
        return json.getInt(string, i);
    }

    @Override
    public boolean getBoolean(final String string) {
        return json.getBoolean(string);
    }

    @Override
    public boolean getBoolean(final String string, final boolean bln) {
        return json.getBoolean(string, bln);
    }

    @Override
    public boolean isNull(final String string) {
        return false;
    }

    @Override
    public ValueType getValueType() {
        return json.getValueType();
    }

    @Override
    public Set<Entry<String, JsonValue>> entrySet() {
        return json.entrySet();
    }
}
