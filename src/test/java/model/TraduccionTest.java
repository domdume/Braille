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
    @Nested
    @DisplayName("Traducción Español → Braille")
    class TraduccionEspanolABraille {

        @Test
        @DisplayName("Debe traducir texto simple en español")
        void debeTraducirTextoSimple() {
            // Arrange
            Traduccion traduccion = Traduccion.crear("hola", DireccionTraduccion.ESPANOL_A_BRAILLE);
            // Act
            traduccion.ejecutar();
            // Assert
            assertTrue(traduccion.estaCompletada());
            assertNotNull(traduccion.getTextoTraducido());
            assertFalse(traduccion.getTextoTraducido().isEmpty());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "hola", "mundo", "café", "niño"})
        @DisplayName("Debe traducir palabras válidas en español")
        void debeTraducirPalabrasValidas(String palabra) {
            // Arrange
            Traduccion traduccion = Traduccion.crear(palabra, DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertTrue(traduccion.estaCompletada());
            assertNotNull(traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir 'hola' exactamente a '⠓⠕⠇⠁'")
        void debeTraducirHolaExactamente() {
            // Arrange
            Traduccion traduccion = Traduccion.crear("hola", DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("⠓⠕⠇⠁", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir 'hola mundo' exactamente a '⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕'")
        void debeTraducirHolaMundoExactamente() {
            // Arrange
            Traduccion traduccion = Traduccion.crear("hola mundo", DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕", traduccion.getTextoTraducido());
        }

        @ParameterizedTest
        @CsvSource({
            "hola, ⠓⠕⠇⠁",
            "mundo, ⠍⠥⠝⠙⠕",
            "café, ⠉⠁⠋⠮",
            "niño, ⠝⠊⠻⠕",
            "a, ⠁",
            "España, ⠨⠑⠎⠏⠁⠻⠁"
        })
        @DisplayName("Debe traducir palabras españolas a Braille correctamente")
        void debeTraducirPalabrasEspanolABrailleExacto(String textoEspanol, String textoBrailleEsperado) {
            // Arrange
            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals(textoBrailleEsperado, traduccion.getTextoTraducido(),
                    "La traducción de '" + textoEspanol + "' no coincide");
        }

        @ParameterizedTest
        @CsvSource({
            "123, ⠼⠁⠃⠉",
            "hola 123, ⠓⠕⠇⠁⠀⠼⠁⠃⠉",
            "año 2024, ⠁⠻⠕⠀⠼⠃⠚⠃⠙"
        })
        @DisplayName("Debe traducir textos con números correctamente")
        void debeTraducirTextoConNumeros(String textoEspanol, String textoBrailleEsperado) {
            // Arrange
            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals(textoBrailleEsperado, traduccion.getTextoTraducido(),
                    "La traducción de '" + textoEspanol + "' con números no coincide");
        }

        @ParameterizedTest
        @CsvSource({
            "José, ⠨⠚⠕⠎⠮",
            "María, ⠨⠍⠁⠗⠌⠁",
            "bebé, ⠃⠑⠃⠮",
            "papá, ⠏⠁⠏⠷",
            "Ramón, ⠨⠗⠁⠍⠬⠝",
            "Raúl, ⠨⠗⠁⠾⠇",
            "pingüino, ⠏⠊⠝⠛⠳⠊⠝⠕"
        })
        @DisplayName("Debe traducir palabras con vocales acentuadas y caracteres especiales")
        void debeTraducirPalabrasConCaracteresEspeciales(String textoEspanol, String textoBrailleEsperado) {
            // Arrange
            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals(textoBrailleEsperado, traduccion.getTextoTraducido(),
                    "La traducción de '" + textoEspanol + "' con caracteres especiales no coincide");
        }

    }

    @Nested
    @DisplayName("Traducción Braille → Español")
    class TraduccionBrailleAEspanol {
        @Test
        @DisplayName("Debe traducir '⠓⠕⠇⠁' exactamente a 'hola'")
        void debeTraducirBrailleHola() {
            // Arrange
            String textoBraille = "⠓⠕⠇⠁";
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("hola", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir '⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕' a 'hola mundo'")
        void debeTraducirHolaMundo() {
            // Arrange
            String textoBraille = "⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕";
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("hola mundo", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir números correctamente: '⠼⠁⠃⠉' -> '123'")
        void debeTraducirNumeros() {
            // Arrange
            String textoBraille = "⠼⠁⠃⠉";
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("123", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir mayúsculas correctamente: '⠨⠓⠕⠇⠁' -> 'Hola'")
        void debeTraducirMayusculas() {
            // Arrange
            String textoBraille = "⠨⠓⠕⠇⠁";
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("Hola", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir texto todo en mayúsculas: '⠨⠑⠨⠎⠨⠏⠨⠁⠨⠻⠨⠁' -> 'ESPAÑA'")
        void debeTraducirTodoMayusculas() {
            // Arrange
            String textoBraille = "⠨⠑⠨⠎⠨⠏⠨⠁⠨⠻⠨⠁";
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("ESPAÑA", traduccion.getTextoTraducido());
        }

        @ParameterizedTest
        @CsvSource({
            "⠖⠨⠓⠕⠇⠁⠖, +Hola+",
            "⠣⠞⠑⠭⠞⠕⠜, (texto)",
            "⠼⠁⠚⠌⠼⠃, 10/2",
            "⠁⠤⠃, a_b"
        })
        @DisplayName("Debe traducir caracteres especiales y puntuación (ajustado a ambigüedad)")
        void debeTraducirCaracteresEspeciales(String textoBraille, String textoEspanolEsperado) {
            // Arrange
            Traduccion traduccion = Traduccion.crear(textoBraille, DireccionTraduccion.BRAILLE_A_ESPANOL);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals(textoEspanolEsperado, traduccion.getTextoTraducido());
        }
    }

    @Nested
    @DisplayName("Traducción Español → Braille Espejo")
    class TraduccionEspanolABrailleEspejo {

        @Test
        @DisplayName("Debe traducir 'a' a espejo correctamente")
        void debeTraducirAEspejo() {
            // Arrange: 'a' es ⠁ (punto 1). Espejo es punto 4 (⠈).
            String textoEspanol = "a";
            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE_ESPEJO);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("⠈", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir 'ab' a espejo invertido")
        void debeTraducirAbEspejo() {
            // Arrange: 'a' es ⠁ (1), 'b' es ⠃ (1-2).
            // Normal: ⠁⠃
            // Espejo chars: 'a'->⠈ (4), 'b'->⠘ (4-5).
            // Reverse string: ⠘⠈
            String textoEspanol = "ab";
            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE_ESPEJO);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("⠘⠈", traduccion.getTextoTraducido());
        }

        @Test
        @DisplayName("Debe traducir 'hola' a espejo invertido")
        void debeTraducirHolaEspejo() {
            // Arrange
            String textoEspanol = "hola";
            // h (1-2-5) -> espejo (4-5-2) -> ⠚ (j)
            // o (1-3-5) -> espejo (4-6-2) -> ⠪
            // l (1-2-3) -> espejo (4-5-6) -> ⠸
            // a (1)     -> espejo (4)     -> ⠈
            // Reverse: ⠈⠸⠪⠚

            Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE_ESPEJO);

            // Act
            traduccion.ejecutar();

            // Assert
            assertEquals("⠈⠸⠪⠚", traduccion.getTextoTraducido());
        }
    }

    @Test
    @DisplayName("Debe traducir el signo '=' correctamente a Braille")
    void debeTraducirSignoIgual() {
        // Arrange
        String textoOriginal = "=";
        DireccionTraduccion direccion = DireccionTraduccion.ESPANOL_A_BRAILLE;

        // Act
        Traduccion traduccion = Traduccion.crear(textoOriginal, direccion);
        traduccion.ejecutar();
        String resultado = traduccion.getTextoTraducido();

        // Assert
        assertEquals("⠶", resultado, "La traducción del signo '=' no coincide");
    }
    @Test
    void debeTraducirMayusculasYNumerosBien() {
        // Arrange
        String textoOriginal = "DOME-123";
        DireccionTraduccion direccion = DireccionTraduccion.ESPANOL_A_BRAILLE;

        // Act
        Traduccion traduccion = Traduccion.crear(textoOriginal, direccion);
        traduccion.ejecutar();
        String resultado = traduccion.getTextoTraducido();

        // Assert
        assertEquals("⠨⠙⠨⠕⠨⠍⠨⠑⠤⠼⠁⠃⠉", resultado, "La traducción del signo 'DOME-123' no coincide");
    }

    @Test
    @DisplayName("Debe preservar el salto de línea durante la traducción")
    void debePreservarSaltoDeLinea() {
        // Arrange
        String textoOriginal = "hola\nmundo";
        DireccionTraduccion direccion = DireccionTraduccion.ESPANOL_A_BRAILLE;

        // Act
        Traduccion traduccion = Traduccion.crear(textoOriginal, direccion);
        traduccion.ejecutar();
        String resultado = traduccion.getTextoTraducido();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("\n"), "La traducción debe conservar el salto de línea");

        // Si quieres validar la traducción exacta, usa esto:
        String esperado = "⠓⠕⠇⠁\n⠍⠥⠝⠙⠕";
        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Deve traducir signos matemáticos y operadores correctamente")
    void debeTraducirSignosMatematicos() {
        //Arrange
        String textoOriginal = "2+2=4";
        DireccionTraduccion direccion = DireccionTraduccion.ESPANOL_A_BRAILLE;

        //Act
        Traduccion traduccion = Traduccion.crear(textoOriginal, direccion);
        traduccion.ejecutar();
        String resultado = traduccion.getTextoTraducido();

        //Assert
        assertEquals("⠼⠃⠖⠼⠃⠶⠼⠙", resultado,
                "La traducción de operaciones matemáticas debe incluir signos +, = y números");
    }

    @ParameterizedTest
    @CsvSource({
            "¡Hola!, ⠖⠨⠓⠕⠇⠁⠖",
            "¿Cómo?, ⠢⠨⠉⠬⠍⠕⠢",
            "(texto), ⠣⠞⠑⠭⠞⠕⠜",
            "a-b, ⠁⠤⠃",
            "5*3, ⠼⠑⠦⠼⠉",
            "10/2, ⠼⠁⠚⠌⠼⠃"
    })
    @DisplayName("Debe traducir todos los caracteres especiales soportados")
    void debeTraducirCaracteresEspeciales(String textoEspanol, String textoBrailleEsperado) {
        // Arrange
        Traduccion traduccion = Traduccion.crear(textoEspanol, DireccionTraduccion.ESPANOL_A_BRAILLE);

        // Act
        traduccion.ejecutar();

        // Assert
        assertEquals(textoBrailleEsperado, traduccion.getTextoTraducido(),
                "La traducción de '" + textoEspanol + "' con caracteres especiales no coincide");
    }
 }