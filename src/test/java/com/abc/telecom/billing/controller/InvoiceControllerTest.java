package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.config.ModelMapperConfig;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.service.BillingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InvoiceController.class)
@Import(ModelMapperConfig.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @Test
    public void getAllInvoices_returnsList() throws Exception {
        Invoice i1 = new Invoice();
        i1.setId(1L);
        i1.setAccountId(1L);
        i1.setAmount(new BigDecimal("10.00"));
        i1.setInvoiceDate(LocalDate.now());

        Invoice i2 = new Invoice();
        i2.setId(2L);
        i2.setAccountId(2L);
        i2.setAmount(new BigDecimal("20.00"));
        i2.setInvoiceDate(LocalDate.now());

        Mockito.when(billingService.getAllInvoices()).thenReturn(Arrays.asList(i1, i2));

        mockMvc.perform(get("/api/invoices").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
