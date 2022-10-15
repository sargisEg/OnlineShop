package org.shop.service.impl.exceptions;

public class ProductInOrderNotFoundException extends RuntimeException {
    public ProductInOrderNotFoundException(Long id) {
        super("Not found product in order with id - " + id);
    }
}
