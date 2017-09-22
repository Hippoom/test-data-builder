package com.github.hippoom.tdb

import spock.lang.Specification

import static com.github.hippoom.tdb.Location.IN_STORE
import static com.github.hippoom.tdb.Location.TAKE_AWAY
import static com.github.hippoom.tdb.Order.Status.PAID
import static com.github.hippoom.tdb.Order.Status.PAYMENT_EXPECTED

class OrderFixtureExample extends Specification {

    def "it should support `listOfSize` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .done()

        then:

        assert orders.size() == 3
    }

    def "it should support `first x` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theFirst(2, { it.with(IN_STORE) })
            .done()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == TAKE_AWAY
    }

    def "it should support `first x should` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theFirst(2).should { it.with(IN_STORE) }
            .done()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == TAKE_AWAY
    }

    def "it should support `first x` api with multiple wither"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theFirst(2, { it.with(IN_STORE).payed() })
            .done()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(0).status == PAID

        assert orders.get(1).location == IN_STORE
        assert orders.get(1).status == PAID

        assert orders.get(2).location == TAKE_AWAY
        assert orders.get(2).status == PAYMENT_EXPECTED
    }

    def "it should support `number x` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theFirst(1, { it.with(IN_STORE).payed() })
            .number(2, { it.payed() })
            .done()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(0).status == PAID

        assert orders.get(1).location == TAKE_AWAY
        assert orders.get(1).status == PAID

        assert orders.get(2).location == TAKE_AWAY
        assert orders.get(2).status == PAYMENT_EXPECTED
    }

    def "it should support `number x should` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .number(2, 3).should { it.with(IN_STORE) }
            .done()

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "it should support `last x` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theLast(2, { it.with(IN_STORE) })
            .done()

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "it should support `last x should` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .theLast(2).should { it.with(IN_STORE) }
            .done()

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "it should support `all` api"() {

        when:

        def orders = OrderFixture.listOfSize(3)
            .all({ it.with(IN_STORE) })
            .done()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == IN_STORE
    }
}
