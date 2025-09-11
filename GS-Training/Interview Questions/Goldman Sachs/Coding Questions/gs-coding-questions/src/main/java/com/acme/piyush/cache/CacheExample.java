package com.acme.piyush.cache;

public class CacheExample {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>();

        for (int i = 1; i <= 10_100; i++) {
            cache.put("key" + i, "value" + i);
        }

        System.out.println("Cache size (should not exceed 10,000): " + cache.size());
        System.out.println("Contains key1? " + cache.containsKey("key1")); // likely false
        System.out.println("Contains key10000? " + cache.containsKey("key10000")); // true
    }
}
