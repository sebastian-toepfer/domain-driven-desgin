package io.github.sebastiantoepfer.ddd.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public interface Media<T extends Media<T>> {
    T withValue(String name, String value);

    T withValue(String name, int value);

    T withValue(String name, long value);

    T withValue(String name, double value);

    T withValue(String name, BigDecimal value);

    T withValue(String name, BigInteger value);

    T withValue(String name, boolean value);

    T withValue(String name, Printable value);

    T withValue(String name, Collection<?> values);

    T withValue(String name, T value);
}
