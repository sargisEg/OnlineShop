package org.shop.service.impl;

import org.shop.entity.User;
import org.shop.repository.UserRepository;
import org.shop.service.core.user.CreateUserParams;
import org.shop.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(CreateUserParams params) {
        Assert.notNull(
                params,
                "Class - UserServiceImpl, method - create " +
                        "params should not be null"
        );
        LOGGER.info("Creating user with params - {}", params);

        final User userFromParams = new User(
                params.getFirstName(),
                params.getLastName(),
                params.getUserRoleType()
        );

        final User user = userRepository.save(userFromParams);

        LOGGER.info("Successfully created user with params - {}, result - {}", params, user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        Assert.notNull(
                id,
                "Class - UserServiceImpl, method - findById " +
                        "id should not be null"
        );
        LOGGER.info("Finding user with id - {}", id);

        final Optional<User> optionalUser = userRepository.findById(id);

        LOGGER.info("Successfully found user with id - {}, result - {}", id, optionalUser);
        return optionalUser;
    }

    @Override
    public Optional<User> findByFirstNameAndLastName(String firstName, String lastName) {
        Assert.hasText(
                firstName,
                "Class - UserServiceImpl, method - findByFirstNameAndSecondName " +
                        "first name should not be null"
        );
        Assert.hasText(
                lastName,
                "Class - UserServiceImpl, method - findByFirstNameAndSecondName " +
                        "last name should not be null"
        );
        LOGGER.info("Finding user with first name - {} and last name - {}", firstName, lastName);

        final Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(firstName, lastName);

        LOGGER.info("Successfully found user with first name - {} and last name - {}, result - {}",
                firstName,
                lastName,
                optionalUser);
        return optionalUser;
    }
}
