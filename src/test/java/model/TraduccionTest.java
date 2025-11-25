package model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Traduccion - Entidad de Dominio")
class TraduccionTest {
    @Test
    @DisplayName("Debe crear traducción válida con parámetros correctos")
    void debeCrearTraduccionValida() {
        // Arrange & Act
        Traduccion traduccion = Traduccion.crear("hola", DireccionTraduccion.ESPANOL_A_BRAILLE);

        // Assert
        assertNotNull(traduccion);
        assertEquals("hola", traduccion.getTextoOriginal());
        assertEquals(DireccionTraduccion.ESPANOL_A_BRAILLE, traduccion.getDireccion());
        assertEquals(Traduccion.EstadoTraduccion.PENDIENTE, traduccion.getEstado());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el texto es null")
    void debeLanzarExcepcionSiTextoEsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Traduccion.crear(null, DireccionTraduccion.ESPANOL_A_BRAILLE);
        });
    }
    @Test
    @DisplayName("Debe lanzar excepción si el texto está vacío")
    void debeLanzarExcepcionSiTextoEstaVacio() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Traduccion.crear("   ", DireccionTraduccion.ESPANOL_A_BRAILLE);
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción si la dirección es null")
    void debeLanzarExcepcionSiDireccionEsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Traduccion.crear("hola", null);
        });
    }
}