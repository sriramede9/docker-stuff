package com.example.democrud.service;

import com.example.democrud.exception.ItemNotFoundException;
import com.example.democrud.model.Item;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory "repository + service" combined for simplicity.
 * Data lives in a ConcurrentHashMap and is lost on restart.
 * This is intentional - the goal right now is to learn Spring Boot
 * basics and Docker, not persistence.
 */
@Service
public class ItemService {

    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public ItemService() {
        // seed a couple of sample items so GET /api/items isn't empty on first run
        Item seed1 = new Item(null, "Keyboard", "Mechanical keyboard", 79.99);
        Item seed2 = new Item(null, "Mouse", "Wireless mouse", 29.99);
        create(seed1);
        create(seed2);
    }

    public Collection<Item> findAll() {
        return store.values();
    }

    public Item findById(Long id) {
        Item item = store.get(id);
        if (item == null) {
            throw new ItemNotFoundException(id);
        }
        return item;
    }

    public Item create(Item item) {
        long newId = idSequence.incrementAndGet();
        item.setId(newId);
        store.put(newId, item);
        return item;
    }

    public Item update(Long id, Item updatedItem) {
        Item existing = findById(id); // throws if missing
        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setPrice(updatedItem.getPrice());
        return existing;
    }

    public void delete(Long id) {
        if (!store.containsKey(id)) {
            throw new ItemNotFoundException(id);
        }
        store.remove(id);
    }
}
