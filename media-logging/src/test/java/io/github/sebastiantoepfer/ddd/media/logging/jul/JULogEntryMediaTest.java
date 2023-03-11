package io.github.sebastiantoepfer.ddd.media.logging.jul;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import io.github.sebastiantoepfer.ddd.media.core.TestPrintable;
import io.github.sebastiantoepfer.ddd.media.logging.LogEntryMedia;
import io.github.sebastiantoepfer.ddd.media.message.DefaultNamedMessageFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JULogEntryMediaTest {

    private InMemoryHandler inMemoryHandler;
    private Logger logger;

    @BeforeEach
    void initLogger() {
        inMemoryHandler = new InMemoryHandler();
        logger = Logger.getLogger(JULogEntryMediaTest.class.getName());
        logger.addHandler(inMemoryHandler);
    }

    @Test
    void should_log_string_at_info() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if ("info".equals(v)) {
                        return Level.INFO;
                    } else {
                        return Level.FINEST;
                    }
                }
            )
        )
            .withValue("test", "info")
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("info").and().hasLevel(Level.INFO)));
    }

    @Test
    void should_log_string_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if ("info".equals(v)) {
                        return Level.INFO;
                    } else {
                        return Level.WARNING;
                    }
                }
            )
        )
            .withValue("test", "test")
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("test").and().hasLevel(Level.WARNING)));
    }

    @Test
    void should_log_int_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (Integer.valueOf(12).equals(v)) {
                        return Level.INFO;
                    } else {
                        return Level.WARNING;
                    }
                }
            )
        )
            .withValue("test", 14)
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("14").and().hasLevel(Level.WARNING)));
    }

    @Test
    void should_log_long_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (Long.valueOf(12L).equals(v)) {
                        return Level.INFO;
                    } else {
                        return Level.WARNING;
                    }
                }
            )
        )
            .withValue("test", 14L)
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("14").and().hasLevel(Level.WARNING)));
    }

    @Test
    void should_log_double_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (Double.valueOf(12.1).equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", 12.1)
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("12.1").and().hasLevel(Level.WARNING)));
    }

    @Test
    void should_log_bigdecimal_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (BigDecimal.valueOf(12.1).equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", BigDecimal.valueOf(12.1))
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("12.1").and().hasLevel(Level.WARNING)));
    }

    @Test
    void should_log_biginteger_at_info() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${test}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (BigInteger.valueOf(121).equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", BigInteger.valueOf(120))
            .logTo(logger);

        assertThat(inMemoryHandler, contains(new LogRecordMatchers().hasMessage("120").and().hasLevel(Level.INFO)));
    }

    @Test
    void should_log_boolean_at_warn() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${success}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if (Boolean.TRUE.equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", true)
            .withValue("success", false)
            .logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(new LogRecordMatchers().hasMessage("false").and().hasLevel(Level.WARNING))
        );
    }

    @Test
    void should_log_printable_at_info() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${person.firstname} ${person.lastname}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if ("WARN".equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", "INFO")
            .withValue("person", new TestPrintable(Map.of("firstname", "Jane", "lastname", "Doe")))
            .logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(new LogRecordMatchers().hasMessage("Jane Doe").and().hasLevel(Level.INFO))
        );
    }

    @Test
    void should_log_collection_at_info() {
        new JULogEntryMedia(
            new DefaultNamedMessageFormat("${fruits}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if ("WARN".equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        )
            .withValue("test", "INFO")
            .withValue("fruits", List.of("bananas", "apples"))
            .logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(new LogRecordMatchers().hasMessage("bananas, apples").and().hasLevel(Level.INFO))
        );
    }

    @Test
    void should_not_change_log_with_sublogger() {
        final LogEntryMedia logMedia = new JULogEntryMedia(
            new DefaultNamedMessageFormat("${fruits}"),
            new DefaultLogLevelDecision(
                Level.SEVERE,
                "test",
                v -> {
                    if ("WARN".equals(v)) {
                        return Level.WARNING;
                    } else {
                        return Level.INFO;
                    }
                }
            )
        );
        logMedia
            .withValue("test", "INFO")
            .withValue("sublogger", logMedia)
            .withValue("fruits", List.of("bananas", "apples"))
            .logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(new LogRecordMatchers().hasMessage("bananas, apples").and().hasLevel(Level.INFO))
        );
    }

    @Test
    void should_log_at_given_level() {
        new JULogEntryMedia(new DefaultNamedMessageFormat("Test message for ${name}"), Level.INFO)
            .withValue("name", "Jane")
            .logTo(logger);

        assertThat(
            inMemoryHandler,
            contains(new LogRecordMatchers().hasMessage("Test message for Jane").and().hasLevel(Level.INFO))
        );
    }
}
