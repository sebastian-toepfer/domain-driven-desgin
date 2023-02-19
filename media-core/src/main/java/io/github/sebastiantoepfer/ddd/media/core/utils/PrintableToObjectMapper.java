package io.github.sebastiantoepfer.ddd.media.core.utils;

import io.github.sebastiantoepfer.ddd.common.Printable;
import java.util.function.Function;

public class PrintableToObjectMapper {

    private final Object value;

    public PrintableToObjectMapper(final Object value) {
        this.value = value;
    }

    public Object toValue(final Function<Printable, ?> mapper) {
        final Object result;
        if (value instanceof Printable printable) {
            result = mapper.apply(printable);
        } else {
            result = value;
        }
        return result;
    }
}
