package com.example.sportalproject.repository;

import com.example.sportalproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);


    User findByUsername(String username);

    User findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
