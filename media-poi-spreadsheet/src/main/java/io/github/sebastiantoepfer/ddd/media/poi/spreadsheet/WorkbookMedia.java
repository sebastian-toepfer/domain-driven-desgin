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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.sebastiantoepfer.ddd.media.core.Writeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import org.apache.poi.ss.usermodel.Workbook;

public final class WorkbookMedia implements Writeable {

    private final Workbook workbook;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public WorkbookMedia(final Workbook workbook) {
        this.workbook = Objects.requireNonNull(workbook);
    }

    public SheetMedia activeSheet(final SheetSchema schema) {
        return sheetAt(workbook.getActiveSheetIndex(), schema);
    }

    public SheetMedia sheetAt(final int index, final SheetSchema schema) {
        return new SheetMedia(workbook.getSheetAt(index), schema);
    }

    @Override
    public void writeTo(final OutputStream output) throws IOException {
        workbook.write(output);
    }
}
