package com.example.democrud.controller;

import com.example.democrud.model.Item;
import com.example.democrud.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item created = itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ (all)
    @GetMapping
    public Collection<Item> getAllItems() {
        return itemService.findAll();
    }

    // READ (one)
    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemService.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @Valid @RequestBody Item item) {
        return itemService.update(id, item);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
