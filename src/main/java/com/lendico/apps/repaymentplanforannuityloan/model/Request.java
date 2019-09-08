package com.lendico.apps.repaymentplanforannuityloan.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;

import com.lendico.apps.repaymentplanforannuityloan.constants.ErrorMessages;
import com.lendico.apps.repaymentplanforannuityloan.exception.ApiException;

/**
 * Holds the request parameters and validates the input parameter values
 * 
 * @author lsajjan
 *
 */
public class Request {
	private double loanAmount;
	private double nominalRate;
	private int duration;
	private String startDate;

	public Request() {
	}

	public Request(final double loanAmount, final double nominalRate, final int duration, final String startDate) {
		this.loanAmount = loanAmount;
		this.nominalRate = nominalRate;
		this.duration = duration;
		this.startDate = startDate;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public double getNominalRate() {
		return nominalRate;
	}

	public int getDuration() {
		return duration;
	}

	public String getStartDate() {
		return startDate;
	}

	/**
	 * Validated the request parameters
	 */
	public void validate() {
		if (this.loanAmount <= 0)
			throw new ApiException(ErrorMessages.LOAN_AMOUNT_REQUIRED_AND_NON_NEGETIVE, HttpStatus.BAD_REQUEST);

		if (this.nominalRate <= 0)
			throw new ApiException(ErrorMessages.NOMINAL_RATE_REQUIRED_AND_NON_NEGETIVE, HttpStatus.BAD_REQUEST);

		if (this.duration <= 0)
			throw new ApiException(ErrorMessages.DURATION_REQUIRED_AND_NON_NEGETIVE, HttpStatus.BAD_REQUEST);

		if (this.startDate == null || this.startDate.isEmpty()) {
			throw new ApiException(ErrorMessages.START_DATE_REQUIRED, HttpStatus.BAD_REQUEST);
		} else {
			/// check for valid date format it should be yyyy-MM-dd'T'HH:mm:ss'Z'
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			try {
				sdf.parse((this.startDate));
			} catch (ParseException e) {
				throw new ApiException(ErrorMessages.INVALID_DATE_FORMAT, HttpStatus.BAD_REQUEST);
			}
		}
	}
}
