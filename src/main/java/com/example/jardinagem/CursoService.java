package com.example.jardinagem;

import java.util.List;

public interface CursoService {
    void saveCurso(CursoDto cursoDto);

    Curso findCursoByNome(String Nome);

    List<Curso> findAllCursos();
}
