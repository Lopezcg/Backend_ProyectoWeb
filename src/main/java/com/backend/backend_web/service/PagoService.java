package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PagoService(PagoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public PagoDTO get(Long id) throws RegistroNoEncontradoException {
        Optional<Pago> pagoOptional = repository.findById(id);
        if (pagoOptional.isEmpty()) {
            throw new RegistroNoEncontradoException("Pago no encontrado con el ID: " + id);
        }
        return pagoOptional.map(pago -> modelMapper.map(pago, PagoDTO.class)).orElse(null);
    }

    public List<PagoDTO> get() {
        List<Pago> pagos = (List<Pago>) repository.findAll();
        System.out.println("Pagos from DB: " + pagos); // Ver los datos directamente de la base de datos
        List<PagoDTO> pagoDTOs = pagos.stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
        System.out.println("Mapped PagoDTOs: " + pagoDTOs); // Ver los datos después del mapeo
        return pagoDTOs;
    }

    public PagoDTO save(PagoDTO pagoDTO) throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (pagoDTO == null) {
            throw new IllegalArgumentException("El DTO de Pago no puede ser nulo");
        }

        if (pagoDTO.getBanco() == null || pagoDTO.getNumCuenta() == null || pagoDTO.getValor() == null) {
            throw new IllegalArgumentException("El DTO de Pago no puede contener valores nulos");
        }

        Pago pago;

        try {
            pago = modelMapper.map(pagoDTO, Pago.class);
            pago.setStatus(0);
            pago = repository.save(pago);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar el pago debido a una violación de integridad", e);
        } catch (Exception e) {
            // Capturar cualquier otra excepción no especificada
            throw new IllegalStateException("Error inesperado al guardar el pago", e);
        }

        pagoDTO.setId(pago.getId());
        return pagoDTO;
    }

    public PagoDTO update(PagoDTO pagoDTO) {
        if (pagoDTO == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        Pago pago = modelMapper.map(pagoDTO.getId(), Pago.class);
        pago.setStatus(0);
        pago.setBanco(pagoDTO.getBanco());
        pago.setNumCuenta(pagoDTO.getNumCuenta());
        pago.setValor(pagoDTO.getValor());
        pago = repository.save(pago);
        return modelMapper.map(pago, PagoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
