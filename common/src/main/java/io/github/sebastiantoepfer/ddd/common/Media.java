package io.github.sebastiantoepfer.ddd.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public interface Media<T extends Media<T>> {
    default T withValue(String name, LocalDate value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_DATE));
    }

    T withValue(String name, LocalTime value);

    T withValue(String name, LocalDateTime value);

    default T withValue(String name, OffsetTime value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_TIME));
    }

    default T withValue(String name, OffsetDateTime value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    T withValue(String name, byte[] bytes);

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
