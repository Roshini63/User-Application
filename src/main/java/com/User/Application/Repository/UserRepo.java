package com.User.Application.Repository;


import com.User.Application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    List<User> findByNameContaining(String name);
}
