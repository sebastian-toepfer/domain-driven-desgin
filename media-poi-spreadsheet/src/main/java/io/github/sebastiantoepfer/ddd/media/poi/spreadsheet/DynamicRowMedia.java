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

import java.util.function.Function;

class DynamicRowMedia implements RowMedia {

    private final RowIdentifier rowFinder;
    private final Function<RowMedia, RowMedia> values;
    private final SheetMedia sheet;

    DynamicRowMedia(final SheetMedia sheet, final RowIdentifier rowFinder) {
        this(sheet, rowFinder, Function.identity());
    }

    private DynamicRowMedia(
        final SheetMedia sheet,
        final RowIdentifier rowFinder,
        final Function<RowMedia, RowMedia> values
    ) {
        this.sheet = sheet;
        this.rowFinder = rowFinder;
        this.values = values;
    }

    @Override
    public RowMedia withValue(final String name, final String value) {
        return withValue(rowFinder.append(name, value), m -> m.withValue(name, value));
    }

    @Override
    public RowMedia withValue(final String name, final double value) {
        return withValue(rowFinder.append(name, value), m -> m.withValue(name, value));
    }

    @Override
    public RowMedia withValue(final String name, final boolean value) {
        return withValue(rowFinder.append(name, value), m -> m.withValue(name, value));
    }

    private RowMedia withValue(final RowIdentifier newRowFinder, final Function<RowMedia, RowMedia> newValue) {
        return newRowFinder
            .findRow(sheet)
            .map(sheet::convertToRowMedia)
            .map(values)
            .map(RowMedia.class::cast)
            .orElse(new DynamicRowMedia(sheet, newRowFinder, values.andThen(newValue)));
    }

    @Override
    public SheetMedia sync() {
        return values.apply(sheet.intoNewRow()).sync();
    }
}
