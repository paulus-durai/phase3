package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.config.ModelMapperConfig;
import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@Import(ModelMapperConfig.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void getAllAccounts_returnsList() throws Exception {
        PostpaidAccount a1 = new PostpaidAccount();
        a1.setId(1L);
        a1.setAccountNumber("ACC-001");
        a1.setCustomerId(1L);

        PostpaidAccount a2 = new PostpaidAccount();
        a2.setId(2L);
        a2.setAccountNumber("ACC-002");
        a2.setCustomerId(2L);

        Mockito.when(accountService.getAllAccounts()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
