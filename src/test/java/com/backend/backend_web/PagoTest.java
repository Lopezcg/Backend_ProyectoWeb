package com.backend.backend_web;

import org.junit.jupiter.api.Test;

import com.backend.backend_web.entity.Pago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PagoTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        long valor = 1000;
        String banco = "Banco XYZ";
        long numCuenta = 1234567890;
        Integer status = 0;

        // Act
        Pago pago = new Pago();
        pago.setId(id);
        pago.setValor(valor);
        pago.setBanco(banco);
        pago.setNumCuenta(numCuenta);
        pago.setStatus(status);

        // Assert
        assertNotNull(pago);
        assertEquals(id, pago.getId());
        assertEquals(valor, pago.getValor());
        assertEquals(banco, pago.getBanco());
        assertEquals(numCuenta, pago.getNumCuenta());
        assertEquals(status, pago.getStatus());
    }
}
