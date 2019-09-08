package com.lendico.apps.repaymentplanforannuityloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.apps.repaymentplanforannuityloan.model.Request;
import com.lendico.apps.repaymentplanforannuityloan.model.Response;
import com.lendico.apps.repaymentplanforannuityloan.service.PaymentPlanGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rest End point for Re-payment Plans for Annuity Loan
 * 
 * @author lsajjan
 *
 */
@RestController
@RequestMapping(value = "generate-plan")
@Api(value = "Lendico", description = "Plan Generator ", tags = ("Annuity loan"))
public class PaymentPlanGeneratorController {

	@Autowired
	private PaymentPlanGeneratorService paymentPlanGeneratorService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Generate Payment Plan", notes = "In order to inform borrowers about the final repayment schedule, we need to have pre-calculated\n"
			+ "repayment plans throughout the lifetime of a loan.\n"
			+ "To be able to calculate a repayment plan specific input parameters are necessary:\n"
			+ "• duration (number of instalments in months)\n" + "• nominal interest rate\n"
			+ "• total loan amount (\"total principal amount\")\n" + "• Date of Disbursement/Payout")
	public List<Response> generatePaymentPlan(@RequestBody final Request request) {
		return paymentPlanGeneratorService.generatePaymentPlan(request);
	}
}