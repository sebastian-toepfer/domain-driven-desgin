package io.github.sebastiantoepfer.ddd.media.core.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CopyMapTest {

    @Test
    @SuppressWarnings("EqualsIncompatibleType")
    void equalsContract() {
        assertThat(new CopyMap<>(new HashMap<>()).equals(new CopyMap<>(new HashMap<>())), is(true));
        assertThat(new CopyMap<>(Map.of("a", "a")).equals(new CopyMap<>(Map.of("a", "a"))), is(true));
        final CopyMap<String, String> same = new CopyMap<>(Map.of("a", "a"));
        assertThat(same.equals(same), is(true));

        assertThat(new CopyMap<>(Map.of("a", "a")).equals(Map.of("a", "a")), is(true));
        assertThat(Map.of("a", "a").equals(new CopyMap<>(Map.of("a", "a"))), is(true));

        assertThat(new CopyMap<>(Map.of("a", "a")).equals(new CopyMap<>(Map.of("b", "b"))), is(false));
        assertThat(new CopyMap<>(Map.of("a", "a")).equals(null), is(false));
        assertThat(new CopyMap<>(Map.of("a", "a")).equals("hello"), is(false));

        assertThat(new CopyMap<>(Map.of("a", "a")).hashCode(), is(new CopyMap<>(Map.of("a", "a")).hashCode()));
        assertThat(new CopyMap<>(Map.of("a", "a")).hashCode(), is(not(new CopyMap<>(Map.of("b", "b")).hashCode())));
    }

    @Test
    void should_merge_with_other() {
        assertThat(
            (Map<String, String>) new CopyMap.MergeOperator().apply(
                new CopyMap<>(Map.of("a", "a")),
                new CopyMap<>(Map.of("b", "b"))
            ),
            allOf(hasEntry("a", "a"), hasEntry("b", "b"))
        );
    }

    @Test
    void should_create_with_new_entry() {
        assertThat(new CopyMap<>(Map.of()).withNewEntry(Map.entry("a", "b")), hasEntry("a", "b"));
    }
}
