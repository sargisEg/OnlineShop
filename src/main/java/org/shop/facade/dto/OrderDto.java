package org.shop.facade.dto;

import org.shop.entity.OrderStatus;

import java.util.List;
import java.util.Objects;

public class OrderDto {

    private Long id;

    private UserDto userDto;

    private OrderStatus orderStatus;

    private List<ProductInOrderDto> productInOrderDtoList;

    private List<String> errors;

    public OrderDto(List<String> errors) {
        this.errors = errors;
    }

    public OrderDto(Long id, UserDto userDto, OrderStatus orderStatus, List<ProductInOrderDto> productInOrderDtoList) {
        this.id = id;
        this.userDto = userDto;
        this.orderStatus = orderStatus;
        this.productInOrderDtoList = productInOrderDtoList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", userDto=").append(userDto);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", productInOrderDtoList=").append(productInOrderDtoList);
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDto orderDto)) return false;
        return Objects.equals(id, orderDto.id) && Objects.equals(userDto, orderDto.userDto) && orderStatus == orderDto.orderStatus && Objects.equals(productInOrderDtoList, orderDto.productInOrderDtoList) && Objects.equals(errors, orderDto.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userDto, orderStatus, productInOrderDtoList, errors);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<ProductInOrderDto> getProductInOrderDtoList() {
        return productInOrderDtoList;
    }

    public void setProductInOrderDtoList(List<ProductInOrderDto> productInOrderDtoList) {
        this.productInOrderDtoList = productInOrderDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
