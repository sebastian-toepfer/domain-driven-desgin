/*
 * The MIT License
 *
 * Copyright 2023 sebastian.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.sebastiantoepfer.ddd.printables.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

import io.github.sebastiantoepfer.ddd.printables.core.test.TestMedia;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class NamedListPrintableTest {

    @Test
    void should_print_printables_under_a_given_name() {
        assertThat(
            new NamedListPrintable(
                "test",
                new MapPrintable(Map.of("name", "jane")),
                new MapPrintable(Map.of("name", "maura"))
            ).printOn(new TestMedia()),
            hasEntry("test", "[{name=jane}, {name=maura}]")
        );
    }

    @Test
    void should_print_a_list_of_printables_under_a_given_name() {
        assertThat(
            new NamedListPrintable(
                "test",
                List.of(new MapPrintable(Map.of("name", "jane")), new MapPrintable(Map.of("name", "maura")))
            ).printOn(new TestMedia()),
            hasEntry("test", "[{name=jane}, {name=maura}]")
        );
    }
}
