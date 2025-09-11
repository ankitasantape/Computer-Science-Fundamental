package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicStreamsExample05 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // This is not a good way to get the elements of a stream into a list
        List<Product> badWay = new ArrayList<>();
        products.stream().forEach(p -> badWay.add(p));
        System.out.println("Collected products (bad way): " + badWay);

        // Using collect() you can collect the elements of a stream into a collection
        List<Product> productList = products.stream()
                .collect(Collectors.toList());
        System.out.println("Collected products (toList): " + productList);

        // Class Collectors contains factory methods for collectors that create different kinds of collections
        // Collect into a Set
        Set<Category> categories = products.stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet());
        System.out.println("Unique categories: " + categories);

        // Collect into a Map (key = name, value = price)
        Map<String, BigDecimal> productPriceMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Product::getPrice));
        System.out.println("Product to Price map: " + productPriceMap);

        // Collectors.joining(...) returns a collector to reduce stream elements into a string
        // Collectors.joining(...) → concatenate names into a single string
        String joinedNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
        System.out.println("Joined product names: " + joinedNames);


    }
}
