package com.User.Application.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // CREATE
    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    // READ ALL (Pagination)
    public Page<User> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // SEARCH
    public List<User> searchByName(String name) {
        return repo.findByNameContaining(name);
    }

    // LOGIN
    public String login(String email, String password) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(password, user.getPassword())) {
            return "Login successful";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}