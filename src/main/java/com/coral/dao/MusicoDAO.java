package com.coral.dao;

import com.coral.model.Musico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class MusicoDAO {

    private final DataSource dataSource;

    @Autowired
    public MusicoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Musico> findAll() throws SQLException {
        // ... (este método já existe e está correto)
        List<Musico> list = new ArrayList<>();
        String sql = "SELECT id,nome,instrumento,ativo FROM musicos";
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

    // Adicione estes métodos dentro da sua classe MusicoDAO

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
    return null; // Retorna null se não encontrar
}

public void update(Musico musico) throws SQLException {
    String sql = "UPDATE musicos SET nome = ?, instrumento = ?, ativo = ? WHERE id = ?";
    try (Connection c = dataSource.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, musico.getNome());
        ps.setString(2, musico.getInstrumento());
        ps.setBoolean(3, musico.isAtivo());
        ps.setInt(4, musico.getId()); // ID é o último parâmetro para o WHERE
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