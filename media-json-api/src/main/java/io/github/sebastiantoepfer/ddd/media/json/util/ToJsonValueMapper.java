package io.github.sebastiantoepfer.ddd.media.json.util;

import jakarta.json.JsonValue;

public interface ToJsonValueMapper {
    JsonValue asJsonValue();
}
