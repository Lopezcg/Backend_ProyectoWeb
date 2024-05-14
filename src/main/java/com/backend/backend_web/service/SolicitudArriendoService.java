package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.entity.SolicitudArriendo;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.SolicitudArrendamientoRepository;

@Service
public class SolicitudArriendoService {

    @Autowired
    private SolicitudArrendamientoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public SolicitudArriendoDTO get(Long id) throws RegistroNoEncontradoException {
        Optional<SolicitudArriendo> solicitudOptional = repository.findById(id);
        SolicitudArriendoDTO solicitudDTO = null;
        if (!solicitudOptional.isPresent()) {
            throw new RegistroNoEncontradoException("Solicitud de arriendo no encontrada con el ID: " + id);
        }
        solicitudDTO = modelMapper.map(solicitudOptional.get(), SolicitudArriendoDTO.class);
        return solicitudDTO;
    }

    public List<SolicitudArriendoDTO> get() {
        List<SolicitudArriendo> solicitudes = (List<SolicitudArriendo>) repository.findAll();
        List<SolicitudArriendoDTO> solicitudesDTO = solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArriendoDTO.class))
                .collect(Collectors.toList());
        return solicitudesDTO;
    }

    public SolicitudArriendoDTO save(SolicitudArriendoDTO solicitudDTO)
            throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (solicitudDTO == null) {
            throw new IllegalArgumentException("El DTO de Solicitud de Arriendo no puede ser nulo");
        }

        if (solicitudDTO.getFechainicio() == null || solicitudDTO.getFechafin() == null
                || solicitudDTO.getCantidadPersonas() == null) {
            throw new IllegalArgumentException(
                    "Los campos de fecha de inicio, fecha de fin y cantidad de personas son obligatorios");
        }

        SolicitudArriendo solicitud;

        try {
            solicitud = modelMapper.map(solicitudDTO, SolicitudArriendo.class);
            solicitud.setEstado(true); // Assuming true is for active status, adjust accordingly
            solicitud.setStatus(0); // Assuming 0 is for active status, adjust accordingly
            solicitud = repository.save(solicitud);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Error al guardar la solicitud de arriendo debido a una violación de integridad", e);
        } catch (Exception e) {
            // Capturar cualquier otra excepción no especificada
            throw new IllegalStateException("Error inesperado al guardar la solicitud de arriendo", e);
        }

        solicitudDTO.setId(solicitud.getId());
        return solicitudDTO;
    }

    public SolicitudArriendoDTO update(SolicitudArriendoDTO solicitudDTO) throws RegistroNoEncontradoException {
        if (!repository.existsById(solicitudDTO.getId())) {
            throw new RuntimeException("Registro no encontrado");
        }
        SolicitudArriendo solicitud = modelMapper.map(get(solicitudDTO.getId()), SolicitudArriendo.class);
        solicitud.setEstado(true);
        solicitud.setStatus(0);

        solicitud.setCantidadPersonas(solicitudDTO.getCantidadPersonas());
        solicitud.setFechafin(solicitudDTO.getFechainicio());
        solicitud.setFechafin(solicitudDTO.getFechafin());// Adjust the status as necessary
        solicitud = repository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudArriendoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
