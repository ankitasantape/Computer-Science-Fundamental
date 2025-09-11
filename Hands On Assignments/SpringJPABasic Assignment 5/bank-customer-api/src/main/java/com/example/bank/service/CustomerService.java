package com.example.bank.service;

import com.example.bank.exception.ResourceNotFoundException;
import com.example.bank.model.Customer;
import com.example.bank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Integer id){
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        if ( !customerRepository.existsById(customer.getId())) {
            throw new ResourceNotFoundException("Customer not found with id " + customer.getId());
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)){
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }

    public List<Customer> searchCustomers(String firstName, String lastName) {
       if(firstName != null && lastName != null){
           return customerRepository.findByFirstNameAndLastName(firstName, lastName);
       }
       else if(firstName != null){
           return customerRepository.findByFirstName(firstName);
       }
       else if(lastName != null){
           return customerRepository.findByLastName(lastName);
       }
       return customerRepository.findAll();
    }
}
