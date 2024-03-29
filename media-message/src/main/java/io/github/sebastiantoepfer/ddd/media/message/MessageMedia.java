package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.stream.Collectors.joining;

import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.core.BaseMedia;
import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import io.github.sebastiantoepfer.ddd.media.core.utils.CopyMap;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MessageMedia implements BaseMedia<MessageMedia>, Writeable {

    private final NamedMessageFormat format;
    private final CopyMap<String, Object> map;

    public MessageMedia(final NamedMessageFormat format) {
        this(format, new CopyMap<>(new HashMap<>()));
    }

    private MessageMedia(final NamedMessageFormat format, final CopyMap<String, Object> map) {
        this.format = format;
        this.map = map;
    }

    @Override
    public final void writeTo(final OutputStream write) throws IOException {
        final Writer osw = new OutputStreamWriter(write, StandardCharsets.UTF_8);
        osw.write(format.format(map));
        osw.flush();
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
