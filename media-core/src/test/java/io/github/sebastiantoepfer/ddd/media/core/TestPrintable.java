package io.github.sebastiantoepfer.ddd.media.core;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import java.util.Map;

public class TestPrintable implements Printable {

    private final Map<String, String> values;

    public TestPrintable() {
        this(Map.of("test", "hello"));
    }

    public TestPrintable(final Map<String, String> values1) {
        values = values1;
    }

    @Override
    public <T extends Media<T>> T printOn(final T media) {
        T result = media;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.withValue(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
