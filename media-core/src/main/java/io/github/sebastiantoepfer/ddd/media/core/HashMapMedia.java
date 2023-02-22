package io.github.sebastiantoepfer.ddd.media.core;

import static java.util.Map.copyOf;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.utils.PrintableToObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapMedia extends AbstractMap<String, Object> implements Media<HashMapMedia> {

    private final Map<String, Object> values;

    public HashMapMedia() {
        this(new HashMap<>());
    }

    private HashMapMedia(final Map<String, Object> map) {
        this.values = map;
    }

    @Override
    public HashMapMedia withValue(final String name, final String value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final int value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final long value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final double value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final BigDecimal value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final BigInteger value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final boolean value) {
        return createCopyWithNewValue(name, value);
    }

    @Override
    public HashMapMedia withValue(final String name, final Printable value) {
        return withValue(name, value.printOn(new HashMapMedia()));
    }

    @Override
    public HashMapMedia withValue(final String name, final Collection<?> values) {
        return createCopyWithNewValue(
            name,
            values
                .stream()
                .map(PrintableToObjectMapper::new)
                .map(mapper -> mapper.toValue(p -> p.printOn(new HashMapMedia())))
                .toList()
        );
    }

    @Override
    public HashMapMedia withValue(final String name, final HashMapMedia value) {
        return createCopyWithNewValue(name, copyOf(value));
    }

    private HashMapMedia createCopyWithNewValue(final String name, final Object value) {
        final Map<String, Object> newValues = new HashMap<>(values);
        newValues.put(name, value);
        return new HashMapMedia(newValues);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return values.entrySet();
    }
}
