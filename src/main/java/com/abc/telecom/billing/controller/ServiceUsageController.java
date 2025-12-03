package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.dto.UsageRecordDto;
import com.abc.telecom.billing.entity.UsageRecord;
import com.abc.telecom.billing.service.UsageRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceUsageController {

    @Autowired
    private UsageRecordService usageRecordService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}/usage")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isServiceOwner(#id)")
    public ResponseEntity<List<UsageRecordDto>> getUsageForService(@PathVariable Long id) {
        List<UsageRecord> usage = usageRecordService.getUsageForService(id);
        List<UsageRecordDto> dtos = usage.stream().map(u -> modelMapper.map(u, UsageRecordDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/usage")
    public ResponseEntity<UsageRecordDto> addUsageForService(@PathVariable Long id, @Valid @RequestBody UsageRecordDto usageDto) {
        UsageRecord entity = modelMapper.map(usageDto, UsageRecord.class);
        UsageRecord created = usageRecordService.createUsageForService(id, entity);
        return ResponseEntity.status(201).body(modelMapper.map(created, UsageRecordDto.class));
    }
}
