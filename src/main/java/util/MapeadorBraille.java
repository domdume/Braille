package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria que contiene los mapeos entre caracteres españoles y Braille.
 * Utiliza representación Unicode de Braille (U+2800 a U+28FF).
 */
public class MapeadorBraille {
    
    // Símbolo especial para indicar que el siguiente carácter es un número
    private static final String SIGNO_NUMERO = "⠼";
    
    // Símbolo para mayúsculas
    private static final String SIGNO_MAYUSCULA = "⠨";
    
    // Mapeo de letras minúsculas a Braille
    private static final Map<Character, String> LETRA_A_BRAILLE = new HashMap<>();
    
    // Mapeo de números a Braille (los números usan las letras a-j precedidas por signo de número)
    private static final Map<Character, String> NUMERO_A_BRAILLE = new HashMap<>();
    
    // Mapeo de signos de puntuación a Braille
    private static final Map<Character, String> PUNTUACION_A_BRAILLE = new HashMap<>();
    
    // Mapeos inversos para la traducción de Braille a español
    private static final Map<String, Character> BRAILLE_A_LETRA = new HashMap<>();
    private static final Map<String, Character> BRAILLE_A_NUMERO = new HashMap<>();
    private static final Map<String, Character> BRAILLE_A_PUNTUACION = new HashMap<>();
    
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
        PUNTUACION_A_BRAILLE.put('÷', "⠦");
        PUNTUACION_A_BRAILLE.put('x', "⠲");
        
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
    
    // Métodos públicos para acceder a los mapeos
    public static String obtenerBrailleParaLetra(char letra) {
        return LETRA_A_BRAILLE.get(Character.toLowerCase(letra));
    }
    
    public static String obtenerBrailleParaNumero(char numero) {
        return NUMERO_A_BRAILLE.get(numero);
    }
    
    public static String obtenerBrailleParaPuntuacion(char puntuacion) {
        return PUNTUACION_A_BRAILLE.get(puntuacion);
    }
    
    public static Character obtenerLetraParaBraille(String braille) {
        return BRAILLE_A_LETRA.get(braille);
    }
    
    public static Character obtenerNumeroParaBraille(String braille) {
        return BRAILLE_A_NUMERO.get(braille);
    }
    
    public static Character obtenerPuntuacionParaBraille(String braille) {
        return BRAILLE_A_PUNTUACION.get(braille);
    }
    
    public static String obtenerSignoNumero() {
        return SIGNO_NUMERO;
    }
    
    public static String obtenerSignoMayuscula() {
        return SIGNO_MAYUSCULA;
    }
    
    // Verificador de caracteres soportados
    public static boolean esCaracterSoportado(char c) {
        return LETRA_A_BRAILLE.containsKey(Character.toLowerCase(c)) ||
               NUMERO_A_BRAILLE.containsKey(c) ||
               PUNTUACION_A_BRAILLE.containsKey(c);
    }
    
    // Verificador de caracteres Braille
    public static boolean esCaracterBraille(char c) {
        return (c >= '\u2800' && c <= '\u28FF');
    }
}
