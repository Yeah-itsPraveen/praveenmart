package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.UserDAO;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.User;
import com.praveen.praveenmart.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userDAO);
    }

    @Test
    public void testAuthenticateSuccess() {
        String hash = PasswordUtil.hashPassword("secret123");
        User user = new User(1L, "Praveen", "praveen@test.com", hash, "BUYER", null);
        when(userDAO.findByEmail("praveen@test.com")).thenReturn(user);

        User authenticated = userService.authenticate("praveen@test.com", "secret123");
        assertNotNull(authenticated);
        assertEquals("Praveen", authenticated.getName());
    }

    @Test
    public void testAuthenticateWrongPassword() {
        String hash = PasswordUtil.hashPassword("secret123");
        User user = new User(1L, "Praveen", "praveen@test.com", hash, "BUYER", null);
        when(userDAO.findByEmail("praveen@test.com")).thenReturn(user);

        User authenticated = userService.authenticate("praveen@test.com", "wrongpass");
        assertNull(authenticated);
    }

    @Test
    public void testRegisterPasswordTooShortThrowsException() {
        assertThrows(ValidationException.class, () -> {
            userService.registerUser("Praveen", "praveen@test.com", "short", "BUYER");
        });
    }

    @Test
    public void testRegisterSuccess() {
        when(userDAO.findByEmail("newuser@test.com")).thenReturn(null);
        when(userDAO.createUser(any(User.class))).thenReturn(true);

        boolean registered = userService.registerUser("New User", "newuser@test.com", "validpassword123", "SELLER");
        assertTrue(registered);
        verify(userDAO, times(1)).createUser(any(User.class));
    }
}
