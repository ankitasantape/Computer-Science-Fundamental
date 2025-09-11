package com.acme.swaggerdemo.repository;

import com.acme.swaggerdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
