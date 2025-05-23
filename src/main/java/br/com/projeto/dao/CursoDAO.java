package br.com.projeto.dao;

import br.com.projeto.config.ConnectionFactory;
import br.com.projeto.model.Curso;
import br.com.projeto.model.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements ICursoDAO {

    private static final String INSERT =
            "INSERT INTO cursos (nome, sigla, area) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID    =
            "SELECT * FROM cursos WHERE id = ?";
    private static final String SELECT_ALL      =
            "SELECT * FROM cursos";
    private static final String SELECT_BY_AREA  =
            "SELECT * FROM cursos WHERE area = ?";
    private static final String SELECT_BY_SIGLA =
            "SELECT * FROM cursos WHERE sigla = ?";
    private static final String UPDATE =
            "UPDATE cursos SET nome = ?, sigla = ?, area = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM cursos WHERE id = ?";

    @Override
    public void create(Curso curso) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getSigla());
            ps.setString(3, curso.getArea().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) curso.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Curso findById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
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
    public List<Curso> findByArea(Area area) {
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_AREA)) {
            ps.setString(1, area.name());
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
    public Curso findBySigla(String sigla) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_SIGLA)) {
            ps.setString(1, sigla);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Curso c) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getSigla());
            ps.setString(3, c.getArea().name());
            ps.setLong(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // mapeia um ResultSet para Curso
    private Curso mapRow(ResultSet rs) throws SQLException {
        return new Curso(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("sigla"),
                Area.valueOf(rs.getString("area"))
        );
    }
}
