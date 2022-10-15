package org.shop.controller;

import org.shop.facade.dto.CreateUserRequestDto;
import org.shop.facade.dto.UserDto;
import org.shop.facade.user.UserCreationFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/online/shop/user")
public class UserCreationController {

    private final UserCreationFacade userCreationFacade;

    public UserCreationController(UserCreationFacade userCreationFacade) {
        this.userCreationFacade = userCreationFacade;
    }

    @PostMapping
    public UserDto createUser(@RequestBody CreateUserRequestDto dto) {
        return userCreationFacade.createUser(dto);
    }
}
