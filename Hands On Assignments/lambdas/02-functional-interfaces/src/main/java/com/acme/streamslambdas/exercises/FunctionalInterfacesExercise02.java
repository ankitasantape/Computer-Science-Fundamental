package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalInterfacesExercise02 {

    public static class SearchCriteria {
        private Category category;
        private String namePattern;
        private BigDecimal minimumPrice;
        private BigDecimal maximumPrice;

        public SearchCriteria(Category category, String namePattern, BigDecimal minimumPrice, BigDecimal maximumPrice) {
            this.category = category;
            this.namePattern = namePattern;
            this.minimumPrice = minimumPrice;
            this.maximumPrice = maximumPrice;
        }
    }

    /**
     * Exercise 2: Write a method that composes a Predicate for a given set of search criteria.
     * <p>
     * Use functional composition to create a Predicate. Check if each of the fields in the search criteria is not null.
     * Combine the different predicates for each of the fields in the appropriate way - include a predicate if the
     * corresponding search criteria field is not null.
     *
     * @param criteria Search criteria.
     * @return A Predicate that returns true for products that match the combination of search criteria.
     */
    public Predicate<Product> createSearchPredicate(SearchCriteria criteria) {
        Predicate<Product> categoryIs = product -> product.getCategory() == criteria.category; /* TODO: Implement a lambda expression that checks if a product's category is equal to criteria.category */;
        Predicate<Product> nameMatches = product -> product.getName().matches(criteria.namePattern);
        Predicate<Product> minimumPriceIs = product -> product.getPrice().compareTo(criteria.minimumPrice) >= 0; /* TODO: Implement a lambda expression that checks if a product's price is greater than criteria.minimumPrice */;
        Predicate<Product> maximumPriceIs = product -> product.getPrice().compareTo(criteria.maximumPrice) <= 0;

        Predicate<Product> predicate = product -> true; /* TODO: Implement a lambda expression that takes a product and always returns true */;

        if (criteria.category != null) {
            // TODO: Update 'predicate' to combine it with the 'categoryIs' predicate.
            predicate = predicate.and(product -> product.getCategory() == criteria.category);
        }

        // TODO: Do the same for the other search criteria and corresponding predictates:
        // - if criteria.namePattern is not null, include the 'nameMatches' predicate
        if (criteria.namePattern != null) {
            predicate = predicate.and(product -> product.getName().matches(criteria.namePattern));
        }
        // - if criteria.minimumPrice is not null, include the 'minimumPriceIs' predicate
        if (criteria.minimumPrice != null) {
            predicate = predicate.and(product -> product.getPrice().compareTo(criteria.minimumPrice) >= 0);
        }
        // - if criteria.maximumPrice is not null, include the 'maximumPriceIs' predicate
        if (criteria.maximumPrice != null) {
            predicate = predicate.and(product -> product.getPrice().compareTo(criteria.maximumPrice) <= 0);
        }

        return predicate;
    }

    public static void main(String[] args) {
        FunctionalInterfacesExercise02 exercise = new FunctionalInterfacesExercise02();
        List<Product> products = ExampleData.getProducts();

        // Example 1: Search for FOOD products under price 10
        SearchCriteria criteria1 = new SearchCriteria(Category.FOOD, null, null, BigDecimal.valueOf(10));
        Predicate<Product> predicate1 = exercise.createSearchPredicate(criteria1);

        List<Product> result1 = products.stream().filter(predicate1).collect(Collectors.toList());
        System.out.println("Food products under 10: " + result1);

        // Example 2: Search for products with name starting with "C"
        SearchCriteria criteria2 = new SearchCriteria(null, "^C.*", null, null);
        Predicate<Product> predicate2 = exercise.createSearchPredicate(criteria2);

        List<Product> result2 = products.stream().filter(predicate2).collect(Collectors.toList());
        System.out.println("Products starting with C: " + result2);

        // Example 3: Search for UTENSILS products priced between 5 and 50
        SearchCriteria criteria3 = new SearchCriteria(Category.UTENSILS, null, BigDecimal.valueOf(5), BigDecimal.valueOf(50));
        Predicate<Product> predicate3 = exercise.createSearchPredicate(criteria3);

        List<Product> result3 = products.stream().filter(predicate3).collect(Collectors.toList());
        System.out.println("Utensils products priced 5-50: " + result3);

        // Example 4: Search for OFFICE products with name containing "Pen"
        SearchCriteria criteria4 = new SearchCriteria(Category.OFFICE, ".*Pen.*", null, null);
        Predicate<Product> predicate4 = exercise.createSearchPredicate(criteria4);

        List<Product> result4 = products.stream().filter(predicate4).collect(Collectors.toList());
        System.out.println("Office products with 'Pen' in name: " + result4);
    }
}
