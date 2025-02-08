package com.rest.cam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rest.cam.dto.OrderDto;
import com.rest.cam.entity.Customer;
import com.rest.cam.entity.Order;
import com.rest.cam.exception.NoResourceFoundException;
import com.rest.cam.exception.OrderApiException;
import com.rest.cam.repository.CustomerRepo;
import com.rest.cam.repository.OrderRepo;
import com.rest.cam.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	private CustomerRepo customerRepo;

	public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo) {
		super();
		this.orderRepo = orderRepo;
		this.customerRepo = customerRepo;
	}

	// DTO to ENTITY
	public Order mapDtoToEntity(OrderDto orderDto, Customer customer) {
		Order order = new Order();
		order.setOrderid(orderDto.getOrderid());
		order.setStatus(orderDto.getStatus());
		order.setTotalAmount(orderDto.getTotalAmount());
		// converting foreign key
		order.setCustomer(customer);
		return order;
	}

	// Entity to Dto
	public OrderDto mapEntityToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderid(order.getOrderid());
		orderDto.setStatus(order.getStatus());
		orderDto.setTotalAmount(order.getTotalAmount());
		// converting foreign key
		orderDto.setCustomer(order.getCustomer().getCustomerid());
		return orderDto;
	}

	@Override
	public OrderDto createOrderByCustomerId(OrderDto orderDto, Long customerid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		if (!customerid.equals(orderDto.getCustomer())) {
			throw new OrderApiException(HttpStatus.BAD_REQUEST,
					"Customer id does not macthes the end point id: " + customerid);
		}
		Order order = mapDtoToEntity(orderDto, customer);
		Order savedOrder = orderRepo.save(order);
		return mapEntityToDto(savedOrder);
	}

	@Override
	public List<OrderDto> getAllOrdersById(Long customerid) {
		Customer customer1 = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		List<Order> byCustomerId = orderRepo.findByCustomer(customer1);
		return byCustomerId.stream().map(orders -> mapEntityToDto(orders)).toList();
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, Long customerid, Long orderid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		Order order = orderRepo.findById(orderid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", orderid));

		if (!order.getCustomer().getCustomerid().equals(customerid)) {
			throw new OrderApiException(HttpStatus.BAD_REQUEST,
					"Order id " + orderid + " does not belong to the customer id: " + customerid);
		}

		order.setStatus(orderDto.getStatus());
		order.setTotalAmount(orderDto.getTotalAmount());

		Order updatedOrder = orderRepo.save(order);
		return mapEntityToDto(updatedOrder);
	}

	@Override
	public OrderDto deleteOrder(Long orderid) {
		Order order = orderRepo.findById(orderid)
				.orElseThrow(() -> new NoResourceFoundException("Order", "ID", orderid));
		orderRepo.deleteOrderByOrderId(order.getOrderid());
		return null;
	}

	// calculating total amount spent by a customer
	@Override
	public Double totalAmountSpent(Long customerid) {
		Customer customer = customerRepo.findById(customerid)
				.orElseThrow(() -> new NoResourceFoundException("Customer", "ID", customerid));
		List<Order> orders = orderRepo.findByCustomer(customer);
		double total = 0.0;
		for (Order order : orders) {
			total += order.getTotalAmount();
		}
		return total;
	}
}
