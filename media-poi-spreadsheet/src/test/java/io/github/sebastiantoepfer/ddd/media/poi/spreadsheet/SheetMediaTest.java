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
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.sebastiantoepfer.ddd.media.poi.spreadsheet.test.RowMatcher;
import io.github.sebastiantoepfer.ddd.printables.core.MapPrintable;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SheetMediaTest {

    private SheetMedia sheetMedia;
    private XSSFSheet sheet;

    @BeforeEach
    void createSheetMedia() {
        final XSSFWorkbook workBook = new XSSFWorkbook();
        sheet = workBook.createSheet();
        sheetMedia = new WorkbookMedia(workBook).activeSheet(new SheetSchema.WithNames("name", "age", "birthday"));
    }

    @Test
    void should_throw_illegal_argument_exeption_if_try_to_update_a_non_existing_row() {
        assertThat(
            assertThrows(IllegalArgumentException.class, () -> sheetMedia.intoRow(0)).getMessage(),
            is("no row with index: 0 exists in current sheet!")
        );
    }

    @Test
    void should_update_a_given_row_with_values_from_printable() {
        final Row rowToUpdate = sheet.createRow(0);
        rowToUpdate.createCell(0).setCellValue("Maura");
        rowToUpdate.createCell(1).setCellValue("38");
        rowToUpdate.createCell(2).setCellValue("1984-08-01");

        assertThat(
            new MapPrintable(Map.of("name", "jane", "age", "40", "birthday", "1982-06-09", "ignore", "detective"))
                .printOn(sheetMedia.intoRow(0))
                .sync(),
            contains(new RowMatcher(contains(cellWith("jane"), cellWith("40"), cellWith("1982-06-09"))))
        );
    }

    @Test
    void should_update_row__determine_by_idx_from_printable_with_values_from_printables() {
        final Row rowToNotToChange = sheet.createRow(0);
        rowToNotToChange.createCell(0).setCellValue("Angie");
        rowToNotToChange.createCell(1).setCellValue("34");
        rowToNotToChange.createCell(2).setCellValue("1988-10-17");

        final Row rowToUpdate = sheet.createRow(1);
        rowToUpdate.createCell(0).setCellValue("Maura");
        rowToUpdate.createCell(1).setCellValue("38");
        rowToUpdate.createCell(2).setCellValue("1984-08-01");

        final Row rowToNotToChange2 = sheet.createRow(2);
        rowToNotToChange2.createCell(0).setCellValue("Molly");
        rowToNotToChange2.createCell(1).setCellValue("36");
        rowToNotToChange2.createCell(2).setCellValue("1986-11-05");

        assertThat(
            new MapPrintable(
                Map.of("name", "Jane", "age", "40", "birthday", "1982-06-09", "ignore", "detective", "id", "1")
            )
                .printOn(sheetMedia.rowIdentifiedByValueOfProperty("id"))
                .sync(),
            contains(
                new RowMatcher(contains(cellWith("Angie"), cellWith("34"), cellWith("1988-10-17"))),
                new RowMatcher(contains(cellWith("Jane"), cellWith("40"), cellWith("1982-06-09"))),
                new RowMatcher(contains(cellWith("Molly"), cellWith("36"), cellWith("1986-11-05")))
            )
        );
    }

    @Test
    void should_create__new_row__determine_by_idx_from_printable_with_values_from_printables() {
        final Row rowToNotToChange = sheet.createRow(0);
        rowToNotToChange.createCell(0).setCellValue("Angie");
        rowToNotToChange.createCell(1).setCellValue("34");
        rowToNotToChange.createCell(2).setCellValue("1988-10-17");

        final Row rowToNotToChange2 = sheet.createRow(1);
        rowToNotToChange2.createCell(0).setCellValue("Molly");
        rowToNotToChange2.createCell(1).setCellValue("36");
        rowToNotToChange2.createCell(2).setCellValue("1986-11-05");

        assertThat(
            new MapPrintable(Map.of("name", "Jane", "age", "40", "birthday", "1982-06-09", "ignore", "detective"))
                .printOn(sheetMedia.rowIdentifiedByValueOfProperty("id"))
                .sync(),
            contains(
                new RowMatcher(contains(cellWith("Angie"), cellWith("34"), cellWith("1988-10-17"))),
                new RowMatcher(contains(cellWith("Molly"), cellWith("36"), cellWith("1986-11-05"))),
                new RowMatcher(contains(cellWith("Jane"), cellWith("40"), cellWith("1982-06-09")))
            )
        );
    }
}
