package git.darul.tokonyadia.service;

import git.darul.tokonyadia.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    List<Customer> getAll();
    Customer getById(String id);
    Customer update(String id, Customer customer);
    String delete(String id);
}
