package io.github.sebastiantoepfer.ddd.media.logging.jul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

class LogRecordMatchers {

    private final List<Matcher<LogRecord>> matchers;

    public LogRecordMatchers() {
        this(new ArrayList<>());
    }

    private LogRecordMatchers(final CompositeMatcher other) {
        this(other.matchers);
    }

    public LogRecordMatchers(final List<Matcher<LogRecord>> matchers) {
        this.matchers = new ArrayList<>(matchers);
    }

    public CompositeMatcher hasMessage(final String message) {
        return withMatcher(new LogRecodMessageMatcher(message));
    }

    public CompositeMatcher hasLevel(final Level level) {
        return withMatcher(new LogRecodLevelMatcher(level));
    }

    private CompositeMatcher withMatcher(final Matcher<LogRecord> matcher) {
        final List<Matcher<LogRecord>> tmp = new ArrayList<>(matchers);
        tmp.add(matcher);
        return new CompositeMatcher(tmp);
    }

    public static class CompositeMatcher extends TypeSafeMatcher<LogRecord> {

        private final List<Matcher<LogRecord>> matchers;

        public CompositeMatcher(final Collection<Matcher<LogRecord>> matchers) {
            new NotEmptyAssertion(matchers).validate("Matchers must be not empty!");
            this.matchers = List.copyOf(matchers);
        }

        @Override
        protected boolean matchesSafely(final LogRecord item) {
            return createMatcher().matches(item);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("LogRecord").appendDescriptionOf(createMatcher());
        }

        @Override
        protected void describeMismatchSafely(final LogRecord item, final Description mismatchDescription) {
            createMatcher().describeMismatch(item, mismatchDescription.appendText("LogRecord "));
        }

        private Matcher<LogRecord> createMatcher() {
            final Matcher<LogRecord> result;
            if (matchers.size() == 1) {
                result = matchers.iterator().next();
            } else {
                result = Matchers.allOf(matchers.toArray(Matcher[]::new));
            }
            return result;
        }

        public LogRecordMatchers and() {
            return new LogRecordMatchers(this);
        }
    }

    private interface Assertion {
        default void validate(String message) {
            if (!isValid()) {
                throw new IllegalArgumentException(message);
            }
        }

        boolean isValid();
    }

    private static class NotEmptyAssertion implements Assertion {

        private final Collection<?> collection;

        public NotEmptyAssertion(final Collection<?> collection) {
            this.collection = List.copyOf(collection);
        }

        @Override
        public boolean isValid() {
            return !collection.isEmpty();
        }
    }

    private static class LogRecodMessageMatcher extends TypeSafeMatcher<LogRecord> {

        private final String message;

        public LogRecodMessageMatcher(final String message) {
            this.message = Objects.requireNonNull(message);
        }

        @Override
        protected boolean matchesSafely(final LogRecord item) {
            return message.equals(item.getMessage());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(" with message: ").appendText(message);
        }

        @Override
        protected void describeMismatchSafely(final LogRecord item, final Description mismatchDescription) {
            mismatchDescription.appendText("but message was: ").appendText(item.getMessage());
        }
    }

    private static class LogRecodLevelMatcher extends TypeSafeMatcher<LogRecord> {

        private final Level level;

        public LogRecodLevelMatcher(final Level level) {
            this.level = Objects.requireNonNull(level);
        }

        @Override
        protected boolean matchesSafely(final LogRecord item) {
            return level.equals(item.getLevel());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(" with level: ").appendValue(level);
        }

        @Override
        protected void describeMismatchSafely(final LogRecord item, final Description mismatchDescription) {
            mismatchDescription.appendText("but level was: ").appendValue(item.getLevel());
        }
    }
}
