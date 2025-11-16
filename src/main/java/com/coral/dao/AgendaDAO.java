package com.coral.dao;

import com.coral.model.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgendaDAO {

    private final DataSource dataSource;

    @Autowired
    public AgendaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Agenda> findAll() throws SQLException {
        List<Agenda> list = new ArrayList<>();
        String sql = "SELECT id, data, local, descricao FROM agenda_apresentacoes";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Agenda a = new Agenda();
                a.setId(rs.getInt("id"));
                a.setData(rs.getDate("data"));
                a.setLocal(rs.getString("local"));
                a.setDescricao(rs.getString("descricao"));
                list.add(a);
            }
        }
        return list;
    }

    public Agenda findById(int id) throws SQLException {
        String sql = "SELECT id, data, local, descricao FROM agenda_apresentacoes WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Agenda a = new Agenda();
                    a.setId(rs.getInt("id"));
                    a.setData(rs.getDate("data"));
                    a.setLocal(rs.getString("local"));
                    a.setDescricao(rs.getString("descricao"));
                    return a;
                }
            }
        }
        return null;
    }

    public void insert(Agenda a) throws SQLException {
        String sql = "INSERT INTO agenda_apresentacoes (`data`, `local`, `descricao`) VALUES (?,?,?)";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, a.getData());
            ps.setString(2, a.getLocal());
            ps.setString(3, a.getDescricao());
            ps.executeUpdate();
        }
    }

    public void update(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda_apresentacoes SET data = ?, local = ?, descricao = ? WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, agenda.getData());
            ps.setString(2, agenda.getLocal());
            ps.setString(3, agenda.getDescricao());
            ps.setInt(4, agenda.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM agenda_apresentacoes WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}