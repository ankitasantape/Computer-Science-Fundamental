package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;

public class LambdasExample02 {

    // Print the products that cost less than the price limit.
    static void printProducts(List<Product> products, BigDecimal priceLimit) {
        for (Product product : products){
            if(product.getPrice().compareTo(priceLimit) < 0){
                System.out.println(product);
            }
        }
        System.out.println("Using stream: ");
        products.stream()
                .filter(p -> p.getPrice().compareTo(priceLimit) < 0)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        printProducts(products, priceLimit);
    }
}
