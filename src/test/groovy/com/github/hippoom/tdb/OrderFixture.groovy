package com.github.hippoom.tdb

import static com.github.hippoom.tdb.Location.TAKE_AWAY

class OrderFixture {

    def target = new Order(Order.Identity.next())

    OrderFixture() {
        target.with(TAKE_AWAY)
    }

    def with(Location location) {
        target.with(location)
        this
    }

    def payed() {
        target.payed()
        this
    }

    def build() {
        target
    }

    static OrderFixture anOrder() {
        new OrderFixture()
    }


    static OrderFixtureList listOfSize(int size) {
        List<OrderFixture> elements = new ArrayList<>()
        for (int i = 0; i < size; i++) {
            elements.add(new OrderFixture())
        }
        new OrderFixtureList(elements)
    }
}
