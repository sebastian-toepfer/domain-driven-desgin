package io.github.sebastiantoepfer.ddd.media.logging.slf4j.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InMemoryAppender extends AppenderBase<ILoggingEvent> implements Collection<LogRecord> {

    public static Collection<LogRecord> createWithLogger(final Logger logger) {
        logger.detachAndStopAllAppenders();
        final InMemoryAppender result = new InMemoryAppender();
        result.start();
        logger.addAppender(result);
        return result;
    }

    private Collection<LogRecord> records;

    private InMemoryAppender() {
        records = new ArrayList<>();
    }

    @Override
    protected void append(final ILoggingEvent e) {
        add(new LogRecord(e.getLevel(), e.getFormattedMessage(), e.getMDCPropertyMap()));
    }

    //<editor-fold defaultstate="collapsed" desc="collection delegates">
    @Override
    public int size() {
        return records.size();
    }

    @Override
    public boolean isEmpty() {
        return records.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return records.contains(o);
    }

    @Override
    public Iterator<LogRecord> iterator() {
        return records.iterator();
    }

    @Override
    public Object[] toArray() {
        return records.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] ts) {
        return records.toArray(ts);
    }

    @Override
    public <T> T[] toArray(final IntFunction<T[]> generator) {
        return records.toArray(generator);
    }

    @Override
    public boolean add(final LogRecord e) {
        return records.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        return records.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> clctn) {
        return records.containsAll(clctn);
    }

    @Override
    public boolean addAll(final Collection<? extends LogRecord> clctn) {
        return records.addAll(clctn);
    }

    @Override
    public boolean removeAll(final Collection<?> clctn) {
        return records.removeAll(clctn);
    }

    @Override
    public boolean removeIf(final Predicate<? super LogRecord> filter) {
        return records.removeIf(filter);
    }

    @Override
    public boolean retainAll(final Collection<?> clctn) {
        return records.retainAll(clctn);
    }

    @Override
    public void clear() {
        records.clear();
    }

    @Override
    public Spliterator<LogRecord> spliterator() {
        return records.spliterator();
    }

    @Override
    public Stream<LogRecord> stream() {
        return records.stream();
    }

    @Override
    public Stream<LogRecord> parallelStream() {
        return records.parallelStream();
    }
    //</editor-fold>

}
