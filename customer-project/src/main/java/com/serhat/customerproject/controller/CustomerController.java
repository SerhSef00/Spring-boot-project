package com.serhat.customerproject.controller;

import com.serhat.customerproject.dto.CustomerDto;
import com.serhat.customerproject.entity.Customer;
import com.serhat.customerproject.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get-customers")
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerDto>> getCustomers(){
        List<CustomerDto> customerDtos = customerService.getCustomers();
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/get-customer/{id}")
    @Operation(summary = "Get customer by id")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/get-paging-customer")
    public Page<Customer> pagination(@RequestParam Integer customerSize,
                                     @RequestParam  Integer customer){
        Pageable pageable = PageRequest.of(customer,customerSize);
        return customerService.findAll(pageable);
    }


    @PostMapping("/add-customer")
    @Operation(summary = "Save customer")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto dto = customerService.addCustomer(customerDto);
        return new ResponseEntity<>(customerDto, HttpStatus.CREATED);

    }

    @PutMapping("/update-customer/{id}")
    @Operation(summary ="Update customer by id")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
                                                      @RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
    }

    @DeleteMapping("/delete-customer/{id}")
    @Operation(summary = "Delete customer by id")
    public ResponseEntity<Boolean> deleteCustomerById(@PathVariable("id") Long id){
        Boolean status = customerService.deleteCustomerById(id);
        return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
    }


}
