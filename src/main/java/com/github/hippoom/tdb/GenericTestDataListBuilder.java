package com.github.hippoom.tdb;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import lombok.Getter;

public class GenericTestDataListBuilder<T> {

    @Getter(PRIVATE)
    private List<T> elements;
    private List<T> currentElements;

    private GenericTestDataListBuilder(List<T> elements) {
        this.elements = elements;
    }

    public GenericTestDataListBuilder<T> theFirst(int size,
        Function<T, T> wither) {
        return theFirst(size).apply(wither);
    }

    public GenericTestDataListBuilder<T> theFirst(int size) {
        return number(IntStream.range(1, size + 1).toArray());
    }

    public GenericTestDataListBuilder<T> theLast(int size,
        Function<T, T> wither) {
        return theLast(size).apply(wither);
    }

    public GenericTestDataListBuilder<T> theLast(int size) {
        int startElementSequence = getElements().size() - size + 1;
        int lastElementSequence = getElements().size() + 1;
        return number(IntStream.range(startElementSequence, lastElementSequence).toArray());
    }

    public GenericTestDataListBuilder<T> apply(Function<T, T> wither) {
        this.currentElements
            .forEach(wither::apply);
        return this;
    }

    public GenericTestDataListBuilder<T> number(int sequence,
        Function<T, T> wither) {
        return number(sequence).apply(wither);
    }

    public GenericTestDataListBuilder<T> number(int... sequences) {
        this.currentElements = IntStream.of(sequences)
            .map(sequence -> sequence - 1)
            .mapToObj(index -> getElements().get(index))
            .collect(toList());
        return this;
    }

    public GenericTestDataListBuilder<T> all(Function<T, T> wither) {
        return all().apply(wither);
    }

    public GenericTestDataListBuilder<T> all() {
        this.currentElements = new ArrayList<>(getElements());
        return this;
    }

    public <B> List<B> build(Function<T, B> builder) {
        return elements.stream()
            .map(builder)
            .collect(toList());
    }

    public static <T> GenericTestDataListBuilder<T> listOfSize(int size,
        Function<Integer, T> byDefault) {
        return new GenericTestDataListBuilder<>(
            IntStream.range(0, size)
                .map(index -> index + 1)
                .mapToObj(byDefault::apply)
                .collect(toList()));
    }

}
