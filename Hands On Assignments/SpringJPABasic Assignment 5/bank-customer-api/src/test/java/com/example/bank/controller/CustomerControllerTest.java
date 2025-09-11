package com.example.bank.controller;

import com.example.bank.model.Customer;
import com.example.bank.service.AccountService;
import com.example.bank.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private AccountService accountService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createCustomer_validInput_returnsCreated() throws Exception{
        Customer customer = new Customer(1, "John", "Doe", "john@example.com", null);

        Mockito.when(customerService.saveCustomer(any(Customer.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())                // expect 201
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllCustomers_returnList() throws Exception{
        Mockito.when(customerService.getAllCustomers())
                .thenReturn(List.of(new Customer()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCustomerById_validId_returnsCustomer() throws Exception{
        Mockito.when(customerService.getCustomerById(1))
                .thenReturn(Optional.of(
                       new Customer(1, "Jane", "Doe", "jane@example.com",null)));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void getCustomerById_notFound_returns404() throws Exception{
        Mockito.when(customerService.getCustomerById(999))
                .thenReturn(Optional.empty());

              mockMvc.perform(get("/customers/999"))
                      .andExpect(status().isNotFound());
    }

    @Test
    void updateCustomer_validInput_returnsUpdated() throws Exception {
        Customer customer = new Customer(1, "Updated","User","updated@example.com",null);
        Mockito.when(customerService.updateCustomer(any(Customer.class)))
                .thenReturn(customer);
        mockMvc.perform(put("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void deleteCustomer_validId_returns200() throws Exception{
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void searchCustomer_byFirstName_returnMatch() throws  Exception{
        Mockito.when(customerService.searchCustomers(eq("Alice"), any()))
                .thenReturn(List.of(new Customer(1, "Alice", "Smith",
                        "alice@example.com",null)));
    }

    @Test
    void createCustomer_blankFirstName_returns400() throws Exception{
        Customer customer = new Customer(null, "", "Doe",
                "email@example.com", null);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name is mandatory"));
    }

    @Test
    void handleUnhandledException_returns500() throws Exception{
        Mockito.when(customerService.getAllCustomers())
                .thenThrow(new RuntimeException("Unexpected"));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
}
