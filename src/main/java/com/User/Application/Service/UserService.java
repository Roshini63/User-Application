package com.User.Application.Service;

import com.User.Application.DTO.LoginRequest;
import com.User.Application.DTO.UserRequest;
import com.User.Application.DTO.UserResponse;
import com.User.Application.Entity.User;
import com.User.Application.Exception.UserNotFoundException;
import com.User.Application.Repository.UserRepo;
import com.User.Application.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.*;

import java.util.*;

//@Service
//public class UserService {
//
//    private final UserRepository repo;
//    private final BCryptPasswordEncoder encoder;
//
//    public UserService(UserRepository repo, BCryptPasswordEncoder encoder) {
//        this.repo = repo;
//        this.encoder = encoder;
//    }
//
//    // CREATE
//    public User create(User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        return repo.save(user);
//    }
//
//    // READ ALL (Pagination)
//    public Page<User> getAll(Pageable pageable) {
//        return repo.findAll(pageable);
//    }
//
//    // SEARCH
//    public List<User> searchByName(String name) {
//        return repo.findByNameContaining(name);
//    }
//
//    // LOGIN
//    public String login(String email, String password) {
//        User user = repo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (encoder.matches(password, user.getPassword())) {
//            return "Login successful";
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//
//    // DELETE
//    public void delete(Long id) {
//        repo.deleteById(id);
//    }
//}



@Service
public class UserService {

    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepo repo, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // CREATE
    public UserResponse create(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        repo.save(user);
        return new UserResponse(user.getName(), user.getEmail());
    }

    // LOGIN
    public String login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getEmail());
    }

    // GET ALL
    public Page<User> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // UPDATE
    public User update(Long id, UserRequest req) {
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setName(req.getName());
        user.setEmail(req.getEmail());

        return repo.save(user);
    }

    // DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}