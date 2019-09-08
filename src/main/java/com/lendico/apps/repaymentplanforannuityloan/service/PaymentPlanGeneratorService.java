package com.lendico.apps.repaymentplanforannuityloan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lendico.apps.repaymentplanforannuityloan.domain.PaymentPerMonth;
import com.lendico.apps.repaymentplanforannuityloan.model.Request;
import com.lendico.apps.repaymentplanforannuityloan.model.Response;

/**
 * Heart of application, represents and encapsulates the Pyament plan logical
 * components.
 * 
 * @author lsajjan
 *
 */
@Service
public class PaymentPlanGeneratorService {

	/**
	 * Generate the first month installment details
	 * 
	 * @param request
	 * @return
	 */
	public List<Response> generatePaymentPlan(final Request request) {
		request.validate();
		final List<PaymentPerMonth> paymentPerMonthList = generatePaymentPlanList(request);
		return generateResponseList(paymentPerMonthList);
	}

	/**
	 * Create the all installment summary object and send as a response to user
	 * 
	 * @param request
	 * @return
	 */
	private List<PaymentPerMonth> generatePaymentPlanList(final Request request) {

		final List<PaymentPerMonth> paymentPerMonthList = new ArrayList<>();
		paymentPerMonthList.add(firstMonth(request));
		paymentPerMonthList.addAll(remainingMonths(request, paymentPerMonthList.get(0)));
		return paymentPerMonthList;
	}

	/**Used to calculate the remaining payment details
	 * @param request
	 * @param previousPaymentPerMonth
	 * @return
	 */
	private List<PaymentPerMonth> remainingMonths(final Request request, PaymentPerMonth previousPaymentPerMonth) {

		final List<PaymentPerMonth> paymentPerMonthList = new ArrayList<>();

		for (int counter = 1; counter < request.getDuration(); counter++) {
			previousPaymentPerMonth = new PaymentPerMonth(request, previousPaymentPerMonth, counter);
			paymentPerMonthList.add(previousPaymentPerMonth);
		}

		return paymentPerMonthList;
	}

	/**
	 * @param request
	 * @return
	 */
	private PaymentPerMonth firstMonth(final Request request) {
		return new PaymentPerMonth(request);
	}

	/**Generate the reponse list 
	 * @param paymentPerMonthList
	 * @return
	 */
	private List<Response> generateResponseList(final List<PaymentPerMonth> paymentPerMonthList) {

		final List<Response> responseList = new ArrayList<>();

		for (final PaymentPerMonth paymentPerMonth : paymentPerMonthList) {
			final Response response = new Response(paymentPerMonth);
			responseList.add(response);
		}

		return responseList;
	}
}
