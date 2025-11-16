package com.coral.dao;

import com.coral.model.Musico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicoDAO {

    private final DataSource dataSource;

    @Autowired
    public MusicoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Musico> findAll() throws SQLException {
        List<Musico> list = new ArrayList<>();
        String sql = "SELECT id, nome, instrumento, ativo FROM musicos";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Musico m = new Musico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setInstrumento(rs.getString("instrumento"));
                m.setAtivo(rs.getBoolean("ativo"));
                list.add(m);
            }
        }
        return list;
    }

    public Musico findById(int id) throws SQLException {
        String sql = "SELECT id, nome, instrumento, ativo FROM musicos WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Musico m = new Musico();
                    m.setId(rs.getInt("id"));
                    m.setNome(rs.getString("nome"));
                    m.setInstrumento(rs.getString("instrumento"));
                    m.setAtivo(rs.getBoolean("ativo"));
                    return m;
                }
            }
        }
        return null;
    }

    public void insert(Musico musico) throws SQLException {
        String sql = "INSERT INTO musicos (nome, instrumento, ativo) VALUES (?, ?, ?)";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, musico.getNome());
            ps.setString(2, musico.getInstrumento());
            ps.setBoolean(3, musico.isAtivo());
            ps.executeUpdate();
        }
    }

    public void update(Musico musico) throws SQLException {
        String sql = "UPDATE musicos SET nome = ?, instrumento = ?, ativo = ? WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, musico.getNome());
            ps.setString(2, musico.getInstrumento());
            ps.setBoolean(3, musico.isAtivo());
            ps.setInt(4, musico.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM musicos WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}