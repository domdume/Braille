package dto;

/**
 * Estructura base para las solicitudes de traducción recibidas por el sistema.
 * 
 * Dto, la encargada de recibir y deserializar el JSON entrante al backend. 
 * Encapsula toda la información necesaria para procesar una traducción:
 * el texto a traducir, la dirección de conversión, etc.
 */
public class SolicitudTraduccion {
    
    /* Atributos */
    private String texto;
    private String direccion;
    
    /* Constructor vacío */
    public SolicitudTraduccion() {
    }
    
    /* Constructor con todos los atributos */
    public SolicitudTraduccion(String texto, String direccion) {
        this.texto = texto;
        this.direccion = direccion;
    }
    
    // --- Getters y Setters ---
    public String getTexto() {
        return texto;
    }
    
    /** Define el texto que se procesará en la traducción. */
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    /** Devuelve la dirección de traducción solicitada. */
    public String getDireccion() {
        return direccion;
    }
    
    /** Establece si la traducción será de texto a braille o de braille a texto. */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
