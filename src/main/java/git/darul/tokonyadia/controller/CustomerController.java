package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.CustomerRequest;
import git.darul.tokonyadia.dto.response.CustomerResponse;
import git.darul.tokonyadia.service.CustomerService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseUtil.buildResponseEntity(HttpStatus.CREATED, "Succesfully Create Data", customerResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> customerResponses = customerService.getAll();
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully Get All Data", customerResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getById(id);
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully Get Data", customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id , @RequestBody CustomerRequest request) {
        CustomerResponse resultCustomer = customerService.update(id, request);
        return  (resultCustomer != null) ?
                ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully Update Data", resultCustomer) :
                ResponseUtil.buildResponseEntity(HttpStatus.BAD_REQUEST, "Id not found", resultCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        return customerService.delete(id) ?
                ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully delete data", null) :
                ResponseUtil.buildResponseEntity(HttpStatus.BAD_REQUEST, "id not found", null);

    }


}
