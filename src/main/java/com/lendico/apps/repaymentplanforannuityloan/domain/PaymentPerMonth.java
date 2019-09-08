package com.lendico.apps.repaymentplanforannuityloan.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.lendico.apps.repaymentplanforannuityloan.constants.Constants;
import com.lendico.apps.repaymentplanforannuityloan.model.Request;

/**
 * Payment Generator hold the installment summary details
 * 
 * @author lsajjan
 *
 */
public class PaymentPerMonth {
	private BigDecimal borrowerPaymentAmount;
	private String date;
	private BigDecimal initialOutstandingPrincipal;
	private BigDecimal interest;
	private BigDecimal principal;
	private BigDecimal remainingOutstandingPrincipal;

	public PaymentPerMonth(final Request request) {
		this.borrowerPaymentAmount = roundOff(calculateBorrowerPaymentAmount(request));
		this.date = generatePaymentDate(request.getStartDate());
		this.initialOutstandingPrincipal = roundOff(request.getLoanAmount());
		this.interest = roundOff(calculateInterest(BigDecimal.valueOf(request.getNominalRate())));
		this.principal = roundOff(calculatePrincipal(this.borrowerPaymentAmount));
		this.remainingOutstandingPrincipal = roundOff(this.initialOutstandingPrincipal.subtract(this.principal));
	}

	public PaymentPerMonth(final Request request, final PaymentPerMonth previousPaymentPerMonth, final int counter) {
		this.initialOutstandingPrincipal = roundOff(previousPaymentPerMonth.getRemainingOutstandingPrincipal());
		this.date = generatePaymentDate(request.getStartDate(), counter);
		this.interest = roundOff(calculateInterest(BigDecimal.valueOf(request.getNominalRate())));
		this.principal = roundOff(calculatePrincipal(previousPaymentPerMonth.getBorrowerPaymentAmount()));
		this.borrowerPaymentAmount = roundOff(this.principal.add(this.interest));
		this.remainingOutstandingPrincipal = roundOff(this.initialOutstandingPrincipal.subtract(this.principal));
	}

	/**
	 * used for rounding off the doubleValue to the upper limit
	 * 
	 * @param doubleValue
	 * @return
	 */
	private BigDecimal roundOff(final double doubleValue) {
		final BigDecimal bigDecimalValue = BigDecimal.valueOf(doubleValue);
		return bigDecimalValue.setScale(Constants.DECIMALS, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Used for rounding off Bigdecimal value
	 * 
	 * @param bigDecimalValue
	 * @return
	 */
	private BigDecimal roundOff(final BigDecimal bigDecimalValue) {
		return bigDecimalValue.setScale(Constants.DECIMALS, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * calculates Borrower Payment Amount Borrower Payment Amount (Annuity) =
	 * Principal + Interest
	 * 
	 * @param request
	 * @return
	 */
	private double calculateBorrowerPaymentAmount(final Request request) {
		return borrowerPaymentAmountNumerator(request) / borrowerPaymentAmountDenominator(request);
	}

	/**
	 * Numerator value is calculated
	 * 
	 * @param request
	 * @see : https://financeformulas.net/Annuity_Payment_Formula.html
	 * @return
	 */
	private double borrowerPaymentAmountNumerator(final Request request) {
		return request.getLoanAmount() * interestRateOverPaymentsPerYear(request.getNominalRate());
	}

	/**
	 * Calculate denominator value in the formula
	 * 
	 * @param request
	 * @see :https://financeformulas.net/Annuity_Payment_Formula.html
	 * @return
	 */
	private double borrowerPaymentAmountDenominator(final Request request) {
		final double base = 1 + interestRateOverPaymentsPerYear(request.getNominalRate());
		final double subtrahend = Math.pow(base, -request.getDuration());
		return (1 - subtrahend);
	}

	/**
	 * Interest over the payment per year (interest rate/100)/12
	 * 
	 * @param nominalRate
	 * @return
	 */
	private double interestRateOverPaymentsPerYear(final double nominalRate) {
		return ((nominalRate / 100) / Constants.PAYMENTS_PER_YEAR);
	}

	/**
	 * generate Payment Date
	 * 
	 * @param startDateTime
	 * @return
	 */
	private String generatePaymentDate(final String startDateTime) {
		final LocalDate startDate = convertStringToLocalDate(startDateTime);
		return convertLocalDateToString(startDate);
	}

	/**
	 * generate Payment Date for future dates
	 * 
	 * @param startDateTime
	 * @param counter
	 * @return
	 */
	private String generatePaymentDate(final String startDateTime, final int counter) {
		final LocalDate startDate = convertStringToLocalDate(startDateTime);
		final LocalDate paymentDate = startDate.plusMonths(counter);
		return convertLocalDateToString(paymentDate);
	}

	/**
	 * Date format conversion using time zone
	 * 
	 * @param dateTimeAsString
	 * @return
	 */
	private LocalDate convertStringToLocalDate(final String dateTimeAsString) {
		final Instant instant = Instant.parse(dateTimeAsString);
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
		return localDateTime.toLocalDate();
	}

	/**
	 * date and time conversion using time zone
	 * 
	 * @param localDate
	 * @return
	 */
	private String convertLocalDateToString(final LocalDate localDate) {
		final ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(localDate, LocalTime.MIDNIGHT),
				ZoneId.of("UTC"));
		return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
	}

	/**
	 * Interest calculation; Interest = (Nominal-Rate * Days in Month * Initial
	 * Outstanding Principal) / days in year e.g. first installment Interest = (5.00
	 * * 30 * 5000 / 360) = 2083.33333333 cents
	 * 
	 * @param nominalRate
	 * @return
	 */
	private BigDecimal calculateInterest(final BigDecimal nominalRate) {
		final BigDecimal interestInCents = (nominalRate.multiply(Constants.DAYS_PER_MONTH)
				.multiply(this.initialOutstandingPrincipal)).divide(Constants.DAYS_PER_YEAR, BigDecimal.ROUND_HALF_UP);
		return interestInCents.movePointLeft(Constants.DECIMALS);
	}

	/**
	 * Priciple amount Principal = Annuity - Interest (if, calculated interest
	 * amount exceeds the initial outstanding principal amount, take initial
	 * outstanding principal amount instead)
	 * 
	 * @param annuity
	 * @return
	 */
	private BigDecimal calculatePrincipal(final BigDecimal annuity) {
		final BigDecimal principal = annuity.subtract(this.interest);

		if (principal.compareTo(this.initialOutstandingPrincipal) > 0) {
			return this.initialOutstandingPrincipal;
		} else {
			return principal;
		}
	}

	public BigDecimal getBorrowerPaymentAmount() {
		return this.borrowerPaymentAmount;
	}

	public String getDate() {
		return this.date;
	}

	public BigDecimal getInitialOutstandingPrincipal() {
		return this.initialOutstandingPrincipal;
	}

	public BigDecimal getInterest() {
		return this.interest;
	}

	public BigDecimal getPrincipal() {
		return this.principal;
	}

	public BigDecimal getRemainingOutstandingPrincipal() {
		return this.remainingOutstandingPrincipal;
	}
}
