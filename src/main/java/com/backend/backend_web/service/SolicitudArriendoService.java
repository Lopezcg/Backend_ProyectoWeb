package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.entity.Arrendatario;
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

    public List<SolicitudArriendoDTO> getSolicitudArriendoByArrendatario(ArrendatarioDTO arrendatarioDTO) {
        List<SolicitudArriendo> solicitudes = repository.findByArrendatarioId(arrendatarioDTO.getId());
        List<SolicitudArriendoDTO> solicitudesDTO = solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArriendoDTO.class)).collect(Collectors.toList());
        return solicitudesDTO;
    }

    public List<SolicitudArriendoDTO> getSolicitudArriendoByArrendador(ArrendadorDTO arrendadorDTO) {
        List<SolicitudArriendo> solicitudes = repository.findByArrendadorId(arrendadorDTO.getId());
        List<SolicitudArriendoDTO> solicitudesDTO = solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArriendoDTO.class)).collect(Collectors.toList());
        return solicitudesDTO;
    }

    public SolicitudArriendoDTO save(SolicitudArriendoDTO solicitudDTO, ArrendatarioDTO arrendatarioDTO)
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
        Arrendatario arrendatario = modelMapper.map(arrendatarioDTO, Arrendatario.class);
        if (arrendatario == null) {
            throw new IllegalArgumentException("El arrendatario proporcionado no puede ser nulo");
        }

        try {
            solicitud = modelMapper.map(solicitudDTO, SolicitudArriendo.class);
            solicitud.setArrendatario(arrendatario);
            solicitud.setEstado(false); // Assuming true is for active status, adjust accordingly
            solicitud.setAceptado(false);
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

    public SolicitudArriendoDTO update(SolicitudArriendoDTO solicitudDTO, ArrendadorDTO arrendadorDTO)
            throws RegistroNoEncontradoException, IllegalArgumentException {
        if (solicitudDTO == null) {
            throw new IllegalArgumentException("El objeto SolicitudArriendoDTO proporcionado es nulo");
        }

        SolicitudArriendo solicitud = repository.findById(solicitudDTO.getId())
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + solicitudDTO.getId()));
        if (solicitud.getPropiedad().getArrendador().getId() != arrendadorDTO.getId()) {
            throw new IllegalArgumentException("La solicitud de arriendo no pertenece al arrendatario proporcionado");
        }
        // Actualizamos los campos necesarios de la entidad
        solicitud.setAceptado(solicitudDTO.isAceptado());
        solicitud.setEstado(solicitudDTO.isEstado()); // Asumiendo que esto es un estado activo/inactivo
        solicitud.setStatus(0);
        solicitud.setCantidadPersonas(solicitudDTO.getCantidadPersonas());
        solicitud.setFechainicio(solicitudDTO.getFechainicio()); // Asumiendo que el método se llama setFechaInicio
        solicitud.setFechafin(solicitudDTO.getFechafin());

        // Guardamos la entidad actualizada
        solicitud = repository.save(solicitud);

        // Convertimos la entidad guardada de vuelta a DTO y la retornamos
        return modelMapper.map(solicitud, SolicitudArriendoDTO.class);
    }

    public void delete(Long id, ArrendatarioDTO arrendatarioDTO) throws RegistroNoEncontradoException {
        Optional<SolicitudArriendo> solicitud = repository.findById(id);
        if (!solicitud.isPresent()) {
            throw new RegistroNoEncontradoException(
                    "Solicitud no encontrada con ID " + id + " por lo tanto no se puede eliminar");
        }
        if (solicitud.get().getArrendatario().getId() == arrendatarioDTO.getId()) {
            throw new IllegalArgumentException("La solicitud de arriendo no pertenece al arrendatario proporcionado");
        }
        repository.deleteById(id);
    }
}
