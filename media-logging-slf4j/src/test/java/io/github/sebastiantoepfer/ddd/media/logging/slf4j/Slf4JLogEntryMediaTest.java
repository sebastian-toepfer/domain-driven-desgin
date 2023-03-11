package io.github.sebastiantoepfer.ddd.media.logging.slf4j;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import io.github.sebastiantoepfer.ddd.media.logging.slf4j.test.InMemoryAppender;
import io.github.sebastiantoepfer.ddd.media.logging.slf4j.test.LogRecord;
import io.github.sebastiantoepfer.ddd.media.message.DefaultNamedMessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

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
        new Slf4JLogEntryMedia(new DefaultNamedMessageFormat("user ${name} was created."), org.slf4j.event.Level.DEBUG)
            .withValue("name", "Jane")
            .logTo(LoggerFactory.getLogger("io.github.sebastiantoepfer.ddd.TestLogging"));

        assertThat(logRecords, hasItem(new LogRecord(Level.DEBUG, "user Jane was created.")));
    }

    @Test
    void should_log_at_info() {
        new Slf4JLogEntryMedia(
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
}
