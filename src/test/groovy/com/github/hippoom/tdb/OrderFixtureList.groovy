package com.github.hippoom.tdb

import java.util.function.Function

import static java.util.stream.Collectors.toList

class OrderFixtureList {
    private List<OrderFixture> elements
    private List<OrderFixture> currentElements

    OrderFixtureList(List<OrderFixture> elements) {
        this.elements = elements
    }

    def theFirst(int size, Function<OrderFixture, Void> wither) {
        for (int i = 0; i < size; i++) {
            wither.apply(elements.get(i))
        }
        this
    }

    def theLast(int size, Function<OrderFixture, Void> wither) {
        for (int i = 0; i < size; i++) {
            wither.apply(elements.get(elements.size() - 1 - i))
        }
        this
    }

    def theFirst(int size) {
        final List<OrderFixture> currentElements = new ArrayList()
        for (int i = 0; i < size; i++) {
            currentElements.add(elements.get(i))
        }
        this.currentElements = currentElements
        this
    }

    def theLast(int size) {
        final List<OrderFixture> currentElements = new ArrayList()
        for (int i = 0; i < size; i++) {
            currentElements.add(elements.get(elements.size() - 1 - i))
        }
        this.currentElements = currentElements
        this
    }

    def should(Function<OrderFixture, Void> wither) {
        this.currentElements.stream()
            .forEach { wither.apply(it) }
        this
    }

    def number(int sequence, Function<OrderFixture, Void> wither) {
        wither.apply(elements.get(sequence - 1))
        this
    }

    def number(int ... sequence) {
        final List<OrderFixture> currentElements = new ArrayList()
        for (int current : sequence) {
            currentElements.add(elements.get(current - 1))
        }
        this.currentElements = currentElements
        this
    }

    def build() {
        elements.stream()
            .map { fixture -> fixture.build() }
            .collect(toList())
    }

    def all(Function<OrderFixture, Void> wither) {
        for (OrderFixture current : elements) {
            wither.apply(current)
        }
        this
    }

}
