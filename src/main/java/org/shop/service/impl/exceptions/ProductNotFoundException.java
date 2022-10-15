package org.shop.service.impl.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Not found product with id - " + id);
    }
}
