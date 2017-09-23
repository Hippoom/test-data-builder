package com.github.hippoom.tdb;

import org.javamoney.moneta.Money;

public class PaymentFixture {

    private Payment target = new Payment(Payment.Identity.next());

    private PaymentFixture() {
        target.with(Order.Identity.next());
        target.with(Money.of(32, "CNY"));
    }

    public PaymentFixture withAmount(int value) {
        target.with(Money.of(value, "CNY"));
        return this;
    }

    public Payment build() {
        return target;
    }

    public static PaymentFixture payment() {
        return new PaymentFixture();
    }


}
