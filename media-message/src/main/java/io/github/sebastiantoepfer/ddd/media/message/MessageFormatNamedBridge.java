package io.github.sebastiantoepfer.ddd.media.message;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Logger;

public class MessageFormatNamedBridge implements NamedMessageFormat {

    private static final Logger LOGGER = Logger.getLogger(MessageFormatNamedBridge.class.getName());
    private final MessageFormat format;
    private final Map<String, Integer> nameIndices;

    public MessageFormatNamedBridge(final MessageFormat format, final Map<String, Integer> nameIndices) {
        checkNameAssigenments(format, nameIndices);
        this.format = new MessageFormat(format.toPattern(), format.getLocale());
        this.nameIndices = Map.copyOf(nameIndices);
    }

    private static void checkNameAssigenments(final MessageFormat format, final Map<String, Integer> nameIndices) {
        if (format.getFormatsByArgumentIndex().length > nameIndices.size()) {
            throw new IllegalArgumentException("Not all placeholders have a name assignment!");
        }
    }

    @Override
    public Collection<String> placeholderNames() {
        LOGGER.entering(MessageFormatNamedBridge.class.getName(), "placeholderNames");
        final Collection<String> result = nameIndices.keySet();
        LOGGER.exiting(MessageFormatNamedBridge.class.getName(), "placeholderNames");
        return result;
    }

    @Override
    public String format(final Map<String, Object> values) {
        LOGGER.entering(MessageFormatNamedBridge.class.getName(), "format", values);
        final String result = format.format(values(values));
        LOGGER.exiting(MessageFormatNamedBridge.class.getName(), "format", result);
        return result;
    }

    private Object[] values(final Map<String, Object> values) {
        LOGGER.entering(MessageFormatNamedBridge.class.getName(), "values", values);
        final Object[] result = values
            .entrySet()
            .stream()
            .map(entry -> Map.entry(nameIndices.get(entry.getKey()), entry.getValue()))
            .sorted(sortByKey())
            .map(Map.Entry::getValue)
            .toArray();
        LOGGER.exiting(MessageFormatNamedBridge.class.getName(), "values", result);
        return result;
    }

    private Comparator<Map.Entry<? extends Comparable, Object>> sortByKey() {
        return (final Map.Entry<? extends Comparable, Object> t, final Map.Entry<? extends Comparable, Object> t1) ->
            t.getKey().compareTo(t1.getKey());
    }
}
