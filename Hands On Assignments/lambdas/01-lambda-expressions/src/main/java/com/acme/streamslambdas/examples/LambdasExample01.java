package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.Comparator;
import java.util.List;

public class LambdasExample01 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Interface Comparator implemented with an anonymous class.
        products.sort(
               new Comparator<Product>() {
                   @Override
                   public int compare(Product p1, Product p2){
                       return p1.getName().compareTo(p2.getName());
                   }
               }
        );
        System.out.println("Sorted with Anonymous Class:");
        for(Product product : products){
            System.out.println(product);
        }

        // Interface Comparator implemented with a lambda expression.
        products.sort((p1,p2) ->  p1.getPrice().compareTo(p1.getPrice()));

        System.out.println("Sorted with lambda (by price):");
        for(Product product : products){
            System.out.println(product);
        }

        // The same with a more verbose syntax:
        // - You can optionally specify the type of the parameters
        // - The body can be a block between { and } or a single expression

        products.sort((Product p1, Product p2) -> {
            return p1.getCategory().compareTo(p2.getCategory());
        });

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
