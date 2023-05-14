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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface SheetSchema {
    boolean hasName(final String name);

    int indexOfCellWithName(final String name);

    final class WithNames implements SheetSchema {

        private final List<String> names;

        public WithNames(final String... names) {
            this(Arrays.asList(names));
        }

        public WithNames(final List<String> names) {
            this.names = List.copyOf(names);
        }

        @Override
        public boolean hasName(final String name) {
            return names.contains(name);
        }

        @Override
        public int indexOfCellWithName(final String name) {
            return names.indexOf(name);
        }
    }

    final class MapedBy implements SheetSchema {

        private final Map<String, Integer> namedIndices;

        public MapedBy(final Map<String, Integer> namedIndices) {
            this.namedIndices = Map.copyOf(namedIndices);
        }

        @Override
        public boolean hasName(final String name) {
            return namedIndices.containsKey(name);
        }

        @Override
        public int indexOfCellWithName(final String name) {
            return namedIndices.getOrDefault(name, -1);
        }
    }
}
