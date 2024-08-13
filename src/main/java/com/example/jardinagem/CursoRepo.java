package com.example.jardinagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepo extends JpaRepository<Curso,Long> {
    Curso findByNome(String nome);
    Optional<Curso> findById(long id);
    Curso findByid(long id);
}
