package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.CUSTOMER_API)
public class CustomerController {

    private final CustomerService customerService;
    private final PathMatcher mvcPathMatcher;

    @Autowired
    public CustomerController(CustomerService customerService, PathMatcher mvcPathMatcher) {
        this.customerService = customerService;
        this.mvcPathMatcher = mvcPathMatcher;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable String id , @RequestBody Customer customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id) {
        return customerService.delete(id);
    }


}
