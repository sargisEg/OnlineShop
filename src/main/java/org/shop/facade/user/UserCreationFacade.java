package org.shop.facade.user;

import org.shop.facade.dto.CreateUserRequestDto;
import org.shop.facade.dto.UserDto;

public interface UserCreationFacade {

    UserDto createUser(CreateUserRequestDto dto);
}
