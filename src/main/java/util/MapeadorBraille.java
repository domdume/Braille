package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria que gestiona los mapeos bidireccionales entre caracteres españoles y Braille.
 *
 * <p>Esta clase implementa el patrón Utility Class con mapeos estáticos inmutables que
 * representan el sistema de escritura Braille español. Utiliza la representación Unicode
 * de Braille (rango U+2800 a U+28FF) definida en el estándar Unicode Braille Patterns.</p>
 *
 * <h2>Características del Sistema Braille Implementado</h2>
 * <ul>
 *   <li><b>Alfabeto español completo</b>: a-z incluyendo ñ</li>
 *   <li><b>Vocales acentuadas</b>: á, é, í, ó, ú, ü</li>
 *   <li><b>Números</b>: 0-9 (precedidos por signo numérico ⠼)</li>
 *   <li><b>Mayúsculas</b>: Indicadas con prefijo ⠠</li>
 *   <li><b>Puntuación</b>: . , ; : ? ! - ( ) "</li>
 *   <li><b>Espacio</b>: Representado con ⠀ (Braille blank pattern)</li>
 * </ul>
 *
 * <h2>Rango Unicode Braille</h2>
 * <p>Los caracteres Braille utilizan el bloque Unicode U+2800 a U+28FF (Braille Patterns).
 * Cada carácter representa una celda Braille de 6 puntos (2×3) donde cada punto puede
 * estar elevado o no, permitiendo 2^6 = 64 combinaciones posibles.</p>
 *
 * <h2>Símbolos Especiales</h2>
 * <ul>
 *   <li><b>Signo de número (⠼)</b>: Indica que los siguientes caracteres son números.
 *       En Braille, los números 1-0 usan los mismos patrones que las letras a-j,
 *       diferenciados solo por el prefijo ⠼.</li>
 *   <li><b>Signo de mayúscula (⠠)</b>: Indica que la siguiente letra es mayúscula.
 *       Solo afecta a UNA letra, para mayúsculas consecutivas se repite.</li>
 * </ul>
 *
 * <h2>Arquitectura de Mapeos</h2>
 * <p>La clase mantiene 6 mapas estáticos:</p>
 * <ul>
 *   <li><b>LETRA_A_BRAILLE</b>: Español minúsculas → Braille (HashMap)</li>
 *   <li><b>NUMERO_A_BRAILLE</b>: Dígitos → Braille (HashMap)</li>
 *   <li><b>PUNTUACION_A_BRAILLE</b>: Signos puntuación → Braille (HashMap)</li>
 *   <li><b>BRAILLE_A_LETRA</b>: Braille → Español minúsculas (HashMap)</li>
 *   <li><b>BRAILLE_A_NUMERO</b>: Braille → Dígitos (HashMap)</li>
 *   <li><b>BRAILLE_A_PUNTUACION</b>: Braille → Signos puntuación (HashMap)</li>
 * </ul>
 *
 * <p>Los mapeos inversos se generan automáticamente en el bloque static a partir
 * de los mapeos directos, garantizando consistencia bidireccional.</p>
 *
 * <h2>Ejemplo de Mapeos</h2>
 * <pre>{@code
 * Letra:        'h' → "⠓" (U+2813, puntos 1-2-5)
 * Número:       '5' → "⠑" (U+2811, puntos 1-5) + prefijo ⠼
 * Mayúscula:    'H' → "⠠⠓" (⠠ + ⠓)
 * Puntuación:   '?' → "⠢" (U+2822, puntos 2-6)
 * Espacio:      ' ' → "⠀" (U+2800, sin puntos elevados)
 * }</pre>
 *
 * <h2>Uso Típico</h2>
 * <pre>{@code
 * // Traducir español a Braille
 * String brailleH = MapeadorBraille.obtenerBrailleParaLetra('h'); // "⠓"
 * String braille5 = MapeadorBraille.obtenerBrailleParaNumero('5'); // "⠑"
 * String signoPrefijo = MapeadorBraille.obtenerSignoNumero(); // "⠼"
 *
 * // Traducir Braille a español
 * Character letra = MapeadorBraille.obtenerLetraParaBraille("⠓"); // 'h'
 * Character numero = MapeadorBraille.obtenerNumeroParaBraille("⠑"); // '5'
 *
 * // Validaciones
 * boolean soportado = MapeadorBraille.esCaracterSoportado('ñ'); // true
 * boolean esBraille = MapeadorBraille.esCaracterBraille('⠓'); // true
 * }</pre>
 *
 * <h2>Inmutabilidad y Thread Safety</h2>
 * <p>Todos los mapas son estáticos y se inicializan una sola vez en el bloque static.
 * No se proporcionan métodos para modificarlos, garantizando inmutabilidad. La clase
 * es thread-safe por diseño al no tener estado mutable.</p>
 *
 * <h2>Patrones de Diseño</h2>
 * <ul>
 *   <li><b>Utility Class</b>: Todos los miembros son estáticos, no se permite instanciación</li>
 *   <li><b>Flyweight implícito</b>: Los mapas estáticos comparten las instancias de String</li>
 *   <li><b>Bidirectional Mapping</b>: Mapeos duales para traducción en ambas direcciones</li>
 * </ul>
 *
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 * @see model.Traduccion
 */
