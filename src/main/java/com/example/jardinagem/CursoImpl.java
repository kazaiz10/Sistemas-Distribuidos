package com.example.jardinagem;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoImpl implements CursoService {
    private CursoRepo cursoRepo;

    public CursoImpl(CursoRepo cursoRepo){
        this.cursoRepo = cursoRepo;
    }

    @Override
    public void saveCurso(CursoDto cursoDto){
        Curso curso = new Curso();
        curso.setNome(cursoDto.getNome());
        curso.setDatai(cursoDto.getDatai());
        curso.setDataf(cursoDto.getDataf());
        cursoRepo.save(curso);
    }

    @Override
    public Curso findCursoByNome(String Nome){
        return cursoRepo.findByNome(Nome);
    }

    @Override
    public List<Curso> findAllCursos(){
        return cursoRepo.findAll();
    }
}