package com.serhat.customerproject.service;

import com.serhat.customerproject.dto.CustomerDto;
import com.serhat.customerproject.entity.Customer;
import com.serhat.customerproject.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private  CustomerRepository customerRepository;

    @Mock
    private  ModelMapper modelMapper;

    @Test
    public void getCustomers() {
        Customer customer = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        CustomerDto customerDto = modelMapper.map(customer,CustomerDto.class);
        List<CustomerDto> actualCustomerDtoList = new ArrayList<>();
        actualCustomerDtoList.add(customerDto);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDto> expectedCustomerResult = customerService.getCustomers();
        assertEquals(actualCustomerDtoList,expectedCustomerResult);
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(2L);
        Long id = 2L;
        Optional<Customer> optionalCustomer = Optional.of(customer);
        CustomerDto customerDto = modelMapper.map(customer,CustomerDto.class);
        when(customerRepository.findById(id)).thenReturn(optionalCustomer);
        CustomerDto customerDto1 = customerService.getCustomerById(id);
        assertEquals(customerDto1,customerDto);
    }

    @Test
    public void addCustomer() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPhoneNumber(15);
        Customer customer = modelMapper.map(customerDto,Customer.class);
        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerDto  expectedResult = customerService.addCustomer(customerDto);
        assertEquals(customer,expectedResult);
        verify(customerRepository,times(1)).save(customer);

    }

    @Test
    public void deleteCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        Long id = 1L;
        Optional<Customer> optionalCustomer = Optional.of(customer);
        when(customerRepository.findById(id))
                .thenReturn(optionalCustomer);
        Boolean expectedCustomer = customerService.deleteCustomerById(id);
        assertTrue(expectedCustomer);
    }

    @Test
    public void updateCustomer() {
    }
}