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
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Row;

public interface RowIdentifier {
    RowIdentifier append(String name, Object value);

    Optional<Row> findRow(Iterable<Row> rows);

    class IndexIsValueOfProperty implements RowIdentifier {

        private static final Pattern POSITIV_NUMBER = Pattern.compile("^\\d+$");

        private final String name;

        public IndexIsValueOfProperty(final String name) {
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public RowIdentifier append(final String name, final Object value) {
            final RowIdentifier result;
            if (this.name.equals(name) && POSITIV_NUMBER.matcher(String.valueOf(value)).find()) {
                result = new IndexRowFinder(Integer.parseInt(String.valueOf(value)));
            } else {
                result = this;
            }
            return result;
        }

        @Override
        public Optional<Row> findRow(final Iterable<Row> rows) {
            return Optional.empty();
        }

        private static class IndexRowFinder implements RowIdentifier {

            private final int rowIdx;

            public IndexRowFinder(final int rowIdx) {
                this.rowIdx = rowIdx;
            }

            @Override
            public RowIdentifier append(final String name, final Object value) {
                return this;
            }

            @Override
            public Optional<Row> findRow(final Iterable<Row> rows) {
                return StreamSupport.stream(rows.spliterator(), false).skip(rowIdx).findFirst();
            }
        }
    }
}
