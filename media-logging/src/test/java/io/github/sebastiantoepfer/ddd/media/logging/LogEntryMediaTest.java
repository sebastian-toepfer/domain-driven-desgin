package io.github.sebastiantoepfer.ddd.media.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import io.github.sebastiantoepfer.ddd.media.message.DefaultNamedMessageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

class LogEntryMediaTest {

    @Test
    void should_write_formated_message_as_log() {
        final InMemoryHandler inMemoryHandler = new InMemoryHandler();
        final Logger logger = Logger.getLogger(LogEntryMediaTest.class.getName());
        logger.addHandler(inMemoryHandler);

        final FixedLevelLogEntryMedia logEntry = new FixedLevelLogEntryMedia(
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
        logEntry.atLevel(Level.INFO).logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(
                new LogRecordMatchers()
                    .hasMessage(
                        """
                        Hello my name is Jane and i am 42 years old.
                        The Disk 2 contains 2.653 of data.
                        The other disk 1 contains only 10.4 of data.
                        We need a true and another false.
                        Are you Joe Doe?
                        Can you buy me bananas, apples?
                        """
                    )
                    .and()
                    .hasLevel(Level.INFO)
            )
        );
    }

    @Test
    void should_do_nothing() {
        //not a good idea to use pitest?
        assertThat(
            new FixedLevelLogEntryMedia(new DefaultNamedMessageFormat("Hello"))
                .withValue("test", new FixedLevelLogEntryMedia(new DefaultNamedMessageFormat("Hello"))),
            is(not(nullValue()))
        );
    }
}
