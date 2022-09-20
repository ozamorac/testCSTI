package com.example.test.service.impl;

import com.example.test.entity.Nota;
import com.example.test.repository.NotaRepository;
import com.example.test.request.NotaRequest;
import com.example.test.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Nota> findAllNotas() {
        return notaRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Nota saveNota(NotaRequest notaRequest) {

        Nota nota = new Nota();

        nota.setIdCurso(notaRequest.getIdCurso());
        nota.setIdUsuario(notaRequest.getIdUsuario());
        nota.setCiclo(notaRequest.getCiclo());
        nota.setNota1(notaRequest.getNota1());
        nota.setNota2(notaRequest.getNota2());
        nota.setNota3(notaRequest.getNota3());
        nota.setNota4(notaRequest.getNota4());
        return notaRepository.save(nota);
    }

}
