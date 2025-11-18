package com.coral.dao;

import com.coral.model.Presenca;
import com.coral.model.TipoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PresencaDAO {

    private final DataSource dataSource;

    @Autowired
    public PresencaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    public List<Presenca> findByAgendaId(int idAgenda) throws SQLException {
        List<Presenca> presencas = new ArrayList<>();
        
        String sql = 
            "(SELECT c.id AS id_participante, c.nome, 'CORISTA' AS tipo_participante, p.presente " +
            "FROM coristas c " +
            "LEFT JOIN presencas p ON c.id = p.id_participante AND p.tipo_participante = 'CORISTA' AND p.id_agenda = ?) " +
            "UNION ALL " +
            "(SELECT m.id AS id_participante, m.nome, 'MUSICO' AS tipo_participante, p.presente " +
            "FROM musicos m " +
            "LEFT JOIN presencas p ON m.id = p.id_participante AND p.tipo_participante = 'MUSICO' AND p.id_agenda = ?)";
        
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAgenda);
            ps.setInt(2, idAgenda);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Presenca p = new Presenca();
                    p.setIdParticipante(rs.getInt("id_participante"));
                    p.setTipoParticipante(TipoParticipante.valueOf(rs.getString("tipo_participante")));
                    p.setIdAgenda(idAgenda);
                    p.setPresente(rs.getBoolean("presente") && !rs.wasNull());
                    presencas.add(p);
                }
            }
        }
        return presencas;
    }
    
    
    public void marcarPresenca(int idParticipante, TipoParticipante tipo, int idAgenda, boolean presente) throws SQLException {
        String selSql = "SELECT id FROM presencas WHERE id_participante = ? AND tipo_participante = ? AND id_agenda = ?";
        Integer presencaId = null;

        try (Connection c = dataSource.getConnection();
             PreparedStatement selPs = c.prepareStatement(selSql)) {
            selPs.setInt(1, idParticipante);
            selPs.setString(2, tipo.name());
            selPs.setInt(3, idAgenda);
            try (ResultSet rs = selPs.executeQuery()) {
                if (rs.next()) presencaId = rs.getInt("id");
            }
            
            if (presencaId != null) { // UPDATE
                String upSql = "UPDATE presencas SET presente = ? WHERE id = ?";
                try (PreparedStatement upPs = c.prepareStatement(upSql)) {
                    upPs.setBoolean(1, presente);
                    upPs.setInt(2, presencaId);
                    upPs.executeUpdate();
                }
            } else { // INSERT
                String insSql = "INSERT INTO presencas (id_participante, tipo_participante, id_agenda, presente) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insPs = c.prepareStatement(insSql)) {
                    insPs.setInt(1, idParticipante);
                    insPs.setString(2, tipo.name());
                    insPs.setInt(3, idAgenda);
                    insPs.setBoolean(4, presente);
                    insPs.executeUpdate();
                }
            }
        }
    }
}