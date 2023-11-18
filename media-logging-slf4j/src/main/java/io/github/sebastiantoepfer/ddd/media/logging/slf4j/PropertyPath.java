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
package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import java.util.Objects;

final class PropertyPath {

    private final String path;

    public PropertyPath(final String path) {
        this.path = Objects.requireNonNull(path);
    }

    public PropertyPath subPath(final String fieldName) {
        final PropertyPath result;
        if (startsWith(fieldName)) {
            result = new PropertyPath(path.substring(path.indexOf('.') + 1));
        } else {
            throw new IllegalArgumentException("path does not start with field!");
        }
        return result;
    }

    public boolean startsWith(final String fieldName) {
        return path.startsWith(fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.path);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyPath other = (PropertyPath) obj;
        return Objects.equals(this.path, other.path);
    }
}
