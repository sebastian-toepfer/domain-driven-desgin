package io.github.sebastiantoepfer.ddd.media.core;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.utils.CopyMap;
import io.github.sebastiantoepfer.ddd.media.core.utils.PrintableToObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapMedia extends AbstractMap<String, Object> implements BaseMedia<HashMapMedia> {

    private final CopyMap<String, Object> entries;

    public HashMapMedia() {
        this(new CopyMap<>(new HashMap<>()));
    }

    private HashMapMedia(final CopyMap<String, Object> map) {
        this.entries = map;
    }

    @Override
    public HashMapMedia withValue(final String name, final String value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final int value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final long value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final double value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final BigDecimal value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final BigInteger value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final boolean value) {
        return new HashMapMedia(entries.withNewValue(name, value));
    }

    @Override
    public HashMapMedia withValue(final String name, final Printable value) {
        return withValue(name, value.printOn(new HashMapMedia()));
    }

    @Override
    public HashMapMedia withValue(final String name, final Collection<?> values) {
        return new HashMapMedia(
            this.entries.withNewValue(
                    name,
                    values
                        .stream()
                        .map(PrintableToObjectMapper::new)
                        .map(mapper -> mapper.toValue(p -> p.printOn(new HashMapMedia())))
                        .toList()
                )
        );
    }

    @Override
    public HashMapMedia withValue(final String name, final HashMapMedia value) {
        return new HashMapMedia(entries.withNewValue(name, Map.copyOf(value)));
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return entries.entrySet();
    }
}
