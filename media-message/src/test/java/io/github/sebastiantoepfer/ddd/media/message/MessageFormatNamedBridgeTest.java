package io.github.sebastiantoepfer.ddd.media.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class MessageFormatNamedBridgeTest {

    @Test
    void should_return_all_know_placeholdernames() {
        assertThat(
            new MessageFormatNamedBridge(
                new MessageFormat("{0} -> {1}"),
                Map.of("test", 1, "name", 0)
            ).placeholderNames(),
            containsInAnyOrder("test", "name")
        );
    }

    @Test
    void should_not_be_createable_with_not_enough_nameIndices() {
        final MessageFormat format = new MessageFormat("{0}");
        final Map<String, Integer> nameIndices = Map.of();
        assertThat(
            assertThrows(
                IllegalArgumentException.class,
                () -> new MessageFormatNamedBridge(format, nameIndices)
            ).getMessage(),
            is("Not all placeholders have a name assignment!")
        );
    }

    @Test
    void should_format() {
        //we use SortedMap with different sort orders to ensure correct sorting :)
        final SortedMap<String, Integer> nameIndeices = new TreeMap(Comparator.reverseOrder());
        nameIndeices.putAll(Map.of("firstname", 1, "years", 0, "lastname", 2));
        assertThat(
            new MessageFormatNamedBridge(
                new MessageFormat("My name is {1} {2} and i am {0, number, integer} years old"),
                nameIndeices
            ).format(new TreeMap<>(Map.of("firstname", "Jane", "years", 31.6, "lastname", "Doe"))),
            is("My name is Jane Doe and i am 32 years old")
        );
    }
}
