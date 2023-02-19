package io.github.sebastiantoepfer.ddd.media.json;

import jakarta.json.JsonValue;

public interface ToJsonValueMapper {
    JsonValue asJsonValue();
}
