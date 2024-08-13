package com.example.jardinagem;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByName(String nome);
    Optional<User> findById(long id);
    User findByid(long id);
}