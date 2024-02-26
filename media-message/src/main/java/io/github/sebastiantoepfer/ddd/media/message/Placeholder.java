package io.github.sebastiantoepfer.ddd.media.message;

import static java.util.function.Predicate.not;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Placeholder {

    private static final Logger LOGGER = Logger.getLogger(Placeholder.class.getName());
    private static final Pattern PATTERN = Pattern.compile(
        "^(" +
        Pattern.quote(PlaceholderExtractor.START) +
        "(?<name>[^,]+)(?<style>[^}]*)" +
        Pattern.quote(String.valueOf(PlaceholderExtractor.END)) +
        ")$"
    );
    private final String value;

    Placeholder(final String placeholder) {
        this.value = placeholder;
    }

    int length() {
        return value.length();
    }

    String name() {
        LOGGER.entering(Placeholder.class.getName(), "name");
        final String result;
        final Matcher matcher = PATTERN.matcher(value);
        if (matcher.find()) {
            result = matcher.group("name");
        } else {
            final IllegalArgumentException thrown = new IllegalArgumentException("Placeholder is not valid!!");
            LOGGER.throwing(Placeholder.class.getName(), "name", thrown);
            throw thrown;
        }
        LOGGER.exiting(Placeholder.class.getName(), "name", result);
        return result;
    }

    Optional<String> style() {
        LOGGER.entering(Placeholder.class.getName(), "style");
        final Optional<String> result = Optional.of(PATTERN.matcher(value))
            .filter(Matcher::find)
            .map(m -> m.group("style"))
            .filter(not(String::isBlank));
        LOGGER.exiting(Placeholder.class.getName(), "stye", result.isPresent());
        return result;
    }

    PlaceholderReplacer replaceWithIndex(final int index) {
        LOGGER.entering(Placeholder.class.getName(), "replaceWithIndex", index);
        final PlaceholderReplacer result = new PlaceholderReplacer(index);
        LOGGER.exiting(Placeholder.class.getName(), "replaceWithIndex", result);
        return result;
    }

    @Override
    public int hashCode() {
        return 97 * Objects.hashCode(this.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Placeholder other = (Placeholder) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return "Placeholder{" + "placeholder=" + value + '}';
    }

    public class PlaceholderReplacer {

        private final int index;

        PlaceholderReplacer(final int index) {
            this.index = index;
        }

        String inside(final String original) {
            LOGGER.entering(PlaceholderReplacer.class.getName(), "inside", original);
            String replacement = createReplacement();
            final String result = original.replace(value, replacement);
            LOGGER.exiting(PlaceholderReplacer.class.getName(), "inseide", result);
            return result;
        }

        private String createReplacement() {
            LOGGER.entering(PlaceholderReplacer.class.getName(), "createReplacement");
            final String result = style()
                .map(style -> String.format("{%d%s}", index, style))
                .orElseGet(() -> String.format("{%d}", index));
            LOGGER.exiting(PlaceholderReplacer.class.getName(), "createReplacement", result);
            return result;
        }
    }
}
