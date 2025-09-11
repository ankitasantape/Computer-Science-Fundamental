package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LambdasExample06 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();
//        FileWriter writer = new FileWriter("products.txt");
//        try (FileWriter writer = new FileWriter("products.txt")){
//            products.forEach(product -> {
//                try{
//                    writer.write(product.toString() + System.lineSeparator());
//                } catch (IOException e){
//                    throw new RuntimeException("Error writing product to file", e);
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // According to forEach(), the lambda expression implements interface Consumer. The accept() method
        // of this interface does not declare any checked exceptions, so the lambda expression is not allowed
        // to throw any checked exceptions. We are forced to handle the IOException inside the lambda expression.

        try (FileWriter writer = new FileWriter("products.txt")) {
            products.forEach(product -> writeProduct(writer, product));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeProduct(FileWriter writer, Product product) {
        try {
            writer.write(product.toString() + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Error writing product to file", e);
        }
    }
}