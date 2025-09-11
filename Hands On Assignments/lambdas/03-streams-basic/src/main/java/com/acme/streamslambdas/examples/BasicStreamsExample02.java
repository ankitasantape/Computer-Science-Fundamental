package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicStreamsExample02 {

    public static void main(String[] args) throws IOException {
        List<Product> products = ExampleData.getProducts();

        // Get a stream from a collection
        Stream<Product> stream1 = products.stream();
        System.out.println("Stream from collection:");
        stream1.forEach(System.out::println);

        // Get a stream from an array
        String[] array = new String[]{"one", "two", "three"};
        Stream<String> stream2 = Arrays.stream(array);
        System.out.println("\nStream from array:");
        stream2.forEach(System.out::println);

        // Create a Stream from elements directly
        Stream<String> stream3 = Stream.of("one", "two", "three");
        System.out.println("\nStream.of elements:");
        stream3.forEach(System.out::println);

        // Create a Stream with zero or one elements with ofNullable()
        Stream<String> stream4 = Stream.ofNullable("four");
        System.out.println("\nStream.ofNullable:");
        stream4.forEach(System.out::println);

        // Create an empty Stream with Stream.empty()
        Stream<?> stream5 = Stream.empty();
        System.out.println("\nStream.empty:");
        stream5.forEach(System.out::println); // Prints nothing

        // Returns an infinite stream of random numbers between 1 (inclusive) and 7 (exclusive)
        IntStream dice = new Random().ints(1, 7);
        System.out.println("\nRandom dice rolls (first 10):");
        dice.limit(10).forEach(System.out::println);

        // There are more methods in the Java standard library that create streams, for example BufferedReader.lines()
        // Example with BufferedReader.lines()
//        System.out.println("\nStream from BufferedReader.lines():");
//        String data = "line one\nline two\nline three";
//        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
//            reader.lines().forEach(System.out::println);
    }
}
