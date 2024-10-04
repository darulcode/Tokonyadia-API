package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.CustomerRequest;
import git.darul.tokonyadia.dto.request.SearchCustomerRequest;
import git.darul.tokonyadia.dto.response.CustomerResponse;
import git.darul.tokonyadia.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    Page<CustomerResponse> getAll(SearchCustomerRequest request);
    CustomerResponse getById(String id);
    CustomerResponse update(String id, CustomerRequest request);
    Boolean delete(String id);
}
