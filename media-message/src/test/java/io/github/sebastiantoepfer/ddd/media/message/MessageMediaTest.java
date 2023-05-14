package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MessageMediaTest {

    @Test
    void should_format_with_strings() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(new MessageFormat("Hello Mr. {0} {1}!"), Map.of("firstName", 0, "lastName", 1))
        )
            .withValue("lastName", "Doe")
            .withValue("firstName", "John");

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("Hello Mr. John Doe!"));
    }

    @Test
    void should_format_with_int() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("The disk \"{1}\" contains {0, number} file(s)."),
                Map.of("diskName", 1, "fileCount", 0)
            )
        )
            .withValue("diskName", "MyDisk")
            .withValue("fileCount", 10);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("The disk \"MyDisk\" contains 10 file(s)."));
    }

    @Test
    void should_format_with_long() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("The disk \"{1}\" contains {0, number} file(s).", Locale.GERMANY),
                Map.of("diskName", 1, "fileCount", 0)
            )
        )
            .withValue("diskName", "MyDisk")
            .withValue("fileCount", 1273L);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("The disk \"MyDisk\" contains 1.273 file(s)."));
    }

    @Test
    void should_format_with_doubles() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("{0,number,#.##}, {0,number,#.#}", Locale.GERMANY),
                Map.of("pi", 0)
            )
        )
            .withValue("pi", 3.1415);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("3,14, 3,1"));
    }

    @Test
    void should_format_with_bigdecimal() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("{0,number,#.##}, {0,number,#.#}", Locale.GERMANY),
                Map.of("pi", 0)
            )
        )
            .withValue("pi", BigDecimal.valueOf(3.1415));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("3,14, 3,1"));
    }

    @Test
    void should_format_with_biginteger() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("The disk \"{1}\" contains {0, number} file(s).", Locale.US),
                Map.of("diskName", 1, "fileCount", 0)
            )
        )
            .withValue("diskName", "MyDisk")
            .withValue("fileCount", BigInteger.valueOf(8771242817265172523L));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("The disk \"MyDisk\" contains 8,771,242,817,265,172,523 file(s)."));
    }

    @Test
    void should_format_with_boolean() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(new MessageFormat("Was {0}."), Map.of("successful", 0))
        )
            .withValue("successful", true);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, is("Was true."));
    }

    @Test
    void should_format_with_printable() throws Exception {
        //we use sal to catch a mutant -> we need a string shorter than "person."
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("Hello {0} {1} {2}."),
                Map.of("sal", 0, "person.lastName", 2, "person.firstName", 1)
            )
        )
            .withValue("sal", "Mrs.")
            .withValue("person", new TestPrintable(Map.of("firstName", "Jane", "lastName", "Doe")));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("Hello Mrs. Jane Doe."));
    }

    @Test
    void should_format_with_collection() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(new MessageFormat("Values are {0}."), Map.of("values", 0))
        )
            .withValue("values", List.of("apples", "bananas"));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("Values are apples, bananas."));
    }

    @Test
    void should_format_with_another_messagemedia() throws Exception {
        final MessageMedia media = new MessageMedia(
            new MessageFormatNamedBridge(
                new MessageFormat("Hello Mr. {0} {1}!"),
                Map.of("person.firstName", 0, "person.lastName", 1)
            )
        )
            .withValue(
                "person",
                new MessageMedia(
                    new MessageFormatNamedBridge(new MessageFormat("{0} {1}"), Map.of("firstName", 0, "lastName", 1))
                )
                    .withValue("lastName", "Doe")
                    .withValue("firstName", "John")
            );

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        media.writeTo(baos);
        final String message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())))
            .lines()
            .collect(joining("\n"));

        assertThat(message, hasToString("Hello Mr. John Doe!"));
    }
}
