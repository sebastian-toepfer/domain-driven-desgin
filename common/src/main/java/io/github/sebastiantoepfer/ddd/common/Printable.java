package io.github.sebastiantoepfer.ddd.common;

public interface Printable {
    <T extends Media<T>> T printOn(T media);
}
