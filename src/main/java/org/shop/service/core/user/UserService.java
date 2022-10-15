package org.shop.service.core.user;

import org.shop.entity.User;

import java.util.Optional;

public interface UserService {

    User create(CreateUserParams params);

    Optional<User> findById(Long id);

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}
