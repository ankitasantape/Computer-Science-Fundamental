package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.ArrayList;
import java.util.List;

public class AdvancedStreamsExample03 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // You can reduce stream elements into an ArrayList with reduce(), but this is inefficient because reduce()
        // is designed for the result container to be immutable; so you need to create intermediate lists and
        // copy elements between them.
        List<String> productNames1 = products.stream().reduce(
                new ArrayList<>(),
                (list, product) -> {
                    ArrayList<String> newList = new ArrayList<>(list);
                    newList.add(product.getName());
                    return newList;
                },
                (list1, list2) -> {
                    ArrayList<String> newList = new ArrayList<>(list1);
                    newList.addAll(list2);
                    return newList;
                });

        // Collection is mutable reduction. This is much more efficient if you have a mutable result container
        // such as an ArrayList, avoiding most of the copying that needs to be done when using reduce().
        List<String> productNames2 = products.stream().collect(
                ArrayList::new,
                (list, product) -> list.add(product.getName()),
                ArrayList::addAll);

        // Print results
        System.out.println("Product names using reduce(): " + productNames1);
        System.out.println("Product names using collect(): " + productNames2);
    }
}
