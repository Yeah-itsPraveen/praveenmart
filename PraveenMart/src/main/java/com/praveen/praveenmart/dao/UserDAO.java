package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.User;

import java.util.List;

public interface UserDAO {

    User findById(Long id);

    User findByEmail(String email);

    List<User> findAll();

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);

    int countUsers();

    int countUsersByRole(String role);
}
