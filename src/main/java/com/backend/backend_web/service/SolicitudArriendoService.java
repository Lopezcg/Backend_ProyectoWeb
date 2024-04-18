package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.entity.SolicitudArriendo;
import com.backend.backend_web.repository.SolicitudArrendamientoRepository;
import com.backend.backend_web.service.CalificacionService;

@Service
public class SolicitudArriendoService {

    @Autowired
    private CalificacionService calificacionService;

    @Autowired
    private SolicitudArrendamientoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public SolicitudArriendoDTO get(Long id) {
        Optional<SolicitudArriendo> solicitudOptional = repository.findById(id);
        SolicitudArriendoDTO solicitudDTO = null;
        if (solicitudOptional.isPresent()) {
            solicitudDTO = modelMapper.map(solicitudOptional.get(), SolicitudArriendoDTO.class);
        }
        return solicitudDTO;
    }

    public List<SolicitudArriendoDTO> get() {
        List<SolicitudArriendo> solicitudes = (List<SolicitudArriendo>) repository.findAll();
        List<SolicitudArriendoDTO> solicitudesDTO = solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArriendoDTO.class))
                .collect(Collectors.toList());
        return solicitudesDTO;
    }

    public SolicitudArriendoDTO save(SolicitudArriendoDTO solicitudDTO) {
        SolicitudArriendo solicitud = modelMapper.map(solicitudDTO, SolicitudArriendo.class);
        solicitud.setEstado(true); // Assuming true is for active status, adjust accordingly
        solicitud.setStatus(0); // Assuming 0 is for active status, adjust accordingly
        solicitud = repository.save(solicitud);
        solicitudDTO.setId(solicitud.getId());
        return solicitudDTO;
    }

    public SolicitudArriendoDTO update(SolicitudArriendoDTO solicitudDTO) {
        if (!repository.existsById(solicitudDTO.getId())) {
            throw new RuntimeException("Registro no encontrado");
        }
        SolicitudArriendo solicitud = modelMapper.map(get(solicitudDTO.getId()), SolicitudArriendo.class);
        solicitud.setEstado(true); 
        solicitud.setStatus(0);
        solicitud.setArrendatario(solicitudDTO.getArrendatario());
        solicitud.setCalificacion(solicitudDTO.getCalificacion());
        solicitud.setCantidadPersonas(solicitudDTO.getCantidadPersonas());
        solicitud.setFechafin(solicitudDTO.getFechainicio());
        solicitud.setFechafin(solicitudDTO.getFechafin());
        solicitud.setPropiedad(solicitudDTO.getPropiedad());
        solicitud.setPago(solicitudDTO.getPago());// Adjust the status as necessary
        solicitud = repository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudArriendoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
