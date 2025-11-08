package com.javademo.spring.batch.repository;

import com.javademo.spring.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<User, Integer> {
}
