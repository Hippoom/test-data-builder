package com.github.hippoom.tdb;

import static com.github.hippoom.tdb.PaymentFixture.payment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

import com.github.hippoom.tdb.Payment.Identity;
import com.github.hippoom.tdb.reflection.PrivateFieldSetter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PrivateFieldSetterExample {

    @Rule
    public ExpectedException thrown = none();

    @Test
    public void it_should_private_one_line_api_to_change_private_fields() {

        Identity id = Identity.of("1");

        Payment payment = payment().build();

        PrivateFieldSetter.set(payment, "id", id);

        assertThat(payment.getId(), is(id));
    }

    @Test
    public void it_should_throw_non_check_exception_given_field_does_not_recognized() {

        thrown.expect(NoSuchFieldException.class);
        thrown.expectMessage("fieldDoesNotExist");

        Payment payment = payment().build();

        PrivateFieldSetter.set(payment, "fieldDoesNotExist", "hello world");

    }
}
