package com.example.democrud.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Item with id " + id + " was not found");
    }
}
