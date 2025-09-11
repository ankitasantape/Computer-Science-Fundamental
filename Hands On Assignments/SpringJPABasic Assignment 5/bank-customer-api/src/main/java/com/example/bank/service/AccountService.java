package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> getAccountsByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
