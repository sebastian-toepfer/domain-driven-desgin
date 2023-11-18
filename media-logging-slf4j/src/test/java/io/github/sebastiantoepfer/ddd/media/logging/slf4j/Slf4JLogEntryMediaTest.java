package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import io.github.sebastiantoepfer.ddd.common.Media;
import io.github.sebastiantoepfer.ddd.common.Printable;
import io.github.sebastiantoepfer.ddd.media.logging.slf4j.test.InMemoryAppender;
import io.github.sebastiantoepfer.ddd.media.logging.slf4j.test.LogRecord;
import io.github.sebastiantoepfer.ddd.media.message.DefaultNamedMessageFormat;
import io.github.sebastiantoepfer.ddd.printables.core.MapPrintable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

class Slf4JLogEntryMediaTest {

    private Level rootLogLevel;
    private List<Appender<ILoggingEvent>> rootLogAppenders;
    private Collection<LogRecord> logRecords;

    @BeforeEach
    void configureLogging() {
        final Logger rootLogger = (Logger) LoggerFactory.getLogger("ROOT");
        rootLogLevel = rootLogger.getLevel();
        rootLogger.setLevel(Level.ERROR);
        rootLogAppenders =
            StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(rootLogger.iteratorForAppenders(), 0), true)
                .toList();
        rootLogger.detachAndStopAllAppenders();

        final Logger log = (Logger) LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging");
        log.setLevel(Level.TRACE);
        logRecords = InMemoryAppender.createWithLogger(log);
    }

    @AfterEach
    void resetRootLogger() {
        rootLogAppenders
            .stream()
            .forEach(a -> {
                a.start();
                ((Logger) LoggerFactory.getLogger("ROOT")).addAppender(a);
            });
    }

    @Test
    void should_log_into_memoryappender() {
        LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging").atDebug().log("a debug log message");

        assertThat(logRecords, hasItem(new LogRecord(Level.DEBUG, "a debug log message")));
    }

    @Test
    void should_write_formated_message_as_log() {
        Slf4JLogEntryMedia
            .of(new DefaultNamedMessageFormat("user ${name} was created."), org.slf4j.event.Level.DEBUG)
            .withValue("name", "Jane")
            .logTo(LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging"));

        assertThat(logRecords, hasItem(new LogRecord(Level.DEBUG, "user Jane was created.")));
    }

    @Test
    void should_log_at_info() {
        Slf4JLogEntryMedia
            .of(
                new DefaultNamedMessageFormat("user ${name} was created."),
                new DefaultLogLevelDescision(
                    org.slf4j.event.Level.DEBUG,
                    "name",
                    v -> {
                        if ("Jane".equals(v)) {
                            return org.slf4j.event.Level.INFO;
                        } else {
                            return org.slf4j.event.Level.TRACE;
                        }
                    }
                )
            )
            .withValue("name", "Jane")
            .logTo(LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging"));

        assertThat(logRecords, hasItem(new LogRecord(Level.INFO, "user Jane was created.")));
    }

    @Test
    void should_write_values_to_mdc() {
        Slf4JLogEntryMedia
            .of(
                new DefaultNamedMessageFormat("user ${name} was created."),
                new DefaultLogLevelDescision(
                    org.slf4j.event.Level.DEBUG,
                    "name",
                    v -> {
                        if ("Jane".equals(v)) {
                            return org.slf4j.event.Level.INFO;
                        } else {
                            return org.slf4j.event.Level.TRACE;
                        }
                    }
                ),
                List.of("user_id")
            )
            .withValue("name", "Jane")
            .withValue("user_id", "user@github.com")
            .logTo(LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging"));

        assertThat(
            logRecords,
            hasItem(new LogRecord(Level.INFO, "user Jane was created.", Map.of("user_id", "user@github.com")))
        );
        assertThat(MDC.getCopyOfContextMap().entrySet(), is(empty()));
    }

    @Test
    void should_write_values_to_mdc_with_othername() {
        Slf4JLogEntryMedia
            .of(
                new DefaultNamedMessageFormat("user ${user.name} was created.", Locale.GERMANY),
                org.slf4j.event.Level.INFO,
                Map.of(
                    "user.user_id",
                    "username",
                    "user.int",
                    "int",
                    "user.long",
                    "long",
                    "user.double",
                    "double",
                    "user.b1",
                    "true",
                    "user.b2",
                    "false",
                    "user.test",
                    "other_value",
                    "user.friend.user_id",
                    "username_of_friend",
                    "bigi",
                    "bigi",
                    "bigd",
                    "tina"
                )
            )
            .withValue(
                "user",
                new Printable() {
                    @Override
                    public <T extends Media<T>> T printOn(final T media) {
                        return media
                            .withValue("name", "Jane")
                            .withValue("user_id", "user@github.com")
                            .withValue("int", 1)
                            .withValue("long", 2L)
                            .withValue("double", 2.1)
                            .withValue("b1", true)
                            .withValue("b2", false)
                            .withValue("col", List.of())
                            .withValue("bigi", BigInteger.ONE)
                            .withValue("bigd", BigDecimal.TEN)
                            .withValue(
                                "friend",
                                new MapPrintable(Map.of("name", "Maura", "user_id", "user@gitlab.com"))
                            );
                    }
                }
            )
            .withValue("test", new MapPrintable(Map.of("name", "Maura", "user_id", "user@gitlab.com")))
            .logTo(LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging"));

        assertThat(
            logRecords,
            hasItem(
                new LogRecord(
                    Level.INFO,
                    "user Jane was created.",
                    Map.of(
                        "username",
                        "user@github.com",
                        "int",
                        "1",
                        "long",
                        "2",
                        "double",
                        "2.1",
                        "true",
                        "true",
                        "false",
                        "false",
                        "username_of_friend",
                        "user@gitlab.com"
                    )
                )
            )
        );
        assertThat(MDC.getCopyOfContextMap().entrySet(), is(empty()));
    }
}
