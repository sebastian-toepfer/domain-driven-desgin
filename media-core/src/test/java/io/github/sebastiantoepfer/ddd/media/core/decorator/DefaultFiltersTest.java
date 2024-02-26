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
package io.github.sebastiantoepfer.ddd.media.core.decorator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import io.github.sebastiantoepfer.ddd.media.core.HashMapMedia;
import org.junit.jupiter.api.Test;

class DefaultFiltersTest {

    @Test
    void should_create_media_with_does_ignore_given_names() {
        assertThat(
            DefaultFilters.without(new HashMapMedia(), "test", "hallo")
                .withValue("test", "testValue")
                .withValue("hallo", "testValue")
                .withValue("value", "testValue")
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", "testValue"))
        );
    }

    @Test
    void should_create_media_with_allow_only_given_names() {
        assertThat(
            DefaultFilters.onlyWith(new HashMapMedia(), "value", "hallo")
                .withValue("test", "testValue")
                .withValue("hallo", "testValue")
                .withValue("value", "testValue")
                .decoratedMedia(),
            both(not(hasKey("test"))).and(hasEntry("value", "testValue"))
        );
    }
}
