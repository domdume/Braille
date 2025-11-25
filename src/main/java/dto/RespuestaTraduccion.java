package dto;

/**
 * Repuesta generada por el sistema tras procesar una solicitud de traducción.
 *
 * En esta clase se encapsula toda la información que el backend enviará al frontend después de ejecutar 
 * la conversión, tanto en el caso de éxito como de error.
 */
public class RespuestaTraduccion {

    private boolean exito;
    private String textoOriginal;
    private String textoTraducido;
    private String direccion;
    private String error;

    /* Constructor vacío.*/
    public RespuestaTraduccion() {
    }

    /*Constructor = Cuando solo se indica si la operación fue exitosa o no.*/
    public RespuestaTraduccion(boolean exito) {
        this.exito = exito;
    }

    /*Constructor utilizado para respuestas exitosas.*/
    public RespuestaTraduccion(String textoOriginal, String textoTraducido, String direccion) {
        this.exito = true;
        this.textoOriginal = textoOriginal;
        this.textoTraducido = textoTraducido;
        this.direccion = direccion;
    }

    /* Constructor utilizado a respuestas con error.*/
    public RespuestaTraduccion(boolean exito, String error) {
        this.exito = exito;
        this.error = error;
    }

    // --- Getters y Setters ---

    /* Bandera para comprobar proceso exitoso*/
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    /* Devuelve el texto original enviado por el usuario. */
    public String getTextoOriginal() {
        return textoOriginal;
    }

    /* Establece el texto original para esta respuesta. */
    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    /* Devuelve el texto traducido generado por el sistema. */
    public String getTextoTraducido() {
        return textoTraducido;
    }

    /* Define el contenido traducido que se enviará al usuario. */
    public void setTextoTraducido(String textoTraducido) {
        this.textoTraducido = textoTraducido;
    }

    /* Indica la dirección de traducción aplicada. */
    public String getDireccion() {
        return direccion;
    }

    /* Establece si la traducción fue de texto a braille o viceversa. */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /* Devuelve el mensaje de error, si existe. */
    public String getError() {
        return error;
    }

    /* Define el mensaje de error en caso de que la traducción falle. */
    public void setError(String error) {
        this.error = error;
    }

}
