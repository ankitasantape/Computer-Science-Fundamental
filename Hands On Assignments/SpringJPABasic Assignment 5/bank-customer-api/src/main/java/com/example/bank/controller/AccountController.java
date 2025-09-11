package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}/account")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable("id") Integer customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);

        return ResponseEntity.ok(accounts != null ? accounts : List.of());
    }
}
