package com.github.hippoom.tdb;

import com.github.hippoom.tdb.Payment.Identity;
import com.github.hippoom.tdb.reflection.PrivateFieldSetter;
import org.javamoney.moneta.Money;

public class PaymentFixture {

    private Payment target = new Payment(Payment.Identity.next());

    private PaymentFixture() {
        target.with(Order.Identity.next());
        target.with(Money.of(32, "CNY"));
    }

    public PaymentFixture with(Identity identity) {
        PrivateFieldSetter.set(target, "id", identity);
        return this;
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
