package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MessageMediaExtensionTest {

    @Test
    void should_decorated_message_media() throws Exception {
        final TestMessageMediaExtension message = new TestMessageMediaExtension(
            new DefaultNamedMessageFormat(
                """
                Hello my name is ${name} and i am ${age} years old.
                The Disk ${diskNumber} contains ${disksize} of data.
                The other disk ${bigDisk} contains only ${bigDiskSize} of data.
                We need a ${bool} and another ${abool}.
                Are you ${person.firstname} ${person.lastname}?
                Can you buy me ${list}?
                """,
                Locale.US
            )
        )
            .withValue("name", "Jane")
            .withValue("age", 42)
            .withValue("diskNumber", 2L)
            .withValue("disksize", 2.653)
            .withValue("bigDiskSize", BigDecimal.valueOf(10.4))
            .withValue("bigDisk", BigInteger.ONE)
            .withValue("bool", true)
            .withValue("abool", false)
            .withValue("person", new TestPrintable(Map.of("firstname", "Joe", "lastname", "Doe")))
            .withValue("list", List.of("bananas", "apples"));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        message.writeTo(baos);
        final String msg = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n")) +
        "\n";

        assertThat(
            msg,
            is(
                """
                Hello my name is Jane and i am 42 years old.
                The Disk 2 contains 2.653 of data.
                The other disk 1 contains only 10.4 of data.
                We need a true and another false.
                Are you Joe Doe?
                Can you buy me bananas, apples?
                """
            )
        );
    }

    @Test
    void should_do_nothing() {
        //not a good idea to use pitest?
        assertThat(
            new TestMessageMediaExtension(new DefaultNamedMessageFormat("Hello"))
                .withValue("test", new TestMessageMediaExtension(new DefaultNamedMessageFormat("Hello"))),
            is(not(nullValue()))
        );
    }

    private static class TestMessageMediaExtension extends MessageMediaExtension<TestMessageMediaExtension> {

        public TestMessageMediaExtension(final NamedMessageFormat format) {
            this(new MessageMedia(format));
        }

        private TestMessageMediaExtension(MessageMedia media) {
            super(media);
        }

        @Override
        protected TestMessageMediaExtension createWith(final MessageMedia message) {
            return new TestMessageMediaExtension(message);
        }
    }
}
