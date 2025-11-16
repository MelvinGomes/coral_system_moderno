package com.coral.controller;

import com.coral.dao.CoristaDAO;
import com.coral.model.Corista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/coristas")
public class CoristaController {

    private final CoristaDAO dao;

    @Autowired
    public CoristaController(CoristaDAO coristaDAO) {
        this.dao = coristaDAO;
    }

    @GetMapping
    public List<Corista> listarTodos() throws SQLException {
        return dao.findAll();
    }

    @GetMapping("/{id}")
public Corista buscarPorId(@PathVariable int id) {
    try {
        Corista corista = dao.findById(id);
        if (corista == null) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corista não encontrado");
        }

        return corista;
    } catch (SQLException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar corista", e);
    }
}

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody Corista corista) throws SQLException {
        dao.insert(corista);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Corista corista) {
    try {

        Corista coristaExistente = dao.findById(id);
        if (coristaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corista não encontrado");
        }

        corista.setId(id);
        
        dao.update(corista);
        
        return ResponseEntity.ok().build();
    } catch (SQLException e) {

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar corista", e);
    }

}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) throws SQLException {
        dao.delete(id);
        return ResponseEntity.ok().build();
    }

    
}