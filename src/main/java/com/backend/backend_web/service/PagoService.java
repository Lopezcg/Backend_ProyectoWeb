package com.backend.backend_web.service;


import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.repository.PagoRepository;

@Service
public class PagoService {
@Autowired
    PagoRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public PagoDTO get(Long id) {
        Optional<Pago> pagoOptional = repository.findById(id);
        PagoDTO pagoDTO = null;
        if (pagoOptional.isPresent()) { // Mejorado para usar isPresent()
            pagoDTO = modelMapper.map(pagoOptional.get(), PagoDTO.class);
        }
        return pagoDTO;
    }

    public List<PagoDTO> get() {
        List<Pago> pagos = (List<Pago>) repository.findAll();
        List<PagoDTO> pagosDTO = pagos.stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class)).collect(Collectors.toList());
        return pagosDTO;
    }

    public PagoDTO save(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setStatus(0);
        pago = repository.save(pago);
        pagoDTO.setId(pago.getId());
        return pagoDTO;
    }

    public PagoDTO update(PagoDTO pagoDTO) throws ValidationException {
        PagoDTO existingPagoDTO = get(pagoDTO.getId());
        if (existingPagoDTO == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setStatus(0);
        pago = repository.save(pago);
        pagoDTO = modelMapper.map(pago, PagoDTO.class);
        return pagoDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

