package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LambdasExercise03 {

    public interface ShoppingCartFactory {
        ShoppingCart newShoppingCart();
    }

    /**
     * Exercise 3a: Implement interface ShoppingCartFactory using a method reference.
     *
     * @return A new ShoppingCartFactory.
     */
    public ShoppingCartFactory getShoppingCartFactory() {
        // TODO: Implement interface ShoppingCartFactory by using a method reference
        // Note: Don't implement ShoppingCartFactory with an anonymous class
        return ShoppingCart::new; // Replace 'null' by your solution
    }

    public static class ShoppingCart {
        private final List<Product> products = new ArrayList<>();

        public void add(Product product) {
            this.products.add(product);
        }

        /**
         * Exercise 3b: Calculate the total amount of the items in the shopping cart.
         *
         * @return The total amount of the items in the shopping cart.
         */
        public BigDecimal getTotalAmount() {
            BigDecimal total = BigDecimal.ZERO;

            // TODO: This solution does not work. Can you explain why?
//            products.forEach(product -> total.add(product.getPrice()));
            //BigDecimal is immutable.
            //total.add(product.getPrice()) returns a new BigDecimal, but never assign it back.
            //So total always stays 0.

            // TODO: Implement this method in whatever way you like (you don't have to use lambda expressions)
            for(Product product: products){
                total = total.add(product.getPrice());
            }
            return total;
        }
    }
    public static void main(String[] args) {
        LambdasExercise03 example = new LambdasExercise03();

        // Get a ShoppingCartFactory (method reference)
        ShoppingCartFactory factory = example.getShoppingCartFactory();

        // Create a new ShoppingCart
        ShoppingCart cart = factory.newShoppingCart();

        // Add some products (from ExampleData)
        List<Product> products = ExampleData.getProducts();

        cart.add(products.get(0));
        cart.add(products.get(1));
        cart.add(products.get(2));

        // Print total amount
        System.out.println("Shopping cart total = " + cart.getTotalAmount());
    }
}
