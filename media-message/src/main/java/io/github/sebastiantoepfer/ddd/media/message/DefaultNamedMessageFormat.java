package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.stream.Collectors.toUnmodifiableSet;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

public class DefaultNamedMessageFormat implements NamedMessageFormat {

    private static final Logger LOGGER = Logger.getLogger(DefaultNamedMessageFormat.class.getName());

    private final String format;
    private final Locale locale;

    public DefaultNamedMessageFormat(final String format) {
        this(format, Locale.getDefault());
    }

    public DefaultNamedMessageFormat(final String format, final Locale locale) {
        this.format = Objects.requireNonNull(format);
        this.locale = Objects.requireNonNull(locale);
    }

    @Override
    public Collection<String> placeholderNames() {
        LOGGER.entering(DefaultNamedMessageFormat.class.getName(), "placeholderNames");
        final Collection<String> result = StreamSupport
            .stream(extractPlaceholders().spliterator(), false)
            .map(Placeholder::name)
            .collect(toUnmodifiableSet());
        LOGGER.exiting(DefaultNamedMessageFormat.class.getName(), "placeholderNames", result);
        return result;
    }

    @Override
    public String format(final Map<String, Object> values) {
        LOGGER.entering(DefaultNamedMessageFormat.class.getName(), "format", values);
        final Placeholders placeholders = extractPlaceholders();
        final MessageFormat mesageFormat = new MessageFormat(placeholders.createMessageFormatString(), locale);
        final String result = mesageFormat.format(placeholders.createValues(values));
        LOGGER.exiting(DefaultNamedMessageFormat.class.getName(), "format", result);
        return result;
    }

    private Placeholders extractPlaceholders() {
        LOGGER.entering(DefaultNamedMessageFormat.class.getName(), "extractPlaceholders");
        final Placeholders placeholders = new Placeholders(new PlaceholderExtractor(format).findPlaceholders());
        LOGGER.exiting(DefaultNamedMessageFormat.class.getName(), "extractPlaceholders");
        return placeholders;
    }

    @Override
    public String toString() {
        return "DefaultNamedMessageFormat{" + "format=" + format + '}';
    }

    private class Placeholders implements Iterable<Placeholder> {

        private final List<Placeholder> wildcard;

        public Placeholders(final List<Placeholder> wildcard) {
            this.wildcard = List.copyOf(wildcard);
        }

        private String createMessageFormatString() {
            LOGGER.entering(Placeholders.class.getName(), "createMessageFormatString");
            String result = format;
            for (int i = 0; i < wildcard.size(); i++) {
                result = wildcard.get(i).replaceWithIndex(i).inside(result);
            }
            LOGGER.exiting(Placeholders.class.getName(), "createMessageFormatString", result);
            return result;
        }

        private Object[] createValues(final Map<String, Object> values) {
            LOGGER.entering(Placeholders.class.getName(), "createValues", values);
            final Object[] result = wildcard.stream().map(Placeholder::name).map(values::get).toArray();
            LOGGER.exiting(Placeholders.class.getName(), "createValues", Arrays.toString(result));
            return result;
        }

        @Override
        public Iterator<Placeholder> iterator() {
            LOGGER.entering(Placeholders.class.getName(), "iterator");
            final Iterator<Placeholder> result = wildcard.iterator();
            LOGGER.exiting(Placeholders.class.getName(), "iterator");
            return result;
        }
    }
}
