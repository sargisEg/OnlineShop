package org.shop.service.impl.exceptions;

public class ProductCategoryNotFoundException extends RuntimeException {
    public ProductCategoryNotFoundException(long categoryId) {
        super("Not found category with id - " + categoryId);
    }
}
