package com.github.hippoom.tdb

import static com.github.hippoom.tdb.Location.TAKE_AWAY

class OrderFixture extends GenericTestDataBuilder<Order> {

    def target = new Order(Order.Identity.next())

    private OrderFixture() {
        target.with(TAKE_AWAY)
    }

    def is(Location location) {
        target.with(location)
        this
    }

    def payed() {
        target.payed()
        this
    }


    static OrderFixture anOrder() {
        new OrderFixture()
    }


    static OrderFixture listOfSize(int size) {
        OrderFixture fixture = new OrderFixture()
        List<OrderFixture> elements = new ArrayList<>()
        for (int i = 0; i < size; i++) {
            elements.add(new OrderFixture())
        }
        fixture.add(elements)
        fixture
    }

    @Override
    Order build() {
        target
    }
}
