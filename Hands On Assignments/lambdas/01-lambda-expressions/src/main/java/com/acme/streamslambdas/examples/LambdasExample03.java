package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;
import com.acme.streamslambdas.exercises.LambdasExercise02;

import java.math.BigDecimal;
import java.util.List;

public class LambdasExample03 {

    @FunctionalInterface
    interface ProductFilter {
        boolean accept(Product product);
    }

    // Print the products that are accepted by the filter.
    static void printProducts(List<Product> products, ProductFilter filter) {
        for (Product product : products){
            if (filter.accept(product)){
                System.out.println(product);
            }
        }
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        // This lambda expression captures the local variable priceLimit.
        // The variable must be effectively final; if it is not, an error will appear in the lambda expression.
        ProductFilter cheapProducts = p -> p.getPrice().compareTo(priceLimit) < 0;

        printProducts(products, cheapProducts);

        // Reassigning the variable, even after the lambda expression, makes it not effectively final.
//         priceLimit = new BigDecimal("6.00");


    }
}
