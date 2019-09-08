package com.lendico.apps.repaymentplanforannuityloan;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = RepaymentPlanForAnnuityloanApplication.class)
@ActiveProfiles("test")
public class PaymentPlanGeneratorIntegrationTest extends BaseIntegrationTest {

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    private static final String ENDPOINT = "/generate-plan";

    @Test
    public void testPaymentPlan_isOk() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(validRequest())))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].borrowerPaymentAmount").value(219.36))
                .andExpect(jsonPath("$[0].date").value("2018-01-01T00:00:00Z"))
                .andExpect(jsonPath("$[0].initialOutstandingPrincipal").value(5000))
                .andExpect(jsonPath("$[0].interest").value(20.83))
                .andExpect(jsonPath("$[0].principal").value(198.53))
                .andExpect(jsonPath("$[0].remainingOutstandingPrincipal").value(4801.47))

                .andExpect(jsonPath("$[1].borrowerPaymentAmount").value(219.36))
                .andExpect(jsonPath("$[1].date").value("2018-02-01T00:00:00Z"))
                .andExpect(jsonPath("$[1].initialOutstandingPrincipal").value(4801.47))
                .andExpect(jsonPath("$[1].interest").value(20.01))
                .andExpect(jsonPath("$[1].principal").value(199.35))
                .andExpect(jsonPath("$[1].remainingOutstandingPrincipal").value(4602.12))

                .andExpect(jsonPath("$[2].borrowerPaymentAmount").value(219.36))
                .andExpect(jsonPath("$[2].date").value("2018-03-01T00:00:00Z"))
                .andExpect(jsonPath("$[2].initialOutstandingPrincipal").value(4602.12))
                .andExpect(jsonPath("$[2].interest").value(19.18))
                .andExpect(jsonPath("$[2].principal").value(200.18))
                .andExpect(jsonPath("$[2].remainingOutstandingPrincipal").value(4401.94))

                .andExpect(jsonPath("$[23].borrowerPaymentAmount").value(219.28))
                .andExpect(jsonPath("$[23].date").value("2019-12-01T00:00:00Z"))
                .andExpect(jsonPath("$[23].initialOutstandingPrincipal").value(218.37))
                .andExpect(jsonPath("$[23].interest").value(0.91))
                .andExpect(jsonPath("$[23].principal").value(218.37))
                .andExpect(jsonPath("$[23].remainingOutstandingPrincipal").value(0))

                .andExpect(jsonPath("$[24].borrowerPaymentAmount").doesNotExist())
                .andExpect(jsonPath("$[24].date").doesNotExist())
                .andExpect(jsonPath("$[24].initialOutstandingPrincipal").doesNotExist())
                .andExpect(jsonPath("$[24].interest").doesNotExist())
                .andExpect(jsonPath("$[24].principal").doesNotExist())
                .andExpect(jsonPath("$[24].remainingOutstandingPrincipal").doesNotExist())

                .andReturn();
    }

    @Test
    public void testPaymentPlan_zeroLoanAmount_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_zeroLoanAmount())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testPaymentPlan_zeroNominalRate_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_zeroNominalRate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testPaymentPlan_zeroDuration_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_zeroDuration())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testPaymentPlan_nullStartDate_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_nullStartDate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testPaymentPlan_emptyStartDate_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_emptyStartDate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }
    @Test
    public void testPaymentPlan_invalidStartDate_isBadRequest() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .content(toJson(invalidRequest_invalidStartDate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").isNotEmpty())
                .andReturn();
    }
}
