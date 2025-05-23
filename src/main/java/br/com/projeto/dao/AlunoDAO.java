package br.com.projeto.dao;

import br.com.projeto.config.ConnectionFactory;
import br.com.projeto.model.Aluno;
import br.com.projeto.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements IAlunoDAO {
    private static final String INSERT =
            "INSERT INTO alunos (nome,email,idade,curso,sexo,maioridade) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_BY_ID =
            "SELECT * FROM alunos WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM alunos";
    private static final String UPDATE =
            "UPDATE alunos SET nome=?,email=?,idade=?,curso=?,sexo=?,maioridade=? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM alunos WHERE id = ?";

    private final ICursoDAO cursoDao = new CursoDAO();

    @Override
    public void create(Aluno a) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getEmail());
            ps.setInt(3, a.getIdade());
            ps.setString(4, a.getCurso().getSigla());
            ps.setString(5, a.getSexo());
            ps.setInt(6, a.getMaioridade());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Aluno mapRow(ResultSet rs) throws SQLException {
        Curso curso = cursoDao.findBySigla(rs.getString("curso"));
        return new Aluno(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getInt("idade"),
                curso,
                rs.getString("sexo"),
                rs.getInt("maioridade")
        );
    }

    @Override
    public Aluno findById(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Aluno> findByCurso(String curso) {
        List<Aluno> lista = new ArrayList<>();
        String SELECT_BY_CURSO = "SELECT * FROM alunos WHERE curso = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_CURSO)) {
            ps.setString(1, curso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void update(Aluno a) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getEmail());
            ps.setInt(3, a.getIdade());
            ps.setString(4, a.getCurso().getSigla());
            ps.setString(5, a.getSexo());
            ps.setInt(6, a.getMaioridade());
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
