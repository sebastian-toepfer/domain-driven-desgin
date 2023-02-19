package io.github.sebastiantoepfer.ddd.media.json;

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
        return new JsonObjectMedia(
            Json.createObjectBuilder(json).add(name, new CollectionToJsonValueMapper(values).asJsonValue())
        );
    }

    @Override
    public JsonObjectMedia withValue(final String name, final JsonObjectMedia value) {
        return new JsonObjectMedia(Json.createObjectBuilder(json).add(name, value));
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
