package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<PostpaidAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    public PostpaidAccount getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
    }

    public PostpaidAccount createAccount(PostpaidAccount account) {
        return accountRepository.save(account);
    }

    public PostpaidAccount updateAccount(Long id, PostpaidAccount accountDetails) {
        PostpaidAccount account = accountRepository.findById(id).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setBalance(accountDetails.getBalance());
        account.setStatus(accountDetails.getStatus());
        account.setCustomerName(accountDetails.getCustomerName());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
