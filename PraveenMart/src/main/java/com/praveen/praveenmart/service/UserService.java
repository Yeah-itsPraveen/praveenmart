package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.UserDAO;
import com.praveen.praveenmart.dao.impl.UserDAOImpl;
import com.praveen.praveenmart.dto.UserResponseDTO;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.User;
import com.praveen.praveenmart.util.PasswordUtil;
import com.praveen.praveenmart.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService() {
        this(new UserDAOImpl());
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticate(String email, String password) {
        if (!ValidationUtil.isValidEmail(email) || password == null || password.isBlank()) {
            return null;
        }

        User user = userDAO.findByEmail(email.trim());
        if (user == null) {
            logger.warn("Authentication failed: user not found for email {}", email);
            return null;
        }

        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            logger.warn("Authentication failed: invalid password for email {}", email);
            return null;
        }

        return user;
    }

    public boolean registerUser(String name, String email, String password, String role) {
        if (!ValidationUtil.isValidName(name)) {
            throw new ValidationException("Invalid name provided.");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            throw new ValidationException("Invalid email format.");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            throw new ValidationException("Password must be at least 8 characters long.");
        }

        if (userDAO.findByEmail(email.trim()) != null) {
            logger.warn("Registration failed: email already exists {}", email);
            return false;
        }

        String normalizedRole = "BUYER";
        if (role != null) {
            String r = role.trim().toUpperCase();
            if ("SELLER".equals(r)) {
                normalizedRole = "SELLER";
            } else {
                normalizedRole = "BUYER";
            }
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User();
        newUser.setName(name.trim());
        newUser.setEmail(email.trim().toLowerCase());
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole(normalizedRole);

        boolean created = userDAO.createUser(newUser);
        if (created) {
            logger.info("User registered successfully: id={}, email={}, role={}", newUser.getId(), newUser.getEmail(), newUser.getRole());
        }
        return created;
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        List<UserResponseDTO> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(new UserResponseDTO(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getCreatedAt()));
        }
        return dtos;
    }

    public boolean deleteUser(Long id) {
        return userDAO.deleteUser(id);
    }

    public int getTotalUsersCount() {
        return userDAO.countUsers();
    }

    public int getBuyersCount() {
        return userDAO.countUsersByRole("BUYER");
    }

    public int getSellersCount() {
        return userDAO.countUsersByRole("SELLER");
    }
}
