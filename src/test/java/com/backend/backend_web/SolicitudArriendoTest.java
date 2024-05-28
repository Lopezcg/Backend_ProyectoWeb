package com.backend.backend_web;

import org.junit.jupiter.api.Test;

import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.entity.SolicitudArriendo;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// class SolicitudArriendoTest {

//     @Test
// //     void testConstructorAndGettersSetters() {
// //         // Arrange
// //         Long id = 1L;
// //         LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
// //         LocalDate fechaFin = LocalDate.of(2022, 1, 15);
// //         int cantidadPersonas = 3;
// //         boolean estado = true;
// //         Propiedad propiedad = new Propiedad();
// //         Arrendatario arrendatario = new Arrendatario();
// //         Pago pago = new Pago();
// //         List<Calificacion> calificacion = (List<Calificacion>) new Calificacion();
// //         Integer status = 0;

// //         // Act
// //         SolicitudArriendo solicitudArriendo = new SolicitudArriendo();
// //         solicitudArriendo.setId(id);
// //         solicitudArriendo.setFechainicio(fechaInicio);
// //         solicitudArriendo.setFechafin(fechaFin);
// //         solicitudArriendo.setCantidadPersonas(cantidadPersonas);
// //         solicitudArriendo.setEstado(estado);
// //         solicitudArriendo.setPropiedad(propiedad);
// //         solicitudArriendo.setArrendatario(arrendatario);
// //         solicitudArriendo.setPago(pago);
// //         solicitudArriendo.setCalificacion(calificacion);
// //         solicitudArriendo.setStatus(status);

// //         // Assert
// //         assertNotNull(solicitudArriendo);
// //         assertEquals(id, solicitudArriendo.getId());
// //         assertEquals(fechaInicio, solicitudArriendo.getFechainicio());
// //         assertEquals(fechaFin, solicitudArriendo.getFechafin());
// //         assertEquals(cantidadPersonas, solicitudArriendo.getCantidadPersonas());
// //         assertEquals(estado, solicitudArriendo.isEstado());
// //         assertEquals(propiedad, solicitudArriendo.getPropiedad());
// //         assertEquals(arrendatario, solicitudArriendo.getArrendatario());
// //         assertEquals(pago, solicitudArriendo.getPago());
// //         assertEquals(calificacion, solicitudArriendo.getCalificacion());
// //         assertEquals(status, solicitudArriendo.getStatus());
// //     }
// // }
