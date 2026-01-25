package model;

import util.MapeadorBraille;

/**
 * Entidad de dominio que representa una traducción Braille.
 *
 * <p>Esta clase encapsula toda la lógica de negocio relacionada con las traducciones,
 * implementando el patrón de dominio rico (Rich Domain Model). Es responsable de
 * validar, ejecutar y mantener el estado de las traducciones bidireccionales entre
 * español y Braille.</p>
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Validar textos de entrada según reglas de negocio</li>
 *   <li>Ejecutar la lógica de traducción en ambas direcciones</li>
 *   <li>Mantener el estado de una traducción (PENDIENTE, COMPLETADA, FALLIDA)</li>
 *   <li>Aplicar reglas específicas de codificación Braille</li>
 * </ul>
 *
 * <p>Reglas de negocio implementadas:</p>
 * <ul>
 *   <li>Números: Requieren signo especial (⠼) al inicio de secuencia numérica</li>
 *   <li>Mayúsculas: Requieren signo especial (⠠) antes del carácter</li>
 *   <li>Espacios: Desactivan el modo número</li>
 *   <li>Normalización: Espacios múltiples se reducen a uno solo</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 * Traduccion traduccion = Traduccion.crear("Hola 123", DireccionTraduccion.ESPANOL_A_BRAILLE);
 * traduccion.ejecutar();
 * String braille = traduccion.getTextoTraducido();
 * </pre>
 *
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 * @see DireccionTraduccion
 * @see MapeadorBraille
 */
public class Traduccion {

    /**
     * Texto original sin modificar.
     */
    private String textoOriginal;

    /**
     * Texto resultante después de la traducción.
     */
    private String textoTraducido;

    /**
     * Dirección en la que se realizará la traducción.
     */
    private DireccionTraduccion direccion;

    /**
     * Estado actual de la traducción.
     */
    private EstadoTraduccion estado;

    /**
     * Enumeración que define los posibles estados de una traducción.
     *
     * <ul>
     *   <li>PENDIENTE: La traducción ha sido creada pero no ejecutada</li>
     *   <li>COMPLETADA: La traducción se ejecutó exitosamente</li>
     *   <li>FALLIDA: La traducción falló durante su ejecución</li>
     * </ul>
     */
    public enum EstadoTraduccion {
        /** Estado inicial cuando se crea la traducción */
        PENDIENTE,
        /** Estado cuando la traducción se completó correctamente */
        COMPLETADA,
        /** Estado cuando la traducción falló por algún error */
        FALLIDA
    }

    /**
     * Constructor privado para forzar el uso de factory method.
     *
     * @param textoOriginal Texto a traducir
     * @param direccion Dirección de la traducción
     */
    private Traduccion(String textoOriginal, DireccionTraduccion direccion) {
        this.textoOriginal = textoOriginal;
        this.direccion = direccion;
        this.estado = EstadoTraduccion.PENDIENTE;
    }

    /**
     * Factory method para crear una nueva traducción.
     *
     * <p>Este método valida la entrada antes de crear la instancia,
     * garantizando que solo se creen traducciones con datos válidos.</p>
     *
     * @param textoOriginal Texto a traducir (no puede ser null ni vacío)
     * @param direccion Dirección de la traducción (no puede ser null)
     * @return Nueva instancia de Traduccion en estado PENDIENTE
     * @throws IllegalArgumentException si textoOriginal es null/vacío o direccion es null
     */
    public static Traduccion crear(String textoOriginal, DireccionTraduccion direccion) {
        validarEntrada(textoOriginal, direccion);
        return new Traduccion(textoOriginal, direccion);
    }

