package com.rest.cam.payload;

import java.util.Date;

import lombok.Data;

@Data
public class MyCustomException {

	private Date date;
	private String message;
	private String description;
}
