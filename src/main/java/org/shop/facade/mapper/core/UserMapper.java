package org.shop.facade.mapper.core;

import org.shop.entity.User;
import org.shop.facade.dto.UserDto;

public interface UserMapper {

    UserDto map(User user);
}
