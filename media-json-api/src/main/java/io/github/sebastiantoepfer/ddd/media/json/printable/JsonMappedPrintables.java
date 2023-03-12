package io.github.sebastiantoepfer.ddd.media.json.printable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.JsonValue;
import java.util.Map;

public class JsonMappedPrintables {

    private final Map.Entry<String, JsonValue> entry;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public JsonMappedPrintables(final Map.Entry<String, JsonValue> entry) {
        this.entry = entry;
    }

    public Printable toPrintable() {
        return new JsonMappedValues(entry.getValue()).toMappedValue().toPrintable(entry.getKey());
    }
}
