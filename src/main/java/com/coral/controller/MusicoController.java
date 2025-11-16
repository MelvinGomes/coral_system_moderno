package com.coral.controller;

import com.coral.dao.MusicoDAO;
import com.coral.model.Musico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/musicos")
public class MusicoController {

    private final MusicoDAO dao;

    @Autowired
    public MusicoController(MusicoDAO musicoDAO) {
        this.dao = musicoDAO;
    }

    @GetMapping
    public List<Musico> listarTodos() throws SQLException {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public Musico buscarPorId(@PathVariable int id) throws SQLException {
        Musico musico = dao.findById(id);
        if (musico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Músico não encontrado");
        }
        return musico;
    }

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody Musico musico) throws SQLException {
        dao.insert(musico);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Musico musico) throws SQLException {
        musico.setId(id);
        dao.update(musico);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) throws SQLException {
        dao.delete(id);
        return ResponseEntity.ok().build();
    }
}