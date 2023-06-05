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
package io.github.sebastiantoepfer.ddd.media.poi.spreadsheet;

import static io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.test.CellMatcher.cellWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.test.RowMatcher;
import io.github.sebastiantoepfer.ddd.printables.core.MapPrintable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

abstract class RowMediaTest {

    @Test
    void should_write_string_value_into_cell() {
        assertThat(createRowMedia().withValue("value", "test").sync(), contains(new RowMatcher(cellWith("test"))));
    }

    @Test
    void should_write_int_value_into_cell() {
        assertThat(createRowMedia().withValue("value", 8).sync(), contains(new RowMatcher(cellWith(8.0))));
    }

    @Test
    void should_write_long_value_into_cell() {
        assertThat(createRowMedia().withValue("value", 10L).sync(), contains(new RowMatcher(cellWith(10.0))));
    }

    @Test
    void should_write_double_value_into_cell() {
        assertThat(createRowMedia().withValue("value", 3.14).sync(), contains(new RowMatcher(cellWith(3.14))));
    }

    @Test
    void should_write_biginteger_value_into_cell() {
        assertThat(createRowMedia().withValue("value", BigInteger.TWO).sync(), contains(new RowMatcher(cellWith(2.0))));
    }

    @Test
    void should_write_bigdecimal_value_into_cell() {
        assertThat(
            createRowMedia().withValue("value", BigDecimal.valueOf(8.7162)).sync(),
            contains(new RowMatcher(cellWith(8.7162)))
        );
    }

    @Test
    void should_write_false_value_into_cell() {
        assertThat(
            createRowMedia().withValue("value", false).sync(),
            contains(new RowMatcher(cellWith(Boolean.FALSE)))
        );
    }

    @Test
    void should_write_true_value_into_cell() {
        assertThat(createRowMedia().withValue("value", true).sync(), contains(new RowMatcher(cellWith(Boolean.TRUE))));
    }

    @Test
    void make_pitest_happy() {
        final RowMedia media = createRowMedia();
        assertThat(media.withValue("value", List.of()), is(media));
        assertThat(media.withValue("value", new MapPrintable(Map.of())), is(media));
        assertThat(media.withValue("value", media), is(media));
    }

    abstract RowMedia createRowMedia();
}
