/*
 * The MIT License
 *
 * Copyright 2023 sebastian.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.sebastiantoepfer.ddd.media.core;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Media.MediaAwareSubscriber;
import io.github.sebastiantoepfer.ddd.common.Printable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;

public interface BaseMedia<T extends BaseMedia<T>> extends Media<T> {
    @Override
    default T withValue(final String name, final LocalDate value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_DATE));
    }

    @Override
    default T withValue(final String name, final LocalTime time) {
        return withValue(
            name,
            OffsetTime.of(time, zoneId().getRules().getOffset(LocalDateTime.of(LocalDate.now(zoneId()), time)))
        );
    }

    @Override
    default T withValue(final String name, final LocalDateTime datetime) {
        return withValue(name, OffsetDateTime.of(datetime, zoneId().getRules().getOffset(datetime)));
    }

    @Override
    default T withValue(final String name, final OffsetTime value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_TIME));
    }

    @Override
    default T withValue(final String name, final OffsetDateTime value) {
        return withValue(name, value.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    default ZoneId zoneId() {
        return ZoneId.systemDefault();
    }

    @Override
    default T withValue(final String name, final BigInteger value) {
        return withValue(name, value.longValue());
    }

    @Override
    default T withValue(final String name, final BigDecimal value) {
        return withValue(name, value.doubleValue());
    }

    @Override
    default T withValue(final String name, final Printable value) {
        return (T) this;
    }

    @Override
    default T withValue(final String name, final Collection<?> values) {
        return (T) this;
    }

    @Override
    default T withValue(final String name, final T value) {
        return (T) this;
    }

    @Override
    default T withValue(final String name, final byte[] bytes) {
        return withValue("bytes", base64Encoder().encodeToString(bytes));
    }

    default Base64.Encoder base64Encoder() {
        return Base64.getMimeEncoder();
    }

    @Override
    default MediaAwareSubscriber<T> byteValueSubscriber(final String name) {
        return new DefaultMediaAwareSubscriber<>((T) this, name);
    }
}
