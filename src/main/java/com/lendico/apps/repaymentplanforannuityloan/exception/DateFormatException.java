package com.lendico.apps.repaymentplanforannuityloan.exception;

import org.springframework.http.HttpStatus;

/**@see Exception Case for invalid Date
 * @author lsajjan
 *
 */
public class DateFormatException extends RuntimeException {   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateFormatException(final String errorMessage, final HttpStatus httpStatus) {
       new ApiException(errorMessage, httpStatus);
    }

   
}
