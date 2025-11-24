package servicio;

import utilidad.MapeadorBraille;

/* Servicio para manejar las traducciones*/
public class ServicioTraduccionBraille {
    
    /**
     * Traduce texto en español a Braille.
     * 
     * @param textoEspanol Texto en español a traducir
     * @return Texto traducido a Braille (usando caracteres Unicode Braille)
     */
    public String traducirEspanolABraille(String textoEspanol) {
        if (textoEspanol == null || textoEspanol.isEmpty()) {
            return "";
        }
        
        StringBuilder resultadoBraille = new StringBuilder();
        boolean enModoNumero = false;
        
        for (int i = 0; i < textoEspanol.length(); i++) {
            char caracterActual = textoEspanol.charAt(i);
            
            // Manejador de espacios
            if (caracterActual == ' ') {
                enModoNumero = false;
                resultadoBraille.append(MapeadorBraille.obtenerBrailleParaLetra(' '));
                continue;
            }
            
            // Manejador de numeros
            if (Character.isDigit(caracterActual)) {
                if (!enModoNumero) {
                    resultadoBraille.append(MapeadorBraille.obtenerSignoNumero());
                    enModoNumero = true;
                }
                resultadoBraille.append(MapeadorBraille.obtenerBrailleParaNumero(caracterActual));
                continue;
            }
            
            // Bandera -> Modo numero
            if (enModoNumero) {
                enModoNumero = false;
            }
            
            // Manejador puntuación
            String puntuacionBraille = MapeadorBraille.obtenerBrailleParaPuntuacion(caracterActual);
            if (puntuacionBraille != null) {
                resultadoBraille.append(puntuacionBraille);
                continue;
            }
            
            // Manejador mayúsculas
            if (Character.isUpperCase(caracterActual)) {
                resultadoBraille.append(MapeadorBraille.obtenerSignoMayuscula());
                caracterActual = Character.toLowerCase(caracterActual);
            }
            
            // Manejador letras
            String letraBraille = MapeadorBraille.obtenerBrailleParaLetra(caracterActual);
            if (letraBraille != null) {
                resultadoBraille.append(letraBraille);
            }
        }
        
        return resultadoBraille.toString();
    }
    
    /**
     * Traduce texto en Braille a español.
     * 
     * @param textoBraille Texto en Braille a traducir
     * @return Texto traducido a español
     */
    public String traducirBrailleAEspanol(String textoBraille) {
        if (textoBraille == null || textoBraille.isEmpty()) {
            return "";
        }
        
        StringBuilder resultadoEspanol = new StringBuilder();
        boolean siguienteMayuscula = false;
        boolean enModoNumero = false;
        
        for (int i = 0; i < textoBraille.length(); i++) {
            char caracterBraille = textoBraille.charAt(i);
            
            // Verificador mayúscula
            if (String.valueOf(caracterBraille).equals(MapeadorBraille.obtenerSignoMayuscula())) {
                siguienteMayuscula = true;
                continue;
            }
            
            // Verificador número
            if (String.valueOf(caracterBraille).equals(MapeadorBraille.obtenerSignoNumero())) {
                enModoNumero = true;
                continue;
            }
            
            // Espacio siempre sale del modo número
            Character letra = MapeadorBraille.obtenerLetraParaBraille(String.valueOf(caracterBraille));
            if (letra != null && letra == ' ') {
                resultadoEspanol.append(' ');
                enModoNumero = false;
                siguienteMayuscula = false;
                continue;
            }
            
            // Si estamos en modo número, tratar de convertir como número
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
            
            // Convertir letra
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
    
    /* Validacion de un texto */
    public boolean esTextoEspanolValido(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        // Verificacion de caracteres permitidos
        for (char c : texto.toCharArray()) {
            if (!MapeadorBraille.esCaracterSoportado(c)) {
                return false;
            }
        }
        
        return true;
    }
    
    /* Validacion del braille */
    public boolean esTextoBrailleValido(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        // Verificador rango Unicode de Braille
        for (char c : texto.toCharArray()) {
            if (!MapeadorBraille.esCaracterBraille(c)) {
                return false;
            }
        }
        
        return true;
    }
}
