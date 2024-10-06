package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.CustomerRequest;
import git.darul.tokonyadia.dto.request.SearchCustomerRequest;
import git.darul.tokonyadia.dto.response.CustomerResponse;
import git.darul.tokonyadia.service.CustomerService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.CUSTOMER_API)
public class CustomerController {

    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.create(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Succesfully Create Data", customerResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(@RequestParam(name = "page",required = false, defaultValue = "1") Integer page,
                                             @RequestParam(name = "size",required = false, defaultValue = "10") Integer size,
                                             @RequestParam(name = "name", required = false) String name
                                             ) {
        SearchCustomerRequest customerRequest = SearchCustomerRequest.builder()
                .name(name)
                .page(page)
                .size(size)
                .build();
        Page<CustomerResponse> customerResponses = customerService.getAll(customerRequest);
        return ResponseUtil.buildResponsePaging(HttpStatus.OK, "Succesfully Get All Data Customer", customerResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully Get Data", customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id ,@RequestBody CustomerRequest request) {
        CustomerResponse resultCustomer = customerService.update(id, request);
        return  (resultCustomer != null) ?
                ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully Update Data", resultCustomer) :
                ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Id not found", resultCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        return customerService.delete(id) ?
                ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully delete data", null) :
                ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "id not found", null);

    }


}
