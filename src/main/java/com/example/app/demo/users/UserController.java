package com.example.app.demo.users;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public List<UserEntity> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Long id, @RequestBody UserEntity user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
