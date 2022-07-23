package co.reldyn.learning.springboottestingpostgresql.customer;

import com.github.javafaker.Faker;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerRepositoryTest {
    private static Faker faker;

    @Autowired
    private CustomerRepository underTest;


    Customer createNewCustomer() {
        Customer customer = Customer.builder()
                .name(faker.name().name())
                .email(faker.internet().safeEmailAddress())
                .creditLimit(faker.number().numberBetween(1000, 10000))
                .build();
        return underTest.saveAndFlush(customer);
    }
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void init() {
        faker = new Faker();
    }

    @Test
    void given_customer_when_saved_then_customer_available_for_given_email() {
        // given
        Customer customer = createNewCustomer();
        // when
        final Boolean exits = underTest.selectExistsEmail(customer.getEmail());
        // then
        assertThat(exits).isTrue();
    }

    @Test
    void given_customer_when_wrong_email_then_should_return_false() {
        //given
        Customer customer = createNewCustomer();
        //when
        final Boolean exits = underTest.selectExistsEmail("a" + customer.getEmail());
        //given
        assertThat(exits).isFalse();
    }

}