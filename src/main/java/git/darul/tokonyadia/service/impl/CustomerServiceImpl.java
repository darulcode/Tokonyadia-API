package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.CustomerRequest;
import git.darul.tokonyadia.dto.request.SearchCustomerRequest;
import git.darul.tokonyadia.dto.response.CustomerResponse;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.repository.CustomerRepository;
import git.darul.tokonyadia.service.CustomerService;
import git.darul.tokonyadia.spesification.CustomerSpesification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
        Customer customerResult = customerRepository.saveAndFlush(customer);
        return getCustomerResponse(customerResult);
    }


    @Override
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Specification<Customer> customerSpecification = CustomerSpesification.spesificationCustomer(request);
        return  customerRepository.findAll(customerSpecification, pageable).map(
                customer -> getCustomerResponse(customer)
        );
    }

    @Override
    public CustomerResponse getById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        Customer resultCustomer = customer.isPresent() ? customer.get() : null;
        return getCustomerResponse(resultCustomer);
    }

    @Override
    public CustomerResponse update(String id,CustomerRequest request) {
        Optional<Customer> customer = customerRepository.findById(id);
        Customer resultCustomer = customer.isPresent() ? customer.get() : null;
        resultCustomer.setName(request.getName());
        resultCustomer.setEmail(request.getEmail());
        resultCustomer.setAddress(request.getAddress());
        resultCustomer.setPhoneNumber(request.getPhoneNumber());
        customerRepository.save(resultCustomer);
        return getCustomerResponse(resultCustomer);
    }

    @Override
    public Boolean delete(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        if (customer == null) {return null;}
        return  CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
}
