package dto;

/**
 * Data Transfer Object (DTO) para las solicitudes de traducción recibidas por el sistema.
 * 
 * <p>Esta clase es responsable de recibir y deserializar el JSON entrante desde el frontend.
 * Encapsula toda la información necesaria para procesar una solicitud de traducción,
 * incluyendo el texto a traducir y la dirección de conversión deseada.</p>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 * SolicitudTraduccion solicitud = new SolicitudTraduccion("Hola mundo", "ESPANOL_A_BRAILLE");
 * </pre>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 */
public class SolicitudTraduccion {

    /**
     * Texto que será traducido.
     * Puede ser texto en español o texto en Braille según la dirección especificada.
     */
    private String texto;
    
    /**
     * Dirección de la traducción.
     * Valores válidos: "ESPANOL_A_BRAILLE" o "BRAILLE_A_ESPANOL"
     */
    private String direccion;

    /**
     * Constructor por defecto sin parámetros.
     * Necesario para la deserialización JSON.
     */
    public SolicitudTraduccion() {
    }

    /**
     * Constructor completo con todos los atributos.
     * 
     * @param texto El texto a traducir
     * @param direccion La dirección de traducción (ESPANOL_A_BRAILLE o BRAILLE_A_ESPANOL)
     */
    public SolicitudTraduccion(String texto, String direccion) {
        this.texto = texto;
        this.direccion = direccion;
    }

    // --- Getters y Setters ---
    
    /**
     * Obtiene el texto que será traducido.
     * 
     * @return El texto a traducir
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto que se procesará en la traducción.
     * 
     * @param texto El texto a traducir
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene la dirección de traducción solicitada.
     * 
     * @return La dirección de traducción
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la traducción.
     * 
     * @param direccion La dirección (ESPANOL_A_BRAILLE o BRAILLE_A_ESPANOL)
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
