package org.shop.facade.mapper.impl;

import org.shop.entity.User;
import org.shop.facade.dto.UserDto;
import org.shop.facade.mapper.core.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserMapperImpl implements UserMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    @Override
    public UserDto map(User user) {
        Assert.notNull(
                user,
                "Class - UserMapperImpl, method - map " +
                        "user should not be null"
        );
        LOGGER.debug("Mapping user - {}", user);
        final UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
        LOGGER.debug("Successfully mapped user - {}, result - {}", user, userDto);
        return userDto;
    }
}
