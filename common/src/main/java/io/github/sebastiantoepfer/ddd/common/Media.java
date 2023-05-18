package io.github.sebastiantoepfer.ddd.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public interface Media<T extends Media<T>> {
    T withValue(String name, String value);

    T withValue(String name, int value);

    default T withValue(String name, BigInteger value) {
        return withValue(name, value.longValue());
    }

    T withValue(String name, long value);

    default T withValue(String name, BigDecimal value) {
        return withValue(name, value.doubleValue());
    }

    T withValue(String name, double value);

    T withValue(String name, boolean value);

    default T withValue(String name, Printable value) {
        return (T) this;
    }

    default T withValue(String name, Collection<?> values) {
        return (T) this;
    }

    default T withValue(String name, T value) {
        return (T) this;
    }
}
