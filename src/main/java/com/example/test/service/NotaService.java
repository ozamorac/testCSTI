package com.example.test.service;

import com.example.test.entity.Nota;
import com.example.test.request.NotaRequest;

import java.util.List;

public interface NotaService {

    List<Nota> findAllNotas();

    Nota saveNota(NotaRequest notaRequest);

}
