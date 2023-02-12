package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.stream.Collectors.joining;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import io.github.sebastiantoepfer.ddd.media.core.utils.CopyMap;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MessageMedia implements Media<MessageMedia> {

    private final NamedMessageFormat format;
    private final CopyMap<String, Object> map;

    public MessageMedia(final NamedMessageFormat format) {
        this(format, new CopyMap<>(new HashMap<>()));
    }

    private MessageMedia(final NamedMessageFormat format, final CopyMap<String, Object> map) {
        this.format = format;
        this.map = map;
    }

    public Writer writeTo(final Writer write) throws IOException {
        write.write(format.format(map));
        return write;
    }

    @Override
    public String toString() {
        return "MessageMedia{" + "format=" + format + ", map=" + map + '}';
    }

    @Override
    public MessageMedia withValue(final String name, final String value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final int value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final long value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final double value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final BigDecimal value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final BigInteger value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final boolean value) {
        return new MessageMedia(format, map.withNewValue(name, value));
    }

    @Override
    public MessageMedia withValue(final String name, final Printable value) {
        return withValue(name, value.printOn(new HashMapMedia()));
    }

    @Override
    public MessageMedia withValue(final String name, final Collection<?> values) {
        return new MessageMedia(
            format,
            map.withNewValue(name, values.stream().map(Object::toString).collect(joining(", ")))
        );
    }

    @Override
    public MessageMedia withValue(final String name, final MessageMedia value) {
        return withValue(name, value.map);
    }

    private MessageMedia withValue(final String name, final Map<String, Object> values) {
        return new MessageMedia(
            format,
            values
                .entrySet()
                .stream()
                .map(entry -> Map.entry(String.format("%s.%s", name, entry.getKey()), entry.getValue()))
                .reduce(map, CopyMap::withNewEntry, new CopyMap.MergeOperator<>())
        );
    }
}
