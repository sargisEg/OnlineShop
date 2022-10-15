package org.shop.service.impl;

import org.assertj.core.api.Assertions;
import org.shop.entity.User;
import org.shop.entity.UserRoleType;
import org.shop.repository.UserRepository;
import org.shop.service.core.user.CreateUserParams;
import org.shop.service.core.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService testSubject;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        testSubject = new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.create(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findById(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByFirstNameAndLastNameWhenFirstNameIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByFirstNameAndLastName(null, "l");
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByFirstNameAndLastNameWhenLastNameIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByFirstNameAndLastName("f", null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreate() {
        final User user = new User(
                "first",
                "last",
                UserRoleType.ROLE_USER
        );
        user.setId(1L);

        when(userRepository.save(
                new User(
                        "first",
                        "last",
                        UserRoleType.ROLE_USER
                )
        )).thenReturn(user);

        Assertions.assertThat(testSubject.create(
                new CreateUserParams(
                        "first",
                        "last",
                        UserRoleType.ROLE_USER
                )
        )).isEqualTo(user);

        verify(userRepository).save(new User(
                        "first",
                        "last",
                        UserRoleType.ROLE_USER
                ));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testFindById() {
        final User user = new User(
                "first",
                "last",
                UserRoleType.ROLE_USER
        );
        user.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Assertions.assertThat(testSubject.findById(1L))
                        .isEqualTo(Optional.of(user));

        verify(userRepository).findById(1L);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        when(userRepository.findByFirstNameAndLastName("f", "l"))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThat(testSubject.findByFirstNameAndLastName("f", "l"))
                .isEqualTo(Optional.of(new User()));

        verify(userRepository).findByFirstNameAndLastName("f", "l");

        verifyNoMoreInteractions(userRepository);
    }
}