package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;

public class LambdasExample07 {


    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Instead of a lambda expression, you can use a method reference to refer to a method
        // that implements the relevant functional interface.
        products.forEach(System.out::println);

        // A method reference can refer to a static method, an instance method or a constructor.
        products.stream()
                .map(Product::getName)
                .forEach(System.out::println);

        // Method reference to a static method.



        // There are two types of method references that refer to an instance method:
        // - Product::getName refers to an instance method of class Product, but not for any particular Product object
        //   map() calls the getName() method on the Product object it receives
        // - System.out::println refers to an instance method of class PrintStream, for a particular PrintStream object;
        //   the one that the variable System.out refers to. forEach() calls the println() method on that PrintStream


        // A method reference with 'new' after the double colon refers to a constructor.
        products.stream()
                .map(ProductNameWrapper::new) // constructor reference
                .forEach(System.out::println);
    }

    // Simple wrapper class for constructor reference example
    static class ProductNameWrapper {
        private final String name;
        ProductNameWrapper(Product product) {
            this.name = product.getName();
        }
        @Override
        public String toString() {
            return "Wrapped: " + name;
        }
    }
}
