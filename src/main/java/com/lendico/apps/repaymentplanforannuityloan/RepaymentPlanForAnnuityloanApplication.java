package com.lendico.apps.repaymentplanforannuityloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * In order to inform borrowers about the final repayment schedule, we need to
 * have pre-calculated repayment plans throughout the lifetime of a loan. To be
 * able to calculate a repayment plan specific input parameters are necessary: •
 * duration (number of instalments in months) • nominal interest rate • total
 * loan amount ("total principal amount") • Date of Disbursement/Payout
 * 
 * @author lsajjan
 *
 */
@SpringBootApplication
public class RepaymentPlanForAnnuityloanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepaymentPlanForAnnuityloanApplication.class, args);
	}

}
