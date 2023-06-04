package io.github.sebastiantoepfer.ddd.common;

public interface Printable {
    <T extends Media<T>> T printOn(T media);

    final class Empty implements Printable {

        @Override
        public <T extends Media<T>> T printOn(final T media) {
            return media;
        }
    }
}
