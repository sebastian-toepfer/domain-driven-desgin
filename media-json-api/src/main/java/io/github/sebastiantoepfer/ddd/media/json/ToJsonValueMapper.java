package io.github.sebastiantoepfer.ddd.media.json;

import jakarta.json.JsonValue;

interface ToJsonValueMapper {
    JsonValue asJsonValue();
}
