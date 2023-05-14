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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.apache.poi.ss.usermodel.Cell;

public final class SingleRowMedia implements RowMedia {

    private final NamedCells cells;
    private final Function<NamedCells, NamedCells> rowAction;

    SingleRowMedia(final NamedCells cells) {
        this(cells, row -> row);
    }

    private SingleRowMedia(final NamedCells cells, final Function<NamedCells, NamedCells> rowAction) {
        this.cells = Objects.requireNonNull(cells);
        this.rowAction = Objects.requireNonNull(rowAction);
    }

    @Override
    public SheetMedia sync() {
        return rowAction.apply(cells).sheet();
    }

    @Override
    public SingleRowMedia withValue(final String name, final String value) {
        return withActions(rowAction.andThen(new NamedCellAction(name, cell -> cell.setCellValue(value))));
    }

    @Override
    public SingleRowMedia withValue(final String name, final long value) {
        return withActions(rowAction.andThen(new NamedCellAction(name, cell -> cell.setCellValue((double) value))));
    }

    @Override
    public SingleRowMedia withValue(final String name, final double value) {
        return withActions(rowAction.andThen(new NamedCellAction(name, cell -> cell.setCellValue(value))));
    }

    @Override
    public SingleRowMedia withValue(final String name, final boolean value) {
        return withActions(rowAction.andThen(new NamedCellAction(name, cell -> cell.setCellValue(value))));
    }

    private SingleRowMedia withActions(final Function<NamedCells, NamedCells> actions) {
        return new SingleRowMedia(cells, actions);
    }

    private static class NamedCellAction implements UnaryOperator<NamedCells> {

        private final String name;
        private final Consumer<Cell> cellAction;

        public NamedCellAction(final String name, final Consumer<Cell> cellAction) {
            this.name = name;
            this.cellAction = cellAction;
        }

        @Override
        public NamedCells apply(final NamedCells t) {
            t.findCellByName(name).ifPresent(cellAction);
            return t;
        }
    }
}
