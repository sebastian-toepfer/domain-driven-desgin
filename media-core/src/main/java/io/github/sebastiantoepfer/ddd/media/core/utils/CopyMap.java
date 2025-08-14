package io.github.sebastiantoepfer.ddd.media.core.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;

public class CopyMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private final Map<K, V> entries;

    public CopyMap(final Map<K, V> entries) {
        this.entries = Map.copyOf(entries);
    }

    private CopyMap<K, V> mergeWith(final CopyMap<K, V> map) {
        final Map<K, V> newValues = new HashMap<>(entries);
        newValues.putAll(map);
        return new CopyMap<>(newValues);
    }

    public CopyMap<K, V> withNewEntry(final Map.Entry<K, V> entry) {
        return withNewValue(entry.getKey(), entry.getValue());
    }

    public CopyMap<K, V> withNewValue(final K key, final V value) {
        final Map<K, V> newValues = new HashMap<>(entries);
        newValues.put(key, value);
        return new CopyMap<>(newValues);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries.entrySet();
    }

    @Override
    public int hashCode() {
        return (
            97 *
            entrySet()
                .stream()
                .mapToInt(entry -> Objects.hash(entry.getKey(), entry.getValue()))
                .sum()
        );
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractMap)) {
            return false;
        }
        final AbstractMap<?, ?> other = (AbstractMap<?, ?>) obj;
        return Objects.equals(entrySet(), other.entrySet());
    }

    public static class MergeOperator<K, V> implements BinaryOperator<CopyMap<K, V>> {

        @Override
        public CopyMap<K, V> apply(final CopyMap<K, V> t, final CopyMap<K, V> u) {
            return t.mergeWith(u);
        }
    }
}
