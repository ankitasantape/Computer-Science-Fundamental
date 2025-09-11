package com.acme.swaggerdemo.service;

import com.acme.swaggerdemo.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
