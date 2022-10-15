package org.shop.service.core.user;

import org.shop.entity.UserRoleType;
import org.springframework.util.Assert;

import java.util.Objects;

public class CreateUserParams {

    private final String firstName;

    private final String lastName;

    private final UserRoleType userRoleType;

    public CreateUserParams(String firstName, String lastName, UserRoleType userRoleType) {
        Assert.hasText(
                firstName,
                "Class - CreateUserParams, method - constructor " +
                        "first name should not be null or empty");
        Assert.hasText(
                lastName,
                "Class - CreateUserParams, method - constructor " +
                        "last name should not be null or empty");
        Assert.notNull(
                userRoleType,
                "Class - CreateUserParams, method - constructor " +
                        "user role type should not be null");
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoleType = userRoleType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateUserParams{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userRoleType=").append(userRoleType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateUserParams that)) return false;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && userRoleType == that.userRoleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, userRoleType);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }
}
