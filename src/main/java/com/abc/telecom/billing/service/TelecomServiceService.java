package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.TelecomService;
import com.abc.telecom.billing.repository.TelecomServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelecomServiceService {

    private final TelecomServiceRepository telecomServiceRepository;

    @Autowired
    public TelecomServiceService(TelecomServiceRepository telecomServiceRepository) {
        this.telecomServiceRepository = telecomServiceRepository;
    }

    public List<TelecomService> getServicesByCustomerId(Long customerId) {
        return telecomServiceRepository.findByCustomerId(customerId);
    }

    public TelecomService createServiceForCustomer(Long customerId, TelecomService service) {
        service.setCustomerId(customerId);
        return telecomServiceRepository.save(service);
    }
}
