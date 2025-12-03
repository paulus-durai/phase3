package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.config.ModelMapperConfig;
import com.abc.telecom.billing.entity.UsageRecord;
import com.abc.telecom.billing.service.UsageRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ServiceUsageController.class)
@Import(ModelMapperConfig.class)
public class ServiceUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsageRecordService usageRecordService;

    @Test
    public void getUsageForService_returnsList() throws Exception {
        UsageRecord u1 = new UsageRecord();
        u1.setId(1L);
        u1.setServiceId(1L);
        u1.setUsageTimestamp(LocalDateTime.now());
        u1.setUnits(new BigDecimal("10"));

        UsageRecord u2 = new UsageRecord();
        u2.setId(2L);
        u2.setServiceId(1L);
        u2.setUsageTimestamp(LocalDateTime.now());
        u2.setUnits(new BigDecimal("5"));

        Mockito.when(usageRecordService.getUsageForService(1L)).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/services/1/usage").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
