package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.config.ModelMapperConfig;
import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.entity.TelecomService;
import com.abc.telecom.billing.service.CustomerService;
import com.abc.telecom.billing.service.TelecomServiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(controllers = CustomerController.class)
@Import(ModelMapperConfig.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private TelecomServiceService telecomServiceService;

    @Test
    public void getAllCustomers_returnsDtoList() throws Exception {
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setName("John Doe");
        c1.setEmail("john@example.com");
        c1.setPhoneNumber("1234567890");
        c1.setAddress("123 Main St");

        Customer c2 = new Customer();
        c2.setId(2L);
        c2.setName("Jane Smith");
        c2.setEmail("jane@example.com");
        c2.setPhoneNumber("0987654321");
        c2.setAddress("456 Elm St");

        List<Customer> customers = Arrays.asList(c1, c2);
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }
}
