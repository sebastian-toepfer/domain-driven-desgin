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
package io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.test;

import java.util.Objects;
import java.util.function.Function;
import org.apache.poi.ss.usermodel.Cell;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class CellMatcher<T> extends TypeSafeMatcher<Cell> {

    public static CellMatcher<String> cellWith(final String value) {
        return new CellMatcher<>(value, Cell::getStringCellValue);
    }

    public static CellMatcher<Double> cellWith(final Double value) {
        return new CellMatcher<>(value, Cell::getNumericCellValue);
    }

    public static CellMatcher<Boolean> cellWith(final Boolean value) {
        return new CellMatcher<>(value, Cell::getBooleanCellValue);
    }

    private final T value;
    private final Function<Cell, T> cellValue;

    private CellMatcher(final T value, final Function<Cell, T> cellValue) {
        this.value = Objects.requireNonNull(value);
        this.cellValue = Objects.requireNonNull(cellValue);
    }

    @Override
    protected boolean matchesSafely(final Cell item) {
        return value.equals(cellValue.apply(item));
    }

    @Override
    public final void describeTo(final Description description) {
        description.appendText("cell with value ").appendValue(value);
    }
}
