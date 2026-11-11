
package com.API.Empresa.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {

    @Test
    void MeuPrimeiroTeste() {

        int resultado = 10 + 5;

        assertEquals(15, resultado);
    }

    @Test
    void DeveSubtrairDoisNumeros() {

        int resultado = 20 - 5;

        assertEquals(15, resultado);
    }
}
