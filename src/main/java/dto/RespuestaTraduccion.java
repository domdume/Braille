package dto;

/**
 * Data Transfer Object (DTO) para las respuestas de traducción generadas por el sistema.
 * 
 * <p>Esta clase encapsula toda la información que el backend enviará al frontend
 * después de procesar una solicitud de traducción. Soporta tanto respuestas exitosas
 * como respuestas con error.</p>
 * 
 * <p>Ejemplos de uso:</p>
 * <pre>
 * // Respuesta exitosa
 * RespuestaTraduccion respuesta = new RespuestaTraduccion("Hola", "⠓⠕⠇⠁", "ESPANOL_A_BRAILLE");
 * 
 * // Respuesta con error
 * RespuestaTraduccion error = new RespuestaTraduccion(false, "Texto vacío");
 * </pre>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 */
public class RespuestaTraduccion {

    /**
     * Indica si la traducción se completó exitosamente.
     */
    private boolean exito;
    
    /**
     * Texto original antes de la traducción.
     */
    private String textoOriginal;
    
    /**
     * Texto resultante después de la traducción.
     */
    private String textoTraducido;
    
    /**
     * Dirección en la que se realizó la traducción.
     */
    private String direccion;
    
    /**
     * Mensaje de error si la traducción falló.
     * Solo presente cuando exito = false.
     */
    private String error;

    /**
     * Constructor por defecto sin parámetros.
     * Necesario para la serialización JSON.
     */
    public RespuestaTraduccion() {
    }

    /**
     * Constructor para indicar únicamente el estado de éxito o fallo.
     * 
     * @param exito true si la operación fue exitosa, false en caso contrario
     */
    public RespuestaTraduccion(boolean exito) {
        this.exito = exito;
    }

    /**
     * Constructor para respuestas exitosas con información completa.
     * Automáticamente establece exito = true.
     * 
     * @param textoOriginal El texto antes de la traducción
     * @param textoTraducido El texto después de la traducción
     * @param direccion La dirección de traducción aplicada
     */
    public RespuestaTraduccion(String textoOriginal, String textoTraducido, String direccion) {
        this.exito = true;
        this.textoOriginal = textoOriginal;
        this.textoTraducido = textoTraducido;
        this.direccion = direccion;
    }

    /**
     * Constructor para respuestas con error.
     * 
     * @param exito Debe ser false para indicar error
     * @param error Mensaje descriptivo del error ocurrido
     */
    public RespuestaTraduccion(boolean exito, String error) {
        this.exito = exito;
        this.error = error;
    }

    // --- Getters y Setters ---

    /**
     * Verifica si la traducción fue exitosa.
     * 
     * @return true si la traducción se completó correctamente, false si hubo error
     */
    public boolean isExito() {
        return exito;
    }

    /**
     * Establece el estado de éxito de la traducción.
     * 
     * @param exito true para éxito, false para error
     */
    public void setExito(boolean exito) {
        this.exito = exito;
    }

    /**
     * Obtiene el texto original enviado por el usuario.
     * 
     * @return El texto antes de ser traducido
     */
    public String getTextoOriginal() {
        return textoOriginal;
    }

    /**
     * Establece el texto original para esta respuesta.
     * 
     * @param textoOriginal El texto antes de la traducción
     */
    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    /**
     * Obtiene el texto traducido generado por el sistema.
     * 
     * @return El texto después de la traducción
     */
    public String getTextoTraducido() {
        return textoTraducido;
    }

    /**
     * Establece el contenido traducido que se enviará al usuario.
     * 
     * @param textoTraducido El texto resultante de la traducción
     */
    public void setTextoTraducido(String textoTraducido) {
        this.textoTraducido = textoTraducido;
    }

    /**
     * Obtiene la dirección de traducción aplicada.
     * 
     * @return La dirección (ESPANOL_A_BRAILLE o BRAILLE_A_ESPANOL)
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de traducción.
     * 
     * @param direccion La dirección de traducción aplicada
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el mensaje de error si la traducción falló.
     * 
     * @return El mensaje de error, o null si no hubo error
     */
    public String getError() {
        return error;
    }

    /**
     * Establece el mensaje de error en caso de que la traducción falle.
     * 
     * @param error Descripción del error ocurrido
     */
    public void setError(String error) {
        this.error = error;
    }

}
