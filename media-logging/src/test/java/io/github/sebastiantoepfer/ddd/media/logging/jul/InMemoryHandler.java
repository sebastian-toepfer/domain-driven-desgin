package io.github.sebastiantoepfer.ddd.media.logging.jul;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.stream.Stream;

class InMemoryHandler extends Handler implements Collection<LogRecord> {

    private final Collection<LogRecord> logrecords;

    public InMemoryHandler() {
        this.logrecords = new HashSet<>();
    }

    @Override
    public void publish(final LogRecord lr) {
        logrecords.add(lr);
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}

    @Override
    public int size() {
        return logrecords.size();
    }

    @Override
    public boolean isEmpty() {
        return logrecords.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return logrecords.contains(o);
    }

    @Override
    public Iterator<LogRecord> iterator() {
        return logrecords.iterator();
    }

    @Override
    public Object[] toArray() {
        return logrecords.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return logrecords.toArray(ts);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return logrecords.toArray(generator);
    }

    @Override
    public boolean add(LogRecord e) {
        return logrecords.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return logrecords.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        return logrecords.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection<? extends LogRecord> clctn) {
        return logrecords.addAll(clctn);
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        return logrecords.removeAll(clctn);
    }

    @Override
    public boolean removeIf(Predicate<? super LogRecord> filter) {
        return logrecords.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        return logrecords.retainAll(clctn);
    }

    @Override
    public void clear() {
        logrecords.clear();
    }

    @Override
    public boolean equals(Object o) {
        return logrecords.equals(o);
    }

    @Override
    public int hashCode() {
        return logrecords.hashCode();
    }

    @Override
    public Spliterator<LogRecord> spliterator() {
        return logrecords.spliterator();
    }

    @Override
    public Stream<LogRecord> stream() {
        return logrecords.stream();
    }

    @Override
    public Stream<LogRecord> parallelStream() {
        return logrecords.parallelStream();
    }
}
