package com.lendico.apps.repaymentplanforannuityloan;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lendico.apps.repaymentplanforannuityloan.model.Request;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public abstract class BaseIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	MockMvc mockMvc;

	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	String toJson(final Object o) {
		return gson.toJson(o);
	}

	Request validRequest() {
		return new Request(5000, 5, 24, "2018-01-01T00:00:01Z");
	}

	Request invalidRequest_zeroLoanAmount() {
		return new Request(0, 5, 24, "2018-01-01T00:00:01Z");
	}

	Request invalidRequest_zeroNominalRate() {
		return new Request(1, 0, 24, "2018-01-01T00:00:01Z");
	}

	Request invalidRequest_zeroDuration() {
		return new Request(1, 1, 0, "2018-01-01T00:00:01Z");
	}

	Request invalidRequest_nullStartDate() {
		return new Request(1, 1, 1, null);
	}

	Request invalidRequest_emptyStartDate() {
		return new Request(1, 1, 1, "");
	}
	Request invalidRequest_invalidStartDate() {
		return new Request(1, 1, 1, "invalid");
	}

}