public class MapeadorBraille {

    /**
     * Símbolo especial Braille (⠼) que indica que el siguiente carácter es un número.
     *
     * <p>En Braille, los números 1-9 y 0 utilizan los mismos patrones que las letras
     * a-j respectivamente. Para diferenciarlos, los números deben ser precedidos por
     * este signo numérico. El efecto persiste hasta encontrar un espacio o puntuación.</p>
     *
     * <p>Unicode: U+283C (puntos 3-4-5-6)</p>
     */
    private static final String SIGNO_NUMERO = "⠼";

    // Símbolo para mayúsculas (Punto 6 en sistema español)
    private static final String SIGNO_MAYUSCULA = "⠨";

    /**
     * Mapeo de letras minúsculas españolas a sus representaciones Braille Unicode.
     *
     * <p>Incluye:</p>
     * <ul>
     *   <li>Alfabeto básico: a-z</li>
     *   <li>Letra ñ (específica del español)</li>
     *   <li>Vocales acentuadas: á, é, í, ó, ú</li>
     *   <li>Vocal con diéresis: ü</li>
     *   <li>Espacio en blanco</li>
     * </ul>
     */
    private static final Map<Character, String> LETRA_A_BRAILLE = new HashMap<>();

    /**
     * Mapeo de dígitos (0-9) a sus representaciones Braille Unicode.
     *
     * <p>Los números en Braille usan los mismos patrones que las letras a-j:</p>
     * <ul>
     *   <li>1 = a, 2 = b, 3 = c, 4 = d, 5 = e</li>
     *   <li>6 = f, 7 = g, 8 = h, 9 = i, 0 = j</li>
     * </ul>
     * <p>Deben ser precedidos por {@link #SIGNO_NUMERO} para distinguirlos de letras.</p>
     */
    private static final Map<Character, String> NUMERO_A_BRAILLE = new HashMap<>();

    /**
     * Mapeo de signos de puntuación a sus representaciones Braille Unicode.
     *
     * <p>Incluye los signos más comunes:</p>
     * <ul>
     *   <li>Finales de oración: . ? !</li>
     *   <li>Separadores: , ; :</li>
     *   <li>Otros: - ( ) "</li>
     * </ul>
     */
    private static final Map<Character, String> PUNTUACION_A_BRAILLE = new HashMap<>();

    /**
     * Mapeo inverso: Braille Unicode a letras minúsculas españolas.
     *
     * <p>Generado automáticamente invirtiendo {@link #LETRA_A_BRAILLE}
     * en el bloque static de inicialización.</p>
     */
    private static final Map<String, Character> BRAILLE_A_LETRA = new HashMap<>();

