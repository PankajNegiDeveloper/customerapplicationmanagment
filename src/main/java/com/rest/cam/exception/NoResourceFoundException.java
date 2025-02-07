package com.rest.cam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoResourceFoundException extends RuntimeException {

	private String resName;
	private String resValue;
	private Long resId;

	public NoResourceFoundException(String resName, String resValue, Long resId) {
		super(String.format(resName+ " not found with the id " + resValue + ": " + resId));
		this.resName = resName;
		this.resValue = resValue;
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResValue() {
		return resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}
}
