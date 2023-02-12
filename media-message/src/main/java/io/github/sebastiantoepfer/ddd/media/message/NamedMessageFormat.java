package io.github.sebastiantoepfer.ddd.media.message;

import java.util.Collection;
import java.util.Map;

public interface NamedMessageFormat {
    String format(final Map<String, Object> values);

    Collection<String> placeholderNames();
}