    /**
     * Valida que la entrada cumpla con las reglas de negocio básicas.
     *
     * @param texto Texto a validar
     * @param direccion Dirección a validar
     * @throws IllegalArgumentException si algún parámetro es inválido
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
     * Ejecuta la traducción aplicando todas las reglas de negocio.
     *
     * <p>Proceso de ejecución:</p>
     * <ol>
     *   <li>Limpia espacios al inicio y final del texto</li>
     *   <li>Normaliza espacios múltiples a un solo espacio</li>
     *   <li>Valida que todos los caracteres sean soportados</li>
     *   <li>Ejecuta la traducción según la dirección especificada</li>
     *   <li>Actualiza el estado a COMPLETADA o FALLIDA</li>
     * </ol>
     *
     * @throws IllegalStateException si la traducción ya fue ejecutada
     * @throws RuntimeException si ocurre un error durante la traducción
     */
    public void ejecutar() {
        if (this.estado == EstadoTraduccion.COMPLETADA) {
            throw new IllegalStateException("La traducción ya fue completada");
        }

        try {
            // Limpiar espacios al inicio y final (común cuando se copia/pega)
            String textoLimpio = textoOriginal.trim();

            // Normalizar espacios múltiples a un solo espacio, preservando saltos de línea
            // Reemplaza grupos de espacios, tabs, carriage return y form feed por un espacio, NO toca '\n'
            textoLimpio = textoLimpio.replaceAll("[ \\t\\f\\r]+", " ");

            // Validar según la dirección
            if (direccion == DireccionTraduccion.ESPANOL_A_BRAILLE) {
                validarTextoEspanol(textoLimpio);
                this.textoTraducido = traducirEspanolABraille(textoLimpio);
            } else if (direccion == DireccionTraduccion.ESPANOL_A_BRAILLE_ESPEJO) {
                validarTextoEspanol(textoLimpio);
                String brailleNormal = traducirEspanolABraille(textoLimpio);
                this.textoTraducido = MapeadorBraille.espejarBraille(brailleNormal);
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
     * Valida que el texto contenga solo caracteres soportados en español.
     *
     * <p>Caracteres soportados incluyen:</p>
     * <ul>
     *   <li>Letras: a-z, A-Z, ñ, Ñ</li>
     *   <li>Acentuadas: á, é, í, ó, ú, ü y sus mayúsculas</li>
     *   <li>Números: 0-9</li>
     *   <li>Puntuación: . , ; : ? ! - ( ) "</li>
     *   <li>Espacio</li>
     * </ul>
     *
     * @param texto Texto a validar
     * @throws IllegalArgumentException si el texto contiene caracteres no soportados
     */
    private void validarTextoEspanol(String texto) {
        for (char c : texto.toCharArray()) {
            // Permitir saltos de línea sin validación de mapeo
            if (c == '\n') continue;
            if (!util.MapeadorBraille.esCaracterSoportado(c)) {
                throw new IllegalArgumentException(
                        "El texto contiene caracteres no soportados: '" + c + "'"
                );
            }
        }
    }

    /**
     * Valida que el texto contenga solo caracteres Braille válidos.
     *
     * <p>Los caracteres Braille válidos están en el rango Unicode U+2800 a U+28FF.</p>
     *
     * @param texto Texto a validar
     * @throws IllegalArgumentException si el texto contiene caracteres no Braille
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
     * Traduce texto en español a representación Braille.
     *
     * <p>Reglas de conversión aplicadas:</p>
     * <ul>
     *   <li>Números: Se antecede ⠼ al inicio de una secuencia numérica</li>
     *   <li>Mayúsculas: Se antecede ⠠ antes de cada letra mayúscula</li>
     *   <li>Espacios: Desactivan el modo número y se representan con ⠀</li>
     *   <li>Puntuación: Cada signo tiene su representación específica</li>
     *   <li>Letras: Se convierten a su equivalente Braille manteniendo minúsculas</li>
     * </ul>
     *
     * @param textoEspanol Texto en español a traducir
     * @return Representación del texto en Braille Unicode
     */
    private String traducirEspanolABraille(String textoEspanol) {
        StringBuilder resultadoBraille = new StringBuilder();
        boolean enModoNumero = false;

        for (int i = 0; i < textoEspanol.length(); i++) {
            char caracterActual = textoEspanol.charAt(i);

            // Preservar saltos de línea y salir de modo numérico
            if (caracterActual == '\n') {
                resultadoBraille.append('\n');
                enModoNumero = false;
                continue;
            }

            // 1) Dígitos → activan modo numérico y usan prefijo de número
            if (Character.isDigit(caracterActual)) {
                if (!enModoNumero) {
                    resultadoBraille.append(util.MapeadorBraille.obtenerSignoNumero());
                    enModoNumero = true;
                }
                String numeroBraille = util.MapeadorBraille.obtenerBrailleParaNumero(caracterActual);
                resultadoBraille.append(numeroBraille != null ? numeroBraille : "?");
                continue;
            }

            // 2) Comas dentro del número NO rompen modo numérico
            if (enModoNumero && caracterActual == ',') {
                String comaBraille = util.MapeadorBraille.obtenerBrailleParaPuntuacion(caracterActual);
                resultadoBraille.append(comaBraille != null ? comaBraille : "?");
                continue;
            }

            // 3) Si aparece algo que NO es dígito ni coma → cerrar modo numérico
            if (enModoNumero && !Character.isDigit(caracterActual) && caracterActual != ',') {
                enModoNumero = false;
            }

            // Espacio: se mapea y de forma natural mantiene fuera del modo numérico
            if (caracterActual == ' ') {
                String espacioBraille = util.MapeadorBraille.obtenerBrailleParaLetra(' ');
                resultadoBraille.append(espacioBraille != null ? espacioBraille : "?");
                continue;
            }

            // 4) Mayúsculas: añadir signo de mayúscula y luego la letra en minúscula
            if (Character.isLetter(caracterActual) && Character.isUpperCase(caracterActual)) {
                resultadoBraille.append(util.MapeadorBraille.obtenerSignoMayuscula());
                char base = Character.toLowerCase(caracterActual);
                String letraBraille = util.MapeadorBraille.obtenerBrailleParaLetra(base);
                resultadoBraille.append(letraBraille != null ? letraBraille : "?");
                continue;
            }

            // 5) Resto de signos, minúsculas, acentos, espacio: intentar puntuación, si no letra; fallback '?'
            String puntuacionBraille = util.MapeadorBraille.obtenerBrailleParaPuntuacion(caracterActual);
            if (puntuacionBraille != null) {
                resultadoBraille.append(puntuacionBraille);
                continue;
            }

            String letraBraille = util.MapeadorBraille.obtenerBrailleParaLetra(caracterActual);
            resultadoBraille.append(letraBraille != null ? letraBraille : "?");
        }

        return resultadoBraille.toString();
    }

    /**
     * Traduce texto en Braille a español.
     *
     * <p>Reglas de conversión aplicadas:</p>
     * <ul>
     *   <li>Signo ⠼: Activa modo número para los siguientes símbolos</li>
     *   <li>Signo ⠠: Convierte la siguiente letra a mayúscula</li>
     *   <li>Espacios: Desactivan el modo número</li>
     *   <li>Puntuación: Se reconoce y convierte a su carácter ASCII</li>
     *   <li>Letras: Se convierten según el contexto (mayúscula o minúscula)</li>
     * </ul>
     *
     * @param textoBraille Texto en Braille Unicode a traducir
     * @return Representación del texto en español
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

    /**
     * Obtiene el texto original sin modificar.
     *
     * @return El texto original proporcionado al crear la traducción
     */
    public String getTextoOriginal() {
        return textoOriginal;
    }

    /**
     * Obtiene el texto traducido.
     *
     * <p>Este método solo debe llamarse después de ejecutar la traducción.
     * Si se intenta acceder al texto traducido antes de completar la traducción,
     * se lanzará una excepción.</p>
     *
     * @return El texto resultante de la traducción
     * @throws IllegalStateException si la traducción no ha sido completada
     */
    public String getTextoTraducido() {
        if (estado != EstadoTraduccion.COMPLETADA) {
            throw new IllegalStateException("La traducción no ha sido completada");
        }
        return textoTraducido;
    }

    /**
     * Obtiene la dirección de la traducción.
     *
     * @return La dirección (ESPANOL_A_BRAILLE o BRAILLE_A_ESPANOL)
     */
    public DireccionTraduccion getDireccion() {
        return direccion;
    }

    /**
     * Obtiene el estado actual de la traducción.
     *
     * @return El estado (PENDIENTE, COMPLETADA o FALLIDA)
     */
    public EstadoTraduccion getEstado() {
        return estado;
    }

    /**
     * Verifica si la traducción está completada exitosamente.
     *
     * @return true si el estado es COMPLETADA, false en caso contrario
     */
    public boolean estaCompletada() {
        return estado == EstadoTraduccion.COMPLETADA;
    }

    /**
     * Verifica si la traducción falló durante su ejecución.
     *
     * @return true si el estado es FALLIDA, false en caso contrario
     */
    public boolean haFallado() {
        return estado == EstadoTraduccion.FALLIDA;
    }
}