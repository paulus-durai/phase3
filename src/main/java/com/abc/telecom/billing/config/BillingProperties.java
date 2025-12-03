package com.abc.telecom.billing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "billing")
public class BillingProperties {

    /**
     * Map of serviceType -> rate (per unit) used for invoice calculation.
     * Example in `application.yml`:
     * billing:
     *   rates:
     *     POSTPAID_MOBILE: 0.10
     *     POSTPAID_BROADBAND: 0.05
     */
    private Map<String, BigDecimal> rates = new HashMap<>();

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
