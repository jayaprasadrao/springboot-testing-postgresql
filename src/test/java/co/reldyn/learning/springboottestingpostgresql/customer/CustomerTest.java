package co.reldyn.learning.springboottestingpostgresql.customer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Objects;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    private Customer customer;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name(faker.name().name())
                .email(faker.internet().safeEmailAddress())
                .creditLimit(faker.number().numberBetween(1000, 10000))
                .profile("")
                .build();
    }

    @Test
    void given_valid_customer_when_check_for_validation_then_no_violations() {
        Set<ConstraintViolation<Customer>> violations = getViolations(customer);
        assertThat(violations).isEmpty();
    }

    @Test
    void given_null_to_customer_name_when_check_for_validation_then_should_have_name_violation() {
        customer.setName(null);
        Set<ConstraintViolation<Customer>> violations = getViolations(customer);
        assertThat(violations).hasSize(1);
        assertThat(Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getPropertyPath()).hasToString("name");
    }

    Set<ConstraintViolation<Customer>> getViolations(Customer customer) {
        Validator validator;
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        return validator.validate(customer);
    }

}