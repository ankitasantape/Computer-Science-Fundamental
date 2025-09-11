package com.example.bank.repository;

import com.example.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);

}
