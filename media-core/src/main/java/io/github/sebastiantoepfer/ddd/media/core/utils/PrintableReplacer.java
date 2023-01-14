package io.github.sebastiantoepfer.ddd.media.core.utils;

import static java.util.stream.Collectors.toList;

import io.github.sebastiantoepfer.ddd.common.Printable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class PrintableReplacer {

    public final Collection<?> values;

    public PrintableReplacer(final Collection<?> values) {
        this.values = Objects.requireNonNull(values);
    }

    public Collection<?> replacePrintables(final Function<Printable, ?> replacer) {
        return values.stream().map(toValue(replacer)).collect(toList());
    }

    private static Function<Object, Object> toValue(final Function<Printable, ?> replacer) {
        return value -> {
            final Object result;
            if (value instanceof Printable printable) {
                result = replacer.apply(printable);
            } else {
                result = value;
            }
            return result;
        };
    }
}
