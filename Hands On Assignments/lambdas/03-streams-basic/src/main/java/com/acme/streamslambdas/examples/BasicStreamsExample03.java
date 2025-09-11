
package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.List;
import java.util.regex.Pattern;

public class BasicStreamsExample03 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // The filter() intermediate operation filters elements from the stream
        System.out.println("Products with price > 5:");
        products.stream()
                .filter(p -> p.getPrice().doubleValue() > 5.0)
                .forEach(System.out::println);

        // The map() intermediate operation does a one-to-one transformation on each element
        System.out.println("\nProduct names:");
        products.stream()
                .map(Product::getName)
                .forEach(System.out::println);

        // The flatMap() intermediate operation does a one-to-N transformation on each element
        // The function passed to flatMap() must return a Stream that contains the output elements
        // The streams returned by the calls to the function are "flattened" into a single output stream
        Pattern spaces = Pattern.compile("\\s+");

        System.out.println("\nProduct names split into words (using flatMap):");
        products.stream()
                .map(Product::getName)
                .flatMap(name -> spaces.splitAsStream(name))
                .forEach(System.out::println);

    }
}
