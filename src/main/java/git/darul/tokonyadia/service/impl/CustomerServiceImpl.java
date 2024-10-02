package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.repository.CustomerRepository;
import git.darul.tokonyadia.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {return null;}
        return customer.get();
    }

    @Override
    public Customer update(String id,Customer customer) {
        Optional<Customer> customerResult = customerRepository.findById(id);
        if (customerResult.isEmpty()) {return null;}
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public String delete(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {return "id not found";}
        customerRepository.deleteById(id);
        return "id deleted";
    }
}
