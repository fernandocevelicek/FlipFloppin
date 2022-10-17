package com.grupo1.FlipFloppin.controllers.rest;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonException.toString());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException.toString());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UsuarioDTO usuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException.toString());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDTO usuario) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(usuario, id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarioService.deleteById(id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException.toString());
        }
    }

}
