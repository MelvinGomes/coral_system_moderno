package com.coral.controller;

import com.coral.dao.PresencaDAO;
import com.coral.model.Presenca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/agenda/{idAgenda}/presencas")
public class PresencaController {

    private final PresencaDAO dao;

    @Autowired
    public PresencaController(PresencaDAO presencaDAO) {
        this.dao = presencaDAO;
    }

    @GetMapping
    public List<Presenca> getPresencas(@PathVariable int idAgenda) throws SQLException {
        return dao.findByAgendaId(idAgenda);
    }

    @PostMapping
    public ResponseEntity<Void> salvarPresencas(@PathVariable int idAgenda, @RequestBody List<Presenca> presencas) throws SQLException {
        for (Presenca p : presencas) {
            // Usa o novo método do DAO
            dao.marcarPresenca(p.getIdParticipante(), p.getTipoParticipante(), idAgenda, p.isPresente());
        }
        return ResponseEntity.ok().build();
    }
}