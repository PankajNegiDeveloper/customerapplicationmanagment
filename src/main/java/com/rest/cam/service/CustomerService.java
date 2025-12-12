package com.rest.cam.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rest.cam.dto.CustomerDto;

public interface CustomerService {

	CustomerDto createCustomer(CustomerDto customerDto);
	List<CustomerDto> getAllCustomers(PageRequest page);
	CustomerDto getCustomerById(Long customerid);
	CustomerDto updateCustomerById(CustomerDto customerDto, Long customerid);
	CustomerDto deleteCustomerById(Long customerid);
}
