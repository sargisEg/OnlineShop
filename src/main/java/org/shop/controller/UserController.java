package org.shop.controller;

import org.shop.facade.dto.EditeProductsInOrderRequestDto;
import org.shop.facade.dto.OrderDto;
import org.shop.facade.product.core.UserFacade;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/online/shop")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/order")
    public OrderDto getOrderByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String user) {
        final Long userId = userFacade.getUserByUserName(user).getId();
        return userFacade.getOrderByUserId(userId);
    }


    @PostMapping("/order")
    public OrderDto addProductToOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String user,
            @RequestBody EditeProductsInOrderRequestDto dto
    ) {
        final Long userId = userFacade.getUserByUserName(user).getId();
        return userFacade.addProductToOrder(userId, dto);
    }


    @DeleteMapping("/order")
    public OrderDto removeProductFromOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String user,
            @RequestBody EditeProductsInOrderRequestDto dto
    ) {
        final Long userId = userFacade.getUserByUserName(user).getId();
        return userFacade.removeProductFromOrder(userId, dto);
    }


    @PutMapping("/order")
    public OrderDto submitOrderByUserId(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String user
    ) {
        final Long userId = userFacade.getUserByUserName(user).getId();
        return userFacade.submitOrderByUserId(userId);
    }
}
