package com.github.hippoom.tdb;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import javax.money.MonetaryAmount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.javamoney.moneta.Money;

@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Getter
@NoArgsConstructor(access = PRIVATE)
public class Payment {

    private Identity id;

    private Order.Identity orderId;

    private MonetaryAmount amount = Money.of(0, "CNY");

    public Payment(Identity id) {
        this.id = id;
    }

    public void with(Order.Identity orderId) {
        this.orderId = orderId;
    }

    public void with(MonetaryAmount amount) {
        this.amount = amount;
    }

    @Value
    public static class Identity {

        private String value;

        public static Identity of(String value) {
            return new Identity(value);
        }

        public static Identity next() {
            return of(UUID.randomUUID().toString());
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
