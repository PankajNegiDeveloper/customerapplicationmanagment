package com.rest.cam.service;

import java.util.List;

import com.rest.cam.dto.OrderDto;

public interface OrderService {

	OrderDto createOrderByCustomerId(OrderDto orderDto, Long customerid);

	List<OrderDto> getAllOrdersById(Long customerid);

	OrderDto updateOrder(OrderDto orderDto, Long reviewid, Long orderid);

	OrderDto deleteOrder(Long orderid);

	Double totalAmountSpent(Long customerid);
}
