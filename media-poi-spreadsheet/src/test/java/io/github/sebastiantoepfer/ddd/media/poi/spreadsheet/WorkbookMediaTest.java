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

import io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.SheetSchema.MapedBy;
import io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.SheetSchema.WithNames;
import io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.test.RowMatcher;
import io.github.sebastiantoepfer.ddd.printables.core.MapPrintable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

class WorkbookMediaTest {

    @Test
    void should_create_new_row_in_activeSheet_with_values_from_printable() throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        assertThat(
            new MapPrintable(Map.of("name", "jane", "age", "40", "birthday", "1982-06-09", "ignore", "detective"))
                .printOn(new WorkbookMedia(workbook).activeSheet(new WithNames("name", "age", "birthday")).intoNewRow())
                .sync(),
            contains(new RowMatcher(cellWith("jane"), cellWith("40"), cellWith("1982-06-09")))
        );
    }

    @Test
    void should_write_workbook_into_stream() throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        final WorkbookMedia media = new MapPrintable(
            Map.of("name", "jane", "age", "40", "birthday", "1982-06-09", "ignore", "detective")
        )
            .printOn(
                new WorkbookMedia(workbook)
                    .activeSheet(new MapedBy(Map.of("name", 0, "age", 1, "birthday", 2)))
                    .intoNewRow()
            )
            .sync()
            .sync();

        assertThat(
            new XSSFWorkbook(new ByteArrayInputStream(media.asBytes())).getSheetAt(0),
            contains(new RowMatcher(cellWith("jane"), cellWith("40"), cellWith("1982-06-09")))
        );
    }
}
