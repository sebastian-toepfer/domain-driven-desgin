package io.github.sebastiantoepfer.ddd.media.json.printable;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.JsonNumber;
import java.math.BigDecimal;

class DecimalNumberJsonMappedValue implements JsonMappedValue {

    private final JsonNumber value;

    public DecimalNumberJsonMappedValue(final JsonNumber value) {
        this.value = value;
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
    public BigDecimal toValue() {
        return value.bigDecimalValue();
    }
}
