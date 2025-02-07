package com.rest.cam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.cam.dto.CustomerDto;
import com.rest.cam.entity.Customer;
import com.rest.cam.exception.NoResourceFoundException;
import com.rest.cam.repository.CustomerRepo;
import com.rest.cam.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	public CustomerServiceImpl(CustomerRepo customerRepo) {
		super();
		this.customerRepo = customerRepo;
	}

	// DTO to Entity
	public Customer mapDtoToEntity(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setCustomerid(customer.getCustomerid());
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		return customer;
	}

	// Entity to DTO
	public CustomerDto mapEntityToDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerid(customer.getCustomerid());
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		return customerDto;
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer createdCustomer = customerRepo.save(mapDtoToEntity(customerDto));
		return mapEntityToDto(createdCustomer);
	}

	@Override
	public List<CustomerDto> getAllCustomers(Pageable page) {
		Page<Customer> findAllCustomers = customerRepo.findAll(page);
//		List<Customer> allCustomers = customerRepo.findAll();
		List<Customer> customers = findAllCustomers.getContent();
		return customers.stream().map(customer -> mapEntityToDto(customer)).toList();
	}

	@Override
	public CustomerDto getCustomerById(Long customerid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		return mapEntityToDto(customer);
	}

	@Override
	public CustomerDto updateCustomerById(CustomerDto customerDto, Long customerid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));

		customer.setEmail(customerDto.getEmail());
		customer.setName(customerDto.getName());

		Customer updatedComment = customerRepo.save(customer);
		return mapEntityToDto(updatedComment);
	}

	@Override
	public CustomerDto deleteCustomerById(Long customerid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		customerRepo.delete(customer);
		return null;
	}
}
