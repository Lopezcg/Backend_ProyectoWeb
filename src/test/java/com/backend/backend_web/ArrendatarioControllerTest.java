package com.backend.backend_web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.backend.backend_web.controller.ArrendatarioController;
import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.service.ArrendatarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ArrendatarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArrendatarioService service;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArrendatarioController controller;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void testCreateArrendatario() throws Exception {
        Arrendatario inputArrendatario = new Arrendatario();
        ArrendatarioDTO resultDTO = new ArrendatarioDTO();
        when(service.save(any(Arrendatario.class))).thenReturn(inputArrendatario);
        when(modelMapper.map(any(Arrendatario.class), eq(ArrendatarioDTO.class))).thenReturn(resultDTO);

        mockMvc.perform(post("/arrendatario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(inputArrendatario)))
                .andExpect(status().isOk());
        
        verify(service, times(1)).save(any(Arrendatario.class));
    }

    
}
