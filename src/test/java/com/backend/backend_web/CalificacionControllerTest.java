package com.backend.backend_web;
import com.backend.backend_web.controller.CalificacionController;
import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.service.CalificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CalificacionController.class)
public class CalificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalificacionService calificacionService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private CalificacionDTO calificacionDTO;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        calificacionDTO = new CalificacionDTO();
        calificacionDTO.setId(1L);
        calificacionDTO.setPuntuacion(5);
        // Completa los demás atributos necesarios
    }

    @Test
    public void createCalificacion_ReturnsCalificacionDTO() throws Exception {
        given(calificacionService.save(calificacionDTO)).willReturn(calificacionDTO);

        mockMvc.perform(post("/calificacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(calificacionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(calificacionDTO.getId()));
    }

    @Test
    public void readCalificacion_ReturnsCalificacionList() throws Exception {
        List<CalificacionDTO> allCalificaciones = Arrays.asList(calificacionDTO);
        given(calificacionService.get()).willReturn(allCalificaciones);

        mockMvc.perform(get("/calificacion")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(calificacionDTO.getId()));
    }

    @Test
    public void updateCalificacion_ReturnsUpdatedCalificacionDTO() throws Exception {
        given(calificacionService.update(calificacionDTO)).willReturn(calificacionDTO);

        mockMvc.perform(put("/calificacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(calificacionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(calificacionDTO.getId()));
    }

    // @Test
    // public void deleteCalificacion_ReturnsOk() throws Exception {
    //     Long calificacionId = 1L;
    //     doNothing().when(calificacionService).delete(calificacionId);

    //     mockMvc.perform(delete("/calificacion/{id}", calificacionId)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }


}