package com.rest.cam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private Long orderid;
	private String status;
	private Double totalAmount;
	private Long customer;
}
