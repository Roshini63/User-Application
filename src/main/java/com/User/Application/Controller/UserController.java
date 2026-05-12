package com.User.Application.Controller;

import com.User.Application.DTO.UserRequest;
import com.User.Application.DTO.UserResponse;
import com.User.Application.Entity.User;
import com.User.Application.DTO.LoginRequest;
import com.User.Application.Service.UserService;

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
    public UserResponse create(@RequestBody UserRequest req) {
        return service.create(req);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return service.login(req);
    }

    // GET ALL
    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserRequest req) {
        return service.update(id, req);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}