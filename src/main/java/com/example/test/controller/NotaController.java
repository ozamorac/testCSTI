package com.example.test.controller;

import com.example.test.entity.Nota;
import com.example.test.request.NotaRequest;
import com.example.test.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nota")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping(path = "/listar",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarNotas() {

        List<Nota> listNota = null;
        Map<String, Object> response = new HashMap<>();
        try {
            listNota = notaService.findAllNotas();
        } catch (Exception e) {
            response.put("Mensaje", e.getMessage());
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (listNota == null || listNota.size()==0) {
            response.put("Mensaje", "No se encontraron datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Nota>>(listNota, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNota(@Valid @RequestBody NotaRequest request, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            return buildResponseError(errors);
        }
        Map<String, Object> response = new HashMap<>();

        Nota newNota = null;
        try {
            newNota = notaService.saveNota(request);

        } catch (Exception e) {
            response.put("Mensaje", "Error al registrar los datos: " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("Mensaje", "Registro creado satisfactoriamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> buildResponseError(List<String> errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("Mensaje", errors);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

}
