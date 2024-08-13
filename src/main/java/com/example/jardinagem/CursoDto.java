package com.example.jardinagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoDto {
    private long id;
    private String nome;
    private LocalDateTime datai;
    private LocalDateTime dataf;
}