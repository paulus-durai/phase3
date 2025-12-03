package com.abc.telecom.billing.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abc.telecom.billing.dto.PostpaidAccountDto;
import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.service.AccountService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostpaidAccountDto>> getAllAccounts() {
        List<PostpaidAccount> accounts = accountService.getAllAccounts();
        List<PostpaidAccountDto> dtos = accounts.stream()
                .map(a -> modelMapper.map(a, PostpaidAccountDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isAccountOwner(#id)")
    public ResponseEntity<PostpaidAccountDto> getAccountById(@PathVariable Long id) {
        PostpaidAccount account = accountService.getAccountById(id);
        return new ResponseEntity<>(modelMapper.map(account, PostpaidAccountDto.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostpaidAccountDto> createAccount(@Valid @RequestBody PostpaidAccountDto dto) {
        PostpaidAccount entity = modelMapper.map(dto, PostpaidAccount.class);
        PostpaidAccount created = accountService.createAccount(entity);
        return new ResponseEntity<>(modelMapper.map(created, PostpaidAccountDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isAccountOwner(#id)")
    public ResponseEntity<PostpaidAccountDto> updateAccount(@PathVariable Long id, @Valid @RequestBody PostpaidAccountDto dto) {
        PostpaidAccount entity = modelMapper.map(dto, PostpaidAccount.class);
        PostpaidAccount updated = accountService.updateAccount(id, entity);
        return new ResponseEntity<>(modelMapper.map(updated, PostpaidAccountDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isAccountOwner(#id)")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}