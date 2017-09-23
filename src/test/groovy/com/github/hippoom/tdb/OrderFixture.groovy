package com.github.hippoom.tdb

import static com.github.hippoom.tdb.Location.TAKE_AWAY

class OrderFixture {

    def target = new Order(Order.Identity.next())

    private OrderFixture() {
        target.with(TAKE_AWAY)
    }

    def withId(int id) {
        target.id = Order.Identity.of(String.valueOf(id))
        this
    }

    def is(Location location) {
        target.with(location)
        this
    }

    def payed() {
        target.payed()
        this
    }


    static OrderFixture order() {
        new OrderFixture()
    }


    def build() {
        target
    }
}
