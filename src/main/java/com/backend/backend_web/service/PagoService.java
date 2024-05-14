package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.entity.Pago;
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

    public PagoDTO get(Long id) {
        Optional<Pago> pagoOptional = repository.findById(id);
        return pagoOptional.map(pago -> modelMapper.map(pago, PagoDTO.class)).orElse(null);
    }

    public List<PagoDTO> get() {
        List<Pago> pagos = (List<Pago>) repository.findAll();
        return pagos.stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
    }

    public PagoDTO save(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setStatus(0);
        pago = repository.save(pago);
        pagoDTO.setId(pago.getId());
        return pagoDTO;
    }

    public PagoDTO update(PagoDTO pagoDTO) {
        System.out.println("ENTREEEEE2");
        System.out.println(pagoDTO.getId());

        // Recuperar el Pago existente desde la base de datos
        Pago pago = repository.findById(pagoDTO.getId())
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        pago.setBanco(pagoDTO.getBanco());
        pago.setNumCuenta(pagoDTO.getNumCuenta());
        pago.setValor(pagoDTO.getValor());

        // Guardar el pago actualizado en la base de datos
        pago = repository.save(pago);

        // Mapear el pago guardado de nuevo a DTO para devolver
        return modelMapper.map(pago, PagoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
