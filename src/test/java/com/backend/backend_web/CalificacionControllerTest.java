package com.backend.backend_web;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.backend.backend_web.controller.CalificacionController;
import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.service.CalificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalificacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalificacionService service;

    @InjectMocks
    private CalificacionController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateCalificacion() throws Exception {
        CalificacionDTO calificacionDTO = new CalificacionDTO();
        // Configura valores para calificacionDTO
        when(service.save(any(CalificacionDTO.class))).thenReturn(calificacionDTO);

        mockMvc.perform(post("/calificacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calificacionDTO)))
                .andExpect(status().isOk());

        verify(service).save(any(CalificacionDTO.class));
    }

    @Test
    void testReadCalificacion() throws Exception {
        Iterable<CalificacionDTO> calificaciones = mock(Iterable.class); // Simula tu lista de calificaciones
        when(service.get()).thenReturn((List<CalificacionDTO>) calificaciones);

        mockMvc.perform(get("/calificacion")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).get();
    }

    @Test
    void testUpdateCalificacion() throws Exception {
        CalificacionDTO calificacionDTO = new CalificacionDTO();
        // Configura valores para calificacionDTO
        when(service.update(any(CalificacionDTO.class))).thenReturn(calificacionDTO);

        mockMvc.perform(put("/calificacion/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calificacionDTO)))
                .andExpect(status().isOk());

        verify(service).update(any(CalificacionDTO.class));
    }

    @Test
    void testDeleteCalificacion() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/calificacion/{id}", 1L))
                .andExpect(status().isOk());

        verify(service).delete(1L);
    }
}
