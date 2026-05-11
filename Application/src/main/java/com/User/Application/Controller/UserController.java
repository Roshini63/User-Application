package com.User.Application.Controller;

import com.example.demo.entity.User;
import com.example.demo.dto.LoginRequest;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // REGISTER
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return service.create(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request.getEmail(), request.getPassword());
    }

    // GET ALL
    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    // SEARCH
    @GetMapping("/search")
    public List<User> search(@RequestParam String name) {
        return service.searchByName(name);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted successfully";
    }
}