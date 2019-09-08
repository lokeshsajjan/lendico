package com.lendico.apps.repaymentplanforannuityloan.exception;

/**
 * Thrown as a exception response to the user with valid exception message
 * 
 * @author lsajjan
 *
 */
public class ApiExceptionResponse {

	private String errorMessage;

	ApiExceptionResponse(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
}