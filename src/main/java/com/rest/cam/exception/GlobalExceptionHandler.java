package com.rest.cam.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rest.cam.payload.MyCustomException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(exception = NoResourceFoundException.class)
	public ResponseEntity<MyCustomException> globalExceptionHandler(NoResourceFoundException nre, WebRequest wr) {
		MyCustomException custom = new MyCustomException();
		custom.setDate(new Date());
		custom.setMessage(nre.getMessage());
		custom.setDescription(wr.getDescription(false));
		return new ResponseEntity<>(custom, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(exception = OrderApiException.class)
	public ResponseEntity<MyCustomException> orderApiException(OrderApiException oa, WebRequest wr) {
		MyCustomException custom = new MyCustomException();
		custom.setDate(new Date());
		custom.setMessage(oa.getMessage());
		custom.setDescription(wr.getDescription(false));
		return new ResponseEntity<>(custom, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			// Storing Errors in the Map
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
