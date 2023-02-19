package io.github.sebastiantoepfer.ddd.media.core.utils.cases;

public class SnakeCased implements Cased {

    private final String value;

    public SnakeCased(final String value) {
        this.value = value;
    }

    @Override
    public String toCase() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            result = new SnakeCaseCharAppendable(value.charAt(i)).appendTo(result);
        }
        return result.toString();
    }
}
