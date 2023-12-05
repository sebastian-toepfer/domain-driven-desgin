package io.github.sebastiantoepfer.ddd.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Flow;

public interface Media<T extends Media<T>> {
    T withValue(String name, LocalDate value);

    T withValue(String name, LocalTime value);

    T withValue(String name, LocalDateTime value);

    T withValue(String name, OffsetTime value);

    T withValue(String name, OffsetDateTime value);

    T withValue(String name, byte[] bytes);

    T withValue(String name, String value);

    T withValue(String name, int value);

    T withValue(String name, BigInteger value);

    T withValue(String name, long value);

    T withValue(String name, BigDecimal value);

    T withValue(String name, double value);

    T withValue(String name, boolean value);

    T withValue(String name, Printable value);

    T withValue(String name, Collection<?> values);

    T withValue(String name, T value);

    MediaAwareSubscriber<T> byteValueSubscriber(String name);

    interface MediaAwareSubscriber<T extends Media<T>> extends Flow.Subscriber<List<ByteBuffer>> {
        T media();
    }
}
