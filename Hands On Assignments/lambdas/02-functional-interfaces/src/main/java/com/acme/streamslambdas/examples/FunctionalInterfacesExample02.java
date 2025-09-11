package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionalInterfacesExample02 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        Map<Category, List<Product>> productsByCategory = new HashMap<>();

        // Without functional interfaces and lambda expressions.
        for (Product product : products){
            Category category = product.getCategory();
            if(!productsByCategory.containsKey(category)){
                // Check if the map already has a List for this category; if not, add one.
                productsByCategory.put(category, new ArrayList<>());
            }
            // Add the product to the appropriate list in the map.
            productsByCategory.get(category).add(product);
        }

        // With Map.computeIfAbsent()
        for(Product product: products){
            productsByCategory.computeIfAbsent(product.getCategory(), p -> new ArrayList<>())
                    .add(product);
        }

        // computeIfAbsent() gets the existing List for the category, or calls the given
        // Function<Category, List<Product>> to create the List if there is no entry in the Map for the category.



        // Map.forEach() takes a BiConsumer (a Consumer which takes two parameters); the key and value of each entry.
        productsByCategory.forEach(((category, productList) -> {
            System.out.println(category + " " + productList);
        }));
    }
}