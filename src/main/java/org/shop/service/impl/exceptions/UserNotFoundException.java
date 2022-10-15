package org.shop.service.impl.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Not found user with id - " + userId);
    }
}
