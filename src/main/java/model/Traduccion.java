package model;

import util.MapeadorBraille;

/**
 * Entidad de dominio que representa una traducción Braille.
 *
 * Esta clase encapsula toda la lógica de negocio relacionada con las traducciones,
 * aplicando el patrón de dominio rico (Rich Domain Model).
 *
 * Responsabilidades:
 * - Validar textos de entrada
 * - Ejecutar la lógica de traducción
 * - Mantener el estado de una traducción
 * - Aplicar reglas de negocio
 */
public class Traduccion {

    // Atributos del dominio
    private String textoOriginal;
    private String textoTraducido;
    private DireccionTraduccion direccion;
    private EstadoTraduccion estado;

    /**
     * Estado de una traducción en el sistema
     */
    public enum EstadoTraduccion {
        PENDIENTE,
        COMPLETADA,
        FALLIDA
    }

    // Constructor privado - usar factory methods
    private Traduccion(String textoOriginal, DireccionTraduccion direccion) {
        this.textoOriginal = textoOriginal;
        this.direccion = direccion;
        this.estado = EstadoTraduccion.PENDIENTE;
    }

    /**
     * Factory method para crear una nueva traducción.
     *
     * @param textoOriginal Texto a traducir
     * @param direccion Dirección de la traducción
     * @return Nueva instancia de Traduccion
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static Traduccion crear(String textoOriginal, DireccionTraduccion direccion) {
        validarEntrada(textoOriginal, direccion);
        return new Traduccion(textoOriginal, direccion);
    }

    /**
     * Valida que la entrada sea correcta según reglas de negocio.
     */
    private static void validarEntrada(String texto, DireccionTraduccion direccion) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }

        if (direccion == null) {
            throw new IllegalArgumentException("La dirección de traducción es obligatoria");
        }
    }

    /**
     * Ejecuta la traducción aplicando las reglas de negocio.
     *
     * @throws IllegalStateException si la traducción ya fue ejecutada
     */
    public void ejecutar() {
        if (this.estado == EstadoTraduccion.COMPLETADA) {
            throw new IllegalStateException("La traducción ya fue completada");
        }

        try {
            // Limpiar espacios al inicio y final (común cuando se copia/pega)
            String textoLimpio = textoOriginal.trim();

            // Validar según la dirección
            if (direccion == DireccionTraduccion.ESPANOL_A_BRAILLE) {
                validarTextoEspanol(textoLimpio);
                this.textoTraducido = traducirEspanolABraille(textoLimpio);
            } else {
                validarTextoBraille(textoLimpio);
                this.textoTraducido = traducirBrailleAEspanol(textoLimpio);
            }

            this.estado = EstadoTraduccion.COMPLETADA;

        } catch (Exception e) {
            this.estado = EstadoTraduccion.FALLIDA;
            throw new RuntimeException("Error al ejecutar la traducción: " + e.getMessage(), e);
        }
    }

    /**
     * Valida que el texto esté en español válido.
     */
    private void validarTextoEspanol(String texto) {
        for (char c : texto.toCharArray()) {
            if (!MapeadorBraille.esCaracterSoportado(c)) {
                throw new IllegalArgumentException(
                        "El texto contiene caracteres no soportados: '" + c + "'"
                );
            }
        }
    }

    /**
     * Valida que el texto esté en Braille válido.
     */
    private void validarTextoBraille(String texto) {
        for (char c : texto.toCharArray()) {
            if (!MapeadorBraille.esCaracterBraille(c)) {
                throw new IllegalArgumentException(
                        "El texto contiene caracteres que no son Braille válido"
                );
            }
        }
    }

    /**
     * Lógica de negocio: Traduce texto en español a Braille.
     *
     * Reglas de negocio:
     * - Los números requieren un signo especial de número antes
     * - Las mayúsculas requieren un signo especial de mayúscula antes
     * - Los espacios desactivan el modo número
     */
    private String traducirEspanolABraille(String textoEspanol) {
        StringBuilder resultadoBraille = new StringBuilder();
        boolean enModoNumero = false;

        for (int i = 0; i < textoEspanol.length(); i++) {
            char caracterActual = textoEspanol.charAt(i);

            // Regla: los espacios terminan el modo número
            if (caracterActual == ' ') {
                enModoNumero = false;
                resultadoBraille.append(MapeadorBraille.obtenerBrailleParaLetra(' '));
                continue;
            }

            // Regla: los números necesitan signo especial al inicio
            if (Character.isDigit(caracterActual)) {
                if (!enModoNumero) {
                    resultadoBraille.append(MapeadorBraille.obtenerSignoNumero());
                    enModoNumero = true;
                }
                resultadoBraille.append(MapeadorBraille.obtenerBrailleParaNumero(caracterActual));
                continue;
            }

            // Salir del modo número si no es un dígito
            if (enModoNumero) {
                enModoNumero = false;
            }

            // Regla: puntuación tiene representación especial
            String puntuacionBraille = MapeadorBraille.obtenerBrailleParaPuntuacion(caracterActual);
            if (puntuacionBraille != null) {
                resultadoBraille.append(puntuacionBraille);
                continue;
            }

            // Regla: las mayúsculas necesitan signo especial
            if (Character.isUpperCase(caracterActual)) {
                resultadoBraille.append(MapeadorBraille.obtenerSignoMayuscula());
                caracterActual = Character.toLowerCase(caracterActual);
            }

            // Traducir letra
            String letraBraille = MapeadorBraille.obtenerBrailleParaLetra(caracterActual);
            if (letraBraille != null) {
                resultadoBraille.append(letraBraille);
            }
        }

        return resultadoBraille.toString();
    }

    /**
     * Lógica de negocio: Traduce texto en Braille a español.
     *
     * Reglas de negocio:
     * - El signo de número activa el modo número
     * - El signo de mayúscula afecta solo al siguiente carácter
     * - Los espacios desactivan el modo número
     */
    private String traducirBrailleAEspanol(String textoBraille) {
        StringBuilder resultadoEspanol = new StringBuilder();
        boolean siguienteMayuscula = false;
        boolean enModoNumero = false;

        for (int i = 0; i < textoBraille.length(); i++) {
            char caracterBraille = textoBraille.charAt(i);

            // Regla: detectar signo de mayúscula
            if (String.valueOf(caracterBraille).equals(MapeadorBraille.obtenerSignoMayuscula())) {
                siguienteMayuscula = true;
                continue;
            }

            // Regla: detectar signo de número
            if (String.valueOf(caracterBraille).equals(MapeadorBraille.obtenerSignoNumero())) {
                enModoNumero = true;
                continue;
            }

            // Regla: el espacio termina el modo número
            Character letra = MapeadorBraille.obtenerLetraParaBraille(String.valueOf(caracterBraille));
            if (letra != null && letra == ' ') {
                resultadoEspanol.append(' ');
                enModoNumero = false;
                siguienteMayuscula = false;
                continue;
            }

            // Regla: si estamos en modo número, convertir como número
            if (enModoNumero) {
                Character digito = MapeadorBraille.obtenerNumeroParaBraille(String.valueOf(caracterBraille));
                if (digito != null) {
                    resultadoEspanol.append(digito);
                    continue;
                } else {
                    enModoNumero = false;
                }
            }

            // Intentar convertir puntuación
            Character puntuacion = MapeadorBraille.obtenerPuntuacionParaBraille(String.valueOf(caracterBraille));
            if (puntuacion != null) {
                resultadoEspanol.append(puntuacion);
                continue;
            }

            // Regla: aplicar mayúscula si está marcada
            if (letra != null) {
                if (siguienteMayuscula) {
                    resultadoEspanol.append(Character.toUpperCase(letra));
                    siguienteMayuscula = false;
                } else {
                    resultadoEspanol.append(letra);
                }
            }
        }

        return resultadoEspanol.toString();
    }

    // --- Getters (sin setters - inmutabilidad después de crear) ---

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public String getTextoTraducido() {
        if (estado != EstadoTraduccion.COMPLETADA) {
            throw new IllegalStateException("La traducción no ha sido completada");
        }
        return textoTraducido;
    }

    public DireccionTraduccion getDireccion() {
        return direccion;
    }

    public EstadoTraduccion getEstado() {
        return estado;
    }

    /**
     * Verifica si la traducción está completa.
     */
    public boolean estaCompletada() {
        return estado == EstadoTraduccion.COMPLETADA;
    }

    /**
     * Verifica si la traducción falló.
     */
    public boolean haFallado() {
        return estado == EstadoTraduccion.FALLIDA;
    }
}