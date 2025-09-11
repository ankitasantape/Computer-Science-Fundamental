package com.acme.piyush.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private static final int MAX_ENTRIES = 10_000;

    private final Map<K, V> cache;

    public LRUCache() {
        this.cache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    public synchronized void put(K key, V value) {
        cache.put(key, value);
    }

    public synchronized V get(K key) {
        return cache.get(key);
    }

    public synchronized int size() {
        return cache.size();
    }

    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    public synchronized void clear() {
        cache.clear();
    }
}
