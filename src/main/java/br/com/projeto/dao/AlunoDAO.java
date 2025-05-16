package br.com.projeto.dao;

import br.com.projeto.config.ConnectionFactory;
import br.com.projeto.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements IAlunoDAO {
    private static final String INSERT =
            "INSERT INTO alunos (nome, email, idade, curso) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID =
            "SELECT * FROM alunos WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM alunos";
    private static final String SELECT_BY_CURSO =
            "SELECT * FROM alunos WHERE curso = ?";
    private static final String UPDATE =
            "UPDATE alunos SET nome = ?, email = ?, idade = ?, curso = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM alunos WHERE id = ?";

    @Override
    public void create(Aluno aluno) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getCurso());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    aluno.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aluno findById(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getInt("idade"),
                            rs.getString("curso")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getInt("idade"),
                        rs.getString("curso")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    @Override
    public List<Aluno> findByCurso(String curso) {
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CURSO)) {
            stmt.setString(1, curso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getInt("idade"),
                            rs.getString("curso")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    @Override
    public void update(Aluno aluno) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getCurso());
            stmt.setInt(5, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
