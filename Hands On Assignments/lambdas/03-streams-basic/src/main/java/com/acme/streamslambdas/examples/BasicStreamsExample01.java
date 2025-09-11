package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicStreamsExample01 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Streams are lazy - without a terminal operation, no work is done
        // When you call intermediate operations on a stream, you're only building the pipeline;
        // no elements are flowing through it yet
        Stream<Product> stream = products.stream()
                .filter(product -> {
                    System.out.println("Filtering: " + product.getName());
                    return product.getPrice().compareTo(new BigDecimal("5.00")) < 0;
                });

        // When you call a terminal operation, the stream will do the work
        List<Product> cheapProducts = stream.collect(Collectors.toList());

        cheapProducts.forEach(System.out::println);

    }
}
