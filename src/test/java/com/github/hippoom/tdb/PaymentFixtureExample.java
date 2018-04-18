package com.github.hippoom.tdb;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.util.List;

import static com.github.hippoom.tdb.GenericTestDataListBuilder.listOfSize;
import static com.github.hippoom.tdb.PaymentFixture.payment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PaymentFixtureExample {

    @Test
    public void it_should_support_navigation_api() {

        List<Payment> payments = listOfSize(3,
            seq -> payment())
            .theFirst(1).apply(payment -> payment.withAmount(20))
            .number(2).apply(payment -> payment.withAmount(25))
            .theLast(1).apply(payment -> payment.withAmount(30))
            .build(PaymentFixture::build);

        assertThat(payments.size(), is(3));
        assertThat(payments.get(0).getAmount(), is(Money.of(20, "CNY")));
        assertThat(payments.get(1).getAmount(), is(Money.of(25, "CNY")));
        assertThat(payments.get(2).getAmount(), is(Money.of(30, "CNY")));
    }

    @Test
    public void it_should_support_indices() {

        List<Payment> payments = listOfSize(3,
            seq -> payment())
            .allWithSeq(PaymentFixture::withAmount)
            .build();

        assertThat(payments.size(), is(3));
        assertThat(payments.get(0).getAmount(), is(Money.of(1, "CNY")));
        assertThat(payments.get(1).getAmount(), is(Money.of(2, "CNY")));
        assertThat(payments.get(2).getAmount(), is(Money.of(3, "CNY")));
    }
}
