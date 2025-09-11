package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme
.streamslambdas.Product;

import java.util.List;

public class LambdasExercise01 {

    /**
     * Exercise 1: Sort a list of products by name using a lambda expression.
     *
     * @param products The list of products to sort.
     */
    public void sortProductsByName(List<Product> products) {
        // TODO: Use a lambda expression to sort the list of products by name
        products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
    }
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        LambdasExercise01 lambdasExercise01 = new LambdasExercise01();
        lambdasExercise01.sortProductsByName(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }

}
