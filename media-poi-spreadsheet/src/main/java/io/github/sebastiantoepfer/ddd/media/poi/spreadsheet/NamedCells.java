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

import java.util.Objects;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

final class NamedCells {

    private final SheetMedia sheet;
    private final Row row;

    public NamedCells(final SheetMedia sheet, final Row row) {
        this.sheet = sheet;
        this.row = Objects.requireNonNull(row);
    }

    Optional<Cell> findCellByName(final String name) {
        Optional<Cell> result;
        if (sheet.hasName(name)) {
            final Integer idx = sheet.getIndexFor(name);
            result = Optional.of(cellByIndex(idx));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    private Cell cellByIndex(final Integer idx) {
        final Cell result;
        if (row.getCell(idx) == null) {
            result = row.createCell(idx, CellType.STRING);
        } else {
            result = row.getCell(idx);
        }
        return result;
    }

    SheetMedia sheet() {
        return sheet;
    }
}
