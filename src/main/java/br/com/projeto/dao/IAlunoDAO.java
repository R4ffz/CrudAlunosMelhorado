package br.com.projeto.dao;

import br.com.projeto.model.Aluno;
import java.util.List;

public interface IAlunoDAO {
    void create(Aluno aluno);
    Aluno findById(int id);
    List<Aluno> findAll();
    List<Aluno> findByCurso(String curso);
    void update(Aluno aluno);
    void delete(int id);
}
