package com.serhat.customerproject.service;

import com.serhat.customerproject.dto.CustomerDto;
import com.serhat.customerproject.entity.Customer;
import com.serhat.customerproject.exception.CustomerNotFoundException;
import com.serhat.customerproject.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public List<CustomerDto> getCustomers(){
        log.info("fetching all customers");
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers
                .stream().map(customer -> modelMapper.map(customer,CustomerDto.class))
                .collect(Collectors.toList());
        return customerDtos;
    }

    public CustomerDto getCustomerById(Long id){
        log.info("fetch customer by id: {}" ,id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->new CustomerNotFoundException("Musteri bulunamadi" +id));

        return modelMapper.map(customer,CustomerDto.class);
    }

    @Transactional
    public CustomerDto addCustomer(CustomerDto customerDto){
        log.info("Save the new customers to database");
        Customer customer = modelMapper.map(customerDto,Customer.class);
        return modelMapper.map(customerRepository.save(customer),CustomerDto.class);
    }
    @Transactional
    public boolean deleteCustomerById(Long id){
        log.info("deleting customer");
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customerRepository.deleteById(id);
            return true;
        }
        throw new CustomerNotFoundException("Customer could not found by id: " + id);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id,CustomerDto customerDto){
        log.info("Updating customer by id:{}", id);
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customer.get().setFirstName(customerDto.getFirstName());
            customer.get().setLastName(customerDto.getLastName());
            customer.get().setPhoneNumber(customerDto.getPhoneNumber());
            return modelMapper.map(customerRepository.save(customer.get()),CustomerDto.class);
        }
        throw new CustomerNotFoundException("Customer not found" + id);
    }

    public Page<Customer> findAll(Pageable pageable) {
        log.info("Get all customers by paging");
        return customerRepository.findAll(pageable);
    }
}
