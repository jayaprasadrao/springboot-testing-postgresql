package co.reldyn.learning.springboottestingpostgresql.customer;

import co.reldyn.learning.springboottestingpostgresql.customer.exception.BadRequestException;
import co.reldyn.learning.springboottestingpostgresql.customer.exception.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    /**
     * list all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Add new customer
     *
     * @param customer customer
     */
    public void addCustomer(Customer customer) {
        Boolean existsEmail =
                customerRepository.selectExistsEmail(customer.getEmail());
        if (existsEmail) {
            throw new BadRequestException(
                    "Email " + customer.getEmail() + " taken"
            );
        }
        customerRepository.save(customer);
    }

    /**
     * delete customer
     *
     * @param id customer id
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(
                    "Customer with id " + id + " does not exists");
        }
        customerRepository.deleteById(id);
    }
}
