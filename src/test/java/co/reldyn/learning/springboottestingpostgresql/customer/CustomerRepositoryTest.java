package co.reldyn.learning.springboottestingpostgresql.customer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


//@DataJpaTest
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerRepositoryTest {
    private static Faker faker;

    @Autowired
    private CustomerRepository underTest;

    @AfterEach
    void tearDownAll() {
        underTest.deleteAll();
    }

    @BeforeAll
    static void initAll() {
        faker = new Faker();
    }

    @Test
    void customerShouldBeBasedOnEmail() {
        // given
        Customer customer = Customer.builder()
                .name(faker.name().name())
                .email(faker.internet().safeEmailAddress())
                .creditLimit(faker.number().numberBetween(1000, 10000))
                .build();
        underTest.saveAndFlush(customer);

        // when
        final Boolean exits = underTest.selectExistsEmail(customer.getEmail());

        // then
        assertThat(exits).isTrue();
    }
}