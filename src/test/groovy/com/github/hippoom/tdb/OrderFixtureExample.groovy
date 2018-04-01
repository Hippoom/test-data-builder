package com.github.hippoom.tdb

import spock.lang.Specification

import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize
import static com.github.hippoom.tdb.Location.IN_STORE
import static com.github.hippoom.tdb.Location.TAKE_AWAY
import static com.github.hippoom.tdb.Order.Status.PAID
import static com.github.hippoom.tdb.Order.Status.PAYMENT_EXPECTED
import static com.github.hippoom.tdb.OrderFixture.order

class OrderFixtureExample extends Specification {

    def "it should support `listOfSize` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .build { it.build() }

        then:

        assert orders.size() == 3
    }

    def "it should support `first x` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .theFirst(2, { it.is(IN_STORE) })
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == TAKE_AWAY
    }

    def "it should support `first x apply` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .theFirst(2).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == TAKE_AWAY
    }

    def "it should support `first x` api with multiple wither"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .theFirst(2, { it.is(IN_STORE).payed() })
            .build { it.build() }

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

        def orders = listOfSize(3, { order().withId(it) })
            .theFirst(1, { it.is(IN_STORE).payed() })
            .number(2, { it.payed() })
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(0).status == PAID

        assert orders.get(1).location == TAKE_AWAY
        assert orders.get(1).status == PAID

        assert orders.get(2).location == TAKE_AWAY
        assert orders.get(2).status == PAYMENT_EXPECTED
    }

    def "it should support `number x apply` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .number(2, 3).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "`number 1..x` is not equivalent to `first x`"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .number(1, 3).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE

        assert orders.get(1).location == TAKE_AWAY

        assert orders.get(2).location == IN_STORE
    }

    def "`number ...` is not equivalent to `last x`"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .number(1, 2).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == TAKE_AWAY
    }

    def "it should support `last x` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .theLast(2, { it.is(IN_STORE) })
            .build { it.build() }

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "it should support `last x apply` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .theLast(2).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE
    }

    def "it should support `range x,y` api"() {

        when:

        def orders = listOfSize(5, { order().withId(it) })
            .range(2, 4, { it.is(IN_STORE) })
            .build { it.build() }

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE

        assert orders.get(3).location == IN_STORE

        assert orders.get(4).location == TAKE_AWAY
    }

    def "it should support `range x,y apply` api"() {

        when:

        def orders = listOfSize(5, { order().withId(it) })
            .range(2, 4).apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == TAKE_AWAY

        assert orders.get(1).location == IN_STORE

        assert orders.get(2).location == IN_STORE

        assert orders.get(3).location == IN_STORE

        assert orders.get(4).location == TAKE_AWAY
    }

    def "it should support `all` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .all({ it.is(IN_STORE) })
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == IN_STORE
    }

    def "it should support `all apply` api"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .all().apply { it.is(IN_STORE) }
            .build { it.build() }

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == IN_STORE
    }

    def "it should provide built-in `build` implementation"() {

        when:

        def orders = listOfSize(3, { order().withId(it) })
            .all().apply { it.is(IN_STORE) }
            .build()

        then:

        assert orders.get(0).location == IN_STORE
        assert orders.get(1).location == IN_STORE
        assert orders.get(2).location == IN_STORE
    }
}
