package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AdvancedStreamsExercise01 {

    /**
     * Exercise 1: Collect product names into a sorted set.
     *
     * @param products A list of products.
     * @return A TreeSet containing the names of the products.
     */
    public Set<String> getSortedProductNames(List<Product> products) {
        // TODO: Collect the names of the products into a TreeSet.
        //
        // Hint: Use the collect() method that takes three functions parameters.
        // What is the purpose of each of these three functions and how do you implement them? (Consult the API documentation).
        // Use method references or lambda expressions to implement the three functions.

        return products.stream()
                .map(Product::getName)  // get product names
                .collect(
                        TreeSet::new,       // Supplier: creates a new TreeSet
                        Set::add,           // Accumulator: adds one element into the set
                        Set::addAll         // Combiner: merges two sets (parallel streams)
                );
    }

    public static void main(String[] args) {
        AdvancedStreamsExercise01 exercise = new AdvancedStreamsExercise01();
        List<Product> products = ExampleData.getProducts();

        Set<String> sortedNames = exercise.getSortedProductNames(products);

        System.out.println("Sorted Product Names:");
        sortedNames.forEach(System.out::println);
    }
}
