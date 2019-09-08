package com.lendico.apps.repaymentplanforannuityloan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Basic API documentation configuration.
 *
 * Created by Lokesh Sajjan 08/09/2019
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.lendico.apps.repaymentplanforannuityloan.controller")).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Lokesh Sajjan", null, "lokeshsajjan.java@gmail.com");
		return new ApiInfoBuilder().title("Repayment plan for an annuity loan")
				.description("Java RESTful Web Service provides repayment plan for an annuity loan").contact(contact)
				.version("1.0.0").build();

	}
}
