package com.github.hippoom.tdb;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class GenericTestDataBuilder<T> {

    private List<GenericTestDataBuilder<T>> elements;
    private List<GenericTestDataBuilder<T>> currentElements;

    protected void add(List<GenericTestDataBuilder<T>> elements) {
        this.elements = elements;
    }

    public GenericTestDataBuilder theFirst(int size, Function<GenericTestDataBuilder, Void> wither) {
        for (int i = 0; i < size; i++) {
            wither.apply(elements.get(i));
        }
        return this;
    }

    public GenericTestDataBuilder<T> theFirst(int size) {
        final List<GenericTestDataBuilder<T>> currentElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            currentElements.add(elements.get(i));
        }
        this.currentElements = currentElements;
        return this;
    }

    public GenericTestDataBuilder<T> theLast(int size, Function<GenericTestDataBuilder, Void> wither) {
        for (int i = 0; i < size; i++) {
            wither.apply(elements.get(elements.size() - 1 - i));
        }
        return this;
    }

    public GenericTestDataBuilder<T> theLast(int size) {
        final List<GenericTestDataBuilder<T>> currentElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            currentElements.add(elements.get(elements.size() - 1 - i));
        }
        this.currentElements = currentElements;
        return this;
    }

    public GenericTestDataBuilder<T> should(Function<GenericTestDataBuilder, Void> wither) {
        this.currentElements
            .forEach(wither::apply);
        return this;
    }

    public GenericTestDataBuilder<T> number(int sequence, Function<GenericTestDataBuilder, Void> wither) {
        wither.apply(elements.get(sequence - 1));
        return this;
    }

    public GenericTestDataBuilder<T> number(int... sequence) {
        final List<GenericTestDataBuilder<T>> currentElements = new ArrayList<>();
        for (int current : sequence) {
            currentElements.add(elements.get(current - 1));
        }
        this.currentElements = currentElements;
        return this;
    }

    public List<T> done() {
        return elements.stream()
            .map(GenericTestDataBuilder::build)
            .collect(toList());
    }

    public GenericTestDataBuilder<T> all(Function<GenericTestDataBuilder, Void> wither) {
        for (GenericTestDataBuilder current : elements) {
            wither.apply(current);
        }
        return this;
    }

    public abstract T build();
}
