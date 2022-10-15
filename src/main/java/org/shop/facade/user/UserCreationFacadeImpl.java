package org.shop.facade.user;

import org.shop.entity.User;
import org.shop.facade.dto.CreateUserRequestDto;
import org.shop.facade.dto.UserDto;
import org.shop.facade.mapper.core.UserMapper;
import org.shop.service.core.user.CreateUserParams;
import org.shop.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserCreationFacadeImpl implements UserCreationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreationFacadeImpl.class);

    private final UserService userService;
    private final UserMapper userMapper;

    public UserCreationFacadeImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(CreateUserRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - UserCreationFacadeImpl, method - createUser " +
                        "dto should not be null"
        );
        LOGGER.info("Creating user for provided request - {}", dto);

        final User user = userService.create(new CreateUserParams(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getRole()
        ));

        final UserDto userDto = userMapper.map(user);

        LOGGER.info("Successfully created user for provided request - {}, result - {}", dto, userDto);
        return userDto;
    }
}
