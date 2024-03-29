package io.github.sebastiantoepfer.ddd.media.json;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.json.printable.JsonMappedPrintables;
import jakarta.json.JsonObject;
import java.util.Objects;

public class JsonObjectPrintable implements Printable {

    private final JsonObject json;

    public JsonObjectPrintable(final JsonObject json) {
        this.json = Objects.requireNonNull(json);
    }

    @Override
    public <T extends Media<T>> T printOn(final T media) {
        return json
            .entrySet()
            .stream()
            .map(JsonMappedPrintables::new)
            .map(JsonMappedPrintables::toPrintable)
            .reduce(media, (m, p) -> p.printOn(m), (l, r) -> null);
    }
}
