package com.example.app.demo.users;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity user);

    UserEntity getUserById(Long id);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(Long id, UserEntity user);

    void deleteUser(Long id);
}
