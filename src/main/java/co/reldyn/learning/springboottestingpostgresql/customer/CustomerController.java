package co.reldyn.learning.springboottestingpostgresql.customer;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public void addCustomer(@Valid @RequestBody CustomerDto dto) {
        Customer customer = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .creditLimit(dto.getCreditLimit())
                .profile(dto.getProfile())
                .build();
        customerService.addCustomer(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
    }

}


@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CustomerDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    private Integer creditLimit;

    private String profile;
}
