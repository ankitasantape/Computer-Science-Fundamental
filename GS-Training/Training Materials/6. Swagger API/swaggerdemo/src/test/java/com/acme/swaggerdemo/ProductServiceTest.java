package com.acme.swaggerdemo;

import com.acme.swaggerdemo.entity.Product;
import com.acme.swaggerdemo.repository.ProductRepository;
import com.acme.swaggerdemo.service.ProductService;
import com.acme.swaggerdemo.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product(); p1.setName("A"); p1.setPrice(10.0);
        Product p2 = new Product(); p2.setName("B"); p2.setPrice(20.0);

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }
}
