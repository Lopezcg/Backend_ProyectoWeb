package com.backend.backend_web;

import com.backend.backend_web.dto.PagoDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class pagoDTOTest {

    @Test
    void givenPagoDTO_whenSetValues_thenCorrectlyRetrieved() {
        PagoDTO pago = new PagoDTO();
        pago.setId(1L);
        pago.setValor(1000L);
        pago.setBanco("Banco Prueba");
        pago.setNumCuenta(123456789L);

        assertAll("PagoDTO",
                () -> assertEquals(1L, pago.getId()),
                () -> assertEquals(1000L, pago.getValor()),
                () -> assertEquals("Banco Prueba", pago.getBanco()),
                () -> assertEquals(123456789L, pago.getNumCuenta())
        );
    }

    @Test
    void givenPagoDTO_whenConstructedWithAllArgs_thenCorrectlyRetrieved() {
        PagoDTO pago = new PagoDTO(1L, 1000L, "Banco Prueba", 123456789L);

        assertAll("PagoDTO using AllArgsConstructor",
                () -> assertEquals(1L, pago.getId()),
                () -> assertEquals(1000L, pago.getValor()),
                () -> assertEquals("Banco Prueba", pago.getBanco()),
                () -> assertEquals(123456789L, pago.getNumCuenta())
        );
    }
}
