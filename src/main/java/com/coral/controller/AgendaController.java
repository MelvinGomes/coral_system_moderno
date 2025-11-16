package com.coral.controller;

import com.coral.dao.AgendaDAO;
import com.coral.model.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    private final AgendaDAO dao;

    @Autowired
    public AgendaController(AgendaDAO agendaDAO) {
        this.dao = agendaDAO;
    }

    @GetMapping
    public List<Agenda> listarTodos() throws SQLException {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public Agenda buscarPorId(@PathVariable int id) throws SQLException {
        Agenda agenda = dao.findById(id);
        if (agenda == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
        return agenda;
    }

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody Agenda evento) throws SQLException {
        dao.insert(evento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Agenda evento) throws SQLException {
        evento.setId(id);
        dao.update(evento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) throws SQLException {
        dao.delete(id);
        return ResponseEntity.ok().build();
    }
}