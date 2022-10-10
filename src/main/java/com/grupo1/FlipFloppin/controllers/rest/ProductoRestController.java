package com.grupo1.FlipFloppin.controllers.rest;

import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/productos")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.findAll());
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonException);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.findById(id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Producto producto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.update(producto, id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productoService.deleteById(id));
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonException);
        }
    }
}
