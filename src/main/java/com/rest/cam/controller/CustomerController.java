package com.rest.cam.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.cam.dto.CustomerDto;
import com.rest.cam.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@PostMapping("create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto customer = customerService.createCustomer(customerDto);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<CustomerDto> getAllCustomers(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		PageRequest page = PageRequest.of(pageNo, pageSize, sort);

		List<CustomerDto> allCustomers = customerService.getAllCustomers(page);
		return new ResponseEntity(allCustomers, HttpStatus.OK);
	}

	@GetMapping("/{customerid}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerid") Long customerid) {
		CustomerDto customerById = customerService.getCustomerById(customerid);
		return new ResponseEntity<>(customerById, HttpStatus.OK);
	}

	@PutMapping("/update/{customerid}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto,
			@PathVariable Long customerid) {
		CustomerDto updateCustomerById = customerService.updateCustomerById(customerDto, customerid);
		return new ResponseEntity<>(updateCustomerById, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{customerid}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerid) {
		customerService.deleteCustomerById(customerid);
		return new ResponseEntity<>("Customer is deleted successfully", HttpStatus.OK);
	}
}
