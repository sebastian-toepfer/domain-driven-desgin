package io.github.sebastiantoepfer.ddd.media.json.printable;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

class JsonMappedValues {

    private final JsonValue value;

    public JsonMappedValues(final JsonValue value) {
        this.value = value;
    }

    public JsonMappedValue toMappedValue() {
        final JsonMappedValue result;
        if (value instanceof JsonString str) {
            result = new StringJsonMappedValue(str);
        } else if (value instanceof JsonNumber number) {
            if (number.isIntegral()) {
                result = new IntegerNumberJsonMappedValue(number);
            } else {
                result = new DecimalNumberJsonMappedValue(number);
            }
        } else if (value instanceof JsonArray array) {
            result = new ArrayJsonMappedValue(array);
        } else if (value instanceof JsonObject obj) {
            result = new ObjectJsonMappedValue(obj);
        } else if (value.getValueType() == JsonValue.ValueType.NULL) {
            result = new NullJsonMappedValue();
        } else {
            result = new BooleanJsonMappedValue(value.getValueType());
        }
        return result;
    }
}
