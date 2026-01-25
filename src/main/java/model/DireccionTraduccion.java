package model;

/**
 * Enumeración que define las direcciones posibles de traducción en el sistema.
 * 
 * <p>Esta enum representa las dos direcciones de conversión soportadas:
 * de español a Braille o de Braille a español.</p>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 * DireccionTraduccion direccion = DireccionTraduccion.ESPANOL_A_BRAILLE;
 * if (direccion == DireccionTraduccion.BRAILLE_A_ESPANOL) {
 *     // Procesar traducción inversa
 * }
 * </pre>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 */
public enum DireccionTraduccion {
    /**
     * Traducción de texto en español a representación Braille.
     * Convierte caracteres alfabéticos, numéricos y de puntuación
     * a su equivalente en código Braille Unicode.
     */
    ESPANOL_A_BRAILLE,
    
    /**
     * Traducción de representación Braille a texto en español.
     * Convierte símbolos Braille Unicode a caracteres
     * alfabéticos, numéricos y de puntuación en español.
     */
    BRAILLE_A_ESPANOL,

    /**
     * Traducción de texto en español a representación Braille con efecto espejo.
     * Útil para la escritura manual con punzón y regleta (perforación de derecha a izquierda).
     */
    ESPANOL_A_BRAILLE_ESPEJO
}
