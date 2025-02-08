package com.rest.cam.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.cam.dto.OrderDto;
import com.rest.cam.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@PostMapping("/create/{customerid}")
	public ResponseEntity<OrderDto> createOrders(@RequestBody OrderDto Orderdto, @PathVariable Long customerid) {
		OrderDto orderByCustomerId = orderService.createOrderByCustomerId(Orderdto, customerid);
		return new ResponseEntity<>(orderByCustomerId, HttpStatus.CREATED);
	}

	@GetMapping("/{customerid}")
	public ResponseEntity<OrderDto> getAllOrdersByCustomerId(@PathVariable Long customerid) {
		List<OrderDto> allOrdersById = orderService.getAllOrdersById(customerid);
		return new ResponseEntity(allOrdersById, HttpStatus.OK);
	}

	@PutMapping("/{customerid}/update/{orderid}")
	public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto, @PathVariable Long customerid,
			@PathVariable Long orderid) {
		OrderDto updatedOrder = orderService.updateOrder(orderDto, customerid, orderid);
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{orderid}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long orderid) {
		orderService.deleteOrder(orderid);
		return new ResponseEntity<>("Order has been deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/totalamount/{customerid}")
	public ResponseEntity<String> totalAmount(@PathVariable Long customerid) {
		Double totalAmountSpent = orderService.totalAmountSpent(customerid);
		return new ResponseEntity(
				totalAmountSpent + " is the total amount spent by the cusomter with id: " + customerid, HttpStatus.OK);
	}

}
