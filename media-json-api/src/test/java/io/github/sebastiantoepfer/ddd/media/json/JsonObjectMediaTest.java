package io.github.sebastiantoepfer.ddd.media.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;

class JsonObjectMediaTest {

    @Test
    void should_be_empty_without_any_value() {
        assertThat(new JsonObjectMedia().isEmpty(), is(true));
    }

    @Test
    void should_be_a_jsonobject() {
        assertThat(new JsonObjectMedia().getValueType(), is(JsonValue.ValueType.OBJECT));
    }

    @Test
    void should_create_simple_jsonobject_with_string_property() {
        assertThat(
            new JsonObjectMedia().withValue("test", "test"),
            is(Json.createObjectBuilder().add("test", "test").build())
        );
    }

    @Test
    void should_create_simple_jsonobject_with_json_property() {
        assertThat(new JsonObjectMedia().withValue("test", 1), is(Json.createObjectBuilder().add("test", 1).build()));
    }

    @Test
    void should_create_with_sub_value() {
        assertThat(
            new JsonObjectMedia()
                .withValue("test", new JsonObjectMedia().withValue("sub", "test"))
                .getJsonObject("test"),
            is(Json.createObjectBuilder().add("sub", "test").build())
        );
    }

    @Test
    void should_create_int_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(1, 2)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(1).add(2).build())
        );
    }

    @Test
    void should_create_long_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(1L, 2L)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(1L).add(2L).build())
        );
    }

    @Test
    void should_create_double_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(1.2, 2.4)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(1.2).add(2.4).build())
        );
    }

    @Test
    void should_create_float_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(1.2f, 2.4f)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(1.2f).add(2.4f).build())
        );
    }

    @Test
    void should_create_bigdecimal_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(BigDecimal.ONE, BigDecimal.TEN)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(BigDecimal.ONE).add(BigDecimal.TEN).build())
        );
    }

    @Test
    void should_create_biginteger_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(BigInteger.ONE, BigInteger.TEN)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(BigInteger.ONE).add(BigInteger.TEN).build())
        );
    }

    @Test
    void should_create_printable_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(testPrintable())).getJsonArray("array"),
            is(Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", "value")).build())
        );
    }

    @Test
    void should_create_boolean_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(true, false, true)).getJsonArray("array"),
            is(Json.createArrayBuilder().add(true).add(false).add(true).build())
        );
    }

    @Test
    void should_create_string_collection_as_array() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of("test", "name")).getJsonArray("array"),
            is(Json.createArrayBuilder().add("test").add("name").build())
        );
    }

    @Test
    void should_create_collection_containing_unkown_types_as_emptyarray() {
        assertThat(
            new JsonObjectMedia().withValue("array", List.of(new Object(), new Object())).getJsonArray("array"),
            is(Json.createArrayBuilder().build())
        );
    }

    @Test
    void should_create_with_printable_as_object() {
        assertThat(
            new JsonObjectMedia().withValue("test", testPrintable()).getJsonObject("test"),
            is(Json.createObjectBuilder().add("name", "value").build())
        );
    }

    private Printable testPrintable() {
        return new Printable() {
            @Override
            public <T extends Media<T>> T printOn(final T media) {
                return media.withValue("name", "value");
            }
        };
    }

    @Test
    void should_correctly_override_jsonobject() {
        final JsonObjectMedia media = new JsonObjectMedia()
            .withValue("string", "value")
            .withValue("int", 1)
            .withValue("long", Long.MAX_VALUE)
            .withValue("double", 2.8)
            .withValue("float", 1.0f)
            .withValue("bigDecimal", BigDecimal.TEN)
            .withValue("bigInteger", BigInteger.TWO)
            .withValue("boolean", Boolean.FALSE)
            .withValue("true_boolean", Boolean.TRUE);

        assertThat(media.isNull("string"), is(false));

        assertThat(media.getJsonString("string"), is(Json.createValue("value")));
        assertThat(media.getString("string"), is("value"));
        assertThat(media.getString("string", "other"), is("value"));
        assertThat(media.getString("other_string", "other"), is("other"));

        assertThat(media.getInt("int", 5), is(1));
        assertThat(media.getJsonNumber("int"), is(Json.createValue(1)));
        assertThat(media.getInt("int"), is(1));
        assertThat(media.getInt("other_int", 5), is(5));

        assertThat(media.getJsonNumber("long"), is(Json.createValue(Long.MAX_VALUE)));
        assertThat(media.getJsonNumber("double"), is(Json.createValue(2.8)));
        assertThat(media.getJsonNumber("float"), is(Json.createValue(1.0)));
        assertThat(media.getJsonNumber("bigDecimal"), is(Json.createValue(BigDecimal.TEN)));
        assertThat(media.getJsonNumber("bigInteger"), is(Json.createValue(BigInteger.TWO)));

        assertThat(media.getBoolean("boolean"), is(false));
        assertThat(media.getBoolean("true_boolean"), is(true));
        assertThat(media.getBoolean("boolean", Boolean.TRUE), is(false));
        assertThat(media.getBoolean("other_boolean", Boolean.FALSE), is(false));
        assertThat(media.getBoolean("other_boolean", Boolean.TRUE), is(true));
    }

    @Test
    void should_generate_correct_jsonstring() {
        assertThat(
            new JsonObjectMedia().withValue("name", "name").toString(),
            is(Json.createObjectBuilder().add("name", "name").build().toString())
        );
    }
}
