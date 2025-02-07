package com.rest.cam.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private Long customerid;

	@NotEmpty
	@Size(min = 3, message = "Name should have atleast 3 characters")
	private String name;

	@NotEmpty
	@Size(min = 3, message = "Email should have atleast 3 characters")
	private String email;

}
