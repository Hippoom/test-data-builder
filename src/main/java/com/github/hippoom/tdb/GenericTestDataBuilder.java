package com.github.hippoom.tdb;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import lombok.Getter;

public abstract class GenericTestDataBuilder<T> {

    @Getter(PRIVATE)
    private List<GenericTestDataBuilder<T>> elements;
    private List<GenericTestDataBuilder<T>> currentElements;

    protected void add(List<GenericTestDataBuilder<T>> elements) {
        this.elements = elements;
    }

    public GenericTestDataBuilder theFirst(int size,
        Function<GenericTestDataBuilder, Void> wither) {
        return theFirst(size).should(wither);
    }

    public GenericTestDataBuilder<T> theFirst(int size) {
        return number(IntStream.range(1, size + 1).toArray());
    }

    public GenericTestDataBuilder<T> theLast(int size,
        Function<GenericTestDataBuilder, Void> wither) {
        return theLast(size).should(wither);
    }

    public GenericTestDataBuilder<T> theLast(int size) {
        int startElementSequence = getElements().size() - size + 1;
        int lastElementSequence = getElements().size() + 1;
        return number(IntStream.range(startElementSequence, lastElementSequence).toArray());
    }

    public GenericTestDataBuilder<T> should(Function<GenericTestDataBuilder, Void> wither) {
        this.currentElements
            .forEach(wither::apply);
        return this;
    }

    public GenericTestDataBuilder<T> number(int sequence,
        Function<GenericTestDataBuilder, Void> wither) {
        return number(sequence).should(wither);
    }

    public GenericTestDataBuilder<T> number(int... sequences) {
        this.currentElements = IntStream.of(sequences)
            .map(sequence -> sequence - 1)
            .mapToObj(index -> getElements().get(index))
            .collect(toList());
        return this;
    }

    public List<T> done() {
        return elements.stream()
            .map(GenericTestDataBuilder::build)
            .collect(toList());
    }

    public GenericTestDataBuilder<T> all(Function<GenericTestDataBuilder, Void> wither) {
        this.currentElements = new ArrayList<>(getElements());
        return should(wither);
    }

    public abstract T build();
}
