package br.com.projeto.dao;

import br.com.projeto.model.Curso;
import br.com.projeto.model.Area;
import java.util.List;

public interface ICursoDAO {
    void create(Curso curso);
    Curso findById(Long id);
    List<Curso> findAll();
    List<Curso> findByArea(Area area);
    Curso findBySigla(String sigla);
    void update(Curso curso);
    void delete(Long id);
}