    /**
     * Mapeo inverso: Braille Unicode a dígitos.
     *
     * <p>Generado automáticamente invirtiendo {@link #NUMERO_A_BRAILLE}
     * en el bloque static de inicialización.</p>
     */
    private static final Map<String, Character> BRAILLE_A_NUMERO = new HashMap<>();

    /**
     * Mapeo inverso: Braille Unicode a signos de puntuación.
     *
     * <p>Generado automáticamente invirtiendo {@link #PUNTUACION_A_BRAILLE}
     * en el bloque static de inicialización.</p>
     */
    private static final Map<String, Character> BRAILLE_A_PUNTUACION = new HashMap<>();

    /**
     * Inicialización estática de todos los mapeos Braille.
     *
     * <p>Este bloque se ejecuta una sola vez cuando la clase es cargada por
     * la JVM. Inicializa todos los mapas directos (español → Braille) y
     * luego genera automáticamente los mapas inversos (Braille → español).</p>
     *
     * <p>El orden de inicialización es:</p>
     * <ol>
     *   <li>Letras a-z y caracteres especiales españoles</li>
     *   <li>Números 0-9</li>
     *   <li>Signos de puntuación</li>
     *   <li>Generación de mapeos inversos</li>
     * </ol>
     */
    static {
        // Inicializar letras (a-z)
        LETRA_A_BRAILLE.put('a', "⠁");
        LETRA_A_BRAILLE.put('b', "⠃");
        LETRA_A_BRAILLE.put('c', "⠉");
        LETRA_A_BRAILLE.put('d', "⠙");
        LETRA_A_BRAILLE.put('e', "⠑");
        LETRA_A_BRAILLE.put('f', "⠋");
        LETRA_A_BRAILLE.put('g', "⠛");
        LETRA_A_BRAILLE.put('h', "⠓");
        LETRA_A_BRAILLE.put('i', "⠊");
        LETRA_A_BRAILLE.put('j', "⠚");
        LETRA_A_BRAILLE.put('k', "⠅");
        LETRA_A_BRAILLE.put('l', "⠇");
        LETRA_A_BRAILLE.put('m', "⠍");
        LETRA_A_BRAILLE.put('n', "⠝");
        LETRA_A_BRAILLE.put('ñ', "⠻");
        LETRA_A_BRAILLE.put('o', "⠕");
        LETRA_A_BRAILLE.put('p', "⠏");
        LETRA_A_BRAILLE.put('q', "⠟");
        LETRA_A_BRAILLE.put('r', "⠗");
        LETRA_A_BRAILLE.put('s', "⠎");
        LETRA_A_BRAILLE.put('t', "⠞");
        LETRA_A_BRAILLE.put('u', "⠥");
        LETRA_A_BRAILLE.put('v', "⠧");
        LETRA_A_BRAILLE.put('w', "⠺");
        LETRA_A_BRAILLE.put('x', "⠭");
        LETRA_A_BRAILLE.put('y', "⠽");
        LETRA_A_BRAILLE.put('z', "⠵");

        // Letras con acento
        LETRA_A_BRAILLE.put('á', "⠷");
        LETRA_A_BRAILLE.put('é', "⠮");
        LETRA_A_BRAILLE.put('í', "⠌");
        LETRA_A_BRAILLE.put('ó', "⠬");
        LETRA_A_BRAILLE.put('ú', "⠾");
        LETRA_A_BRAILLE.put('ü', "⠳");

        // Espacio
        LETRA_A_BRAILLE.put(' ', "⠀");

        // Números 
        NUMERO_A_BRAILLE.put('0', "⠚");
        NUMERO_A_BRAILLE.put('1', "⠁");
        NUMERO_A_BRAILLE.put('2', "⠃");
        NUMERO_A_BRAILLE.put('3', "⠉");
        NUMERO_A_BRAILLE.put('4', "⠙");
        NUMERO_A_BRAILLE.put('5', "⠑");
        NUMERO_A_BRAILLE.put('6', "⠋");
        NUMERO_A_BRAILLE.put('7', "⠛");
        NUMERO_A_BRAILLE.put('8', "⠓");
        NUMERO_A_BRAILLE.put('9', "⠊");

        // Puntuación
        PUNTUACION_A_BRAILLE.put('.', "⠄");
        PUNTUACION_A_BRAILLE.put(',', "⠂");
        PUNTUACION_A_BRAILLE.put(';', "⠆");
        PUNTUACION_A_BRAILLE.put(':', "⠒");
        PUNTUACION_A_BRAILLE.put('?', "⠢");
        PUNTUACION_A_BRAILLE.put('¿', "⠢");
        PUNTUACION_A_BRAILLE.put('!', "⠖");
        PUNTUACION_A_BRAILLE.put('-', "⠤");
        PUNTUACION_A_BRAILLE.put('(', "⠣");
        PUNTUACION_A_BRAILLE.put(')', "⠜");
        PUNTUACION_A_BRAILLE.put('"', "⠶");
        PUNTUACION_A_BRAILLE.put('_', "⠤");
        PUNTUACION_A_BRAILLE.put('+', "⠖");
        PUNTUACION_A_BRAILLE.put('=', "⠶");
        PUNTUACION_A_BRAILLE.put('÷', "⠲");
        PUNTUACION_A_BRAILLE.put('*', "⠦");
        PUNTUACION_A_BRAILLE.put('/', "⠌");
        PUNTUACION_A_BRAILLE.put('¡', "⠖");
        // Crear mapeos inversos
        for (Map.Entry<Character, String> entry : LETRA_A_BRAILLE.entrySet()) {
            BRAILLE_A_LETRA.put(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<Character, String> entry : NUMERO_A_BRAILLE.entrySet()) {
            BRAILLE_A_NUMERO.put(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<Character, String> entry : PUNTUACION_A_BRAILLE.entrySet()) {
            BRAILLE_A_PUNTUACION.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Obtiene la representación Braille de una letra española.
     *
     * <p>Convierte automáticamente a minúsculas antes de buscar. Para letras
     * mayúsculas, el código llamante debe agregar manualmente el prefijo
     * {@link #SIGNO_MAYUSCULA}.</p>
     *
     * @param letra carácter español (a-z, ñ, vocales acentuadas, espacio).
     *              Las mayúsculas se convierten automáticamente a minúsculas.
     * @return representación Braille Unicode, o null si el carácter no está mapeado
     */
    public static String obtenerBrailleParaLetra(char letra) {
        return LETRA_A_BRAILLE.get(Character.toLowerCase(letra));
    }

    /**
     * Obtiene la representación Braille de un dígito.
     *
     * <p>Retorna solo el patrón Braille del número. El código llamante es
     * responsable de agregar el prefijo {@link #SIGNO_NUMERO} cuando sea necesario.</p>
     *
     * @param numero dígito del 0 al 9
     * @return representación Braille Unicode del número, o null si no es un dígito
     */
    public static String obtenerBrailleParaNumero(char numero) {
        return NUMERO_A_BRAILLE.get(numero);
    }

    /**
     * Obtiene la representación Braille de un signo de puntuación.
     *
     * @param puntuacion signo de puntuación soportado (. , ; : ? ! - ( ) ")
     * @return representación Braille Unicode, o null si el signo no está mapeado
     */
    public static String obtenerBrailleParaPuntuacion(char puntuacion) {
        return PUNTUACION_A_BRAILLE.get(puntuacion);
    }

    /**
     * Obtiene el carácter español correspondiente a un patrón Braille de letra.
     *
     * <p>Realiza la traducción inversa: Braille → español. Solo retorna letras
     * minúsculas; el código llamante debe gestionar el signo de mayúscula.</p>
     *
     * @param braille representación Braille Unicode de una letra
     * @return carácter español minúscula, o null si el patrón no es una letra conocida
     */
    public static Character obtenerLetraParaBraille(String braille) {
        return BRAILLE_A_LETRA.get(braille);
    }

    /**
     * Obtiene el dígito correspondiente a un patrón Braille numérico.
     *
     * <p>El patrón Braille debe ser el carácter numérico sin el prefijo
     * {@link #SIGNO_NUMERO}. El código llamante debe gestionar el contexto
     * numérico activado por dicho prefijo.</p>
     *
     * @param braille representación Braille Unicode de un número
     * @return dígito del 0 al 9, o null si el patrón no es un número conocido
     */
    public static Character obtenerNumeroParaBraille(String braille) {
        return BRAILLE_A_NUMERO.get(braille);
    }

    /**
     * Obtiene el signo de puntuación correspondiente a un patrón Braille.
     *
     * @param braille representación Braille Unicode de un signo de puntuación
     * @return carácter de puntuación, o null si el patrón no es puntuación conocida
     */
    public static Character obtenerPuntuacionParaBraille(String braille) {
        return BRAILLE_A_PUNTUACION.get(braille);
    }

    /**
     * Obtiene el símbolo Braille que indica el inicio de un número.
     *
     * @return el signo numérico "⠼" (U+283C)
     */
    public static String obtenerSignoNumero() {
        return SIGNO_NUMERO;
    }

    /**
     * Obtiene el símbolo Braille que indica que la siguiente letra es mayúscula.
     *
     * @return el signo de mayúscula "⠠" (U+2820)
     */
    public static String obtenerSignoMayuscula() {
        return SIGNO_MAYUSCULA;
    }

    /**
     * Verifica si un carácter español está soportado por el sistema de traducción.
     *
     * <p>Un carácter está soportado si existe en alguno de los mapeos:
     * letras (incluyendo ñ y acentos), números o puntuación.</p>
     *
     * <p>Las mayúsculas se convierten a minúsculas para la verificación,
     * ya que todas las letras mayúsculas son soportadas mediante el prefijo ⠠.</p>
     *
     * @param c carácter a verificar
     * @return true si el carácter tiene mapeo definido, false en caso contrario
     */
    public static boolean esCaracterSoportado(char c) {
        return LETRA_A_BRAILLE.containsKey(Character.toLowerCase(c)) ||
                NUMERO_A_BRAILLE.containsKey(c) ||
                PUNTUACION_A_BRAILLE.containsKey(c);
    }

    /**
     * Verifica si un carácter pertenece al rango Unicode de Braille Patterns.
     *
     * <p>El rango Unicode de Braille es U+2800 a U+28FF (256 patrones posibles).
     * Esto incluye todos los patrones de 6 y 8 puntos definidos en el estándar Unicode.</p>
     *
     * <p>Esta verificación es útil para validar entrada de texto que debe ser
     * exclusivamente Braille, o para detectar si un texto contiene caracteres Braille.</p>
     *
     * @param c carácter a verificar
     * @return true si el carácter está en el rango U+2800-U+28FF, false en caso contrario
     */
    public static boolean esCaracterBraille(char c) {
        return (c >= '\u2800' && c <= '\u28FF');
    }

    public static String espejarBraille(String textoBraille) {
        if (textoBraille == null || textoBraille.isEmpty()) return textoBraille;
        String[] lineas = textoBraille.split("\n", -1);
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < lineas.length; i++) {
            StringBuilder lineaEspejada = new StringBuilder();
            for (char c : lineas[i].toCharArray()) {
                lineaEspejada.append(esCaracterBraille(c) ? espejarCaracter(c) : c);
            }
            resultado.append(lineaEspejada.reverse().toString());
            if (i < lineas.length - 1) resultado.append("\n");
        }
        return resultado.toString();
    }

    private static char espejarCaracter(char c) {
        int mask = c - '\u2800';
        int newMask = 0;
        if ((mask & 0x01) != 0) newMask |= 0x08;
        if ((mask & 0x02) != 0) newMask |= 0x10;
        if ((mask & 0x04) != 0) newMask |= 0x20;
        if ((mask & 0x08) != 0) newMask |= 0x01;
        if ((mask & 0x10) != 0) newMask |= 0x02;
        if ((mask & 0x20) != 0) newMask |= 0x04;
        if ((mask & 0x40) != 0) newMask |= 0x80;
        if ((mask & 0x80) != 0) newMask |= 0x40;
        return (char) ('\u2800' + newMask);
    }
}