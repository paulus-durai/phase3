package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.UsageRecord;
import com.abc.telecom.billing.repository.UsageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageRecordService {

    private final UsageRecordRepository usageRecordRepository;

    @Autowired
    public UsageRecordService(UsageRecordRepository usageRecordRepository) {
        this.usageRecordRepository = usageRecordRepository;
    }

    public List<UsageRecord> getUsageForService(Long serviceId) {
        // return all for now
        return usageRecordRepository.findByServiceIdAndUsageDateBetween(serviceId, java.time.LocalDate.of(1970,1,1), java.time.LocalDate.now());
    }

    public UsageRecord createUsageForService(Long serviceId, UsageRecord usageRecord) {
        usageRecord.setServiceId(serviceId);
        return usageRecordRepository.save(usageRecord);
    }
}
