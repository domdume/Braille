package servlet;

import com.google.gson.Gson;
import dto.SolicitudTraduccion;
import dto.RespuestaTraduccion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ServicioTraduccionBraille;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Controlador REST para el sistema de traducción español-Braille.
 * 
 * <p>Este servlet implementa el patrón MVC (Model-View-Controller) actuando como
 * controlador que maneja las peticiones HTTP para el endpoint de traducción. Su
 * responsabilidad principal es coordinar la comunicación entre el cliente HTTP y
 * la capa de servicio.</p>
 * 
 * <h2>Arquitectura REST</h2>
 * <ul>
 *   <li><b>Endpoint</b>: {@code /api/traducir}</li>
 *   <li><b>Método HTTP</b>: POST (único método soportado para traducción)</li>
 *   <li><b>Content-Type</b>: application/json; charset=UTF-8</li>
 *   <li><b>Formato de entrada</b>: JSON → {@link SolicitudTraduccion}</li>
 *   <li><b>Formato de salida</b>: JSON → {@link RespuestaTraduccion}</li>
 * </ul>
 * 
 * <h2>Responsabilidades del Controlador (Patrón MVC)</h2>
 * <ul>
 *   <li>Recibir y procesar peticiones HTTP POST</li>
 *   <li>Deserializar cuerpo JSON a DTOs de entrada ({@link SolicitudTraduccion})</li>
 *   <li>Validar formato básico de la petición HTTP</li>
 *   <li>Delegar procesamiento de negocio al {@link ServicioTraduccionBraille}</li>
 *   <li>Serializar DTOs de respuesta ({@link RespuestaTraduccion}) a JSON</li>
 *   <li>Configurar códigos de estado HTTP apropiados (200, 400, 405, 500)</li>
 *   <li>Manejar errores de comunicación y formato</li>
 * </ul>
 * 
 * <p><b>IMPORTANTE</b>: Este controlador NO contiene lógica de negocio. Toda la lógica
 * de traducción reside en {@link model.Traduccion} (dominio) y la orquestación en
 * {@link ServicioTraduccionBraille} (servicio).</p>
 * 
 * <h2>Flujo de Procesamiento</h2>
 * <pre>{@code
 * 1. Cliente envía POST a /api/traducir con JSON
 * 2. Servlet lee cuerpo HTTP completo
 * 3. Deserializa JSON → SolicitudTraduccion (Gson)
 * 4. Valida que JSON no sea vacío o malformado
 * 5. Delega a servicioTraduccion.procesarTraduccion()
 * 6. Recibe RespuestaTraduccion del servicio
 * 7. Serializa RespuestaTraduccion → JSON (Gson)
 * 8. Configura código HTTP (200 OK o 400 Bad Request)
 * 9. Envía respuesta JSON al cliente
 * }</pre>
 * 
 * <h2>Formato de Request JSON</h2>
 * <pre>{@code
 * {
 *   "texto": "Hola mundo",
 *   "direccion": "ESPANOL_A_BRAILLE"
 * }
 * }</pre>
 * 
 * <h2>Formato de Response JSON (Éxito)</h2>
 * <pre>{@code
 * {
 *   "exito": true,
 *   "textoOriginal": "Hola mundo",
 *   "textoTraducido": "⠠⠓⠕⠇⠁ ⠍⠥⠝⠙⠕",
 *   "direccion": "ESPANOL_A_BRAILLE",
 *   "error": null
 * }
 * }</pre>
 * 
 * <h2>Formato de Response JSON (Error)</h2>
 * <pre>{@code
 * {
 *   "exito": false,
 *   "textoOriginal": null,
 *   "textoTraducido": null,
 *   "direccion": null,
 *   "error": "El texto no puede estar vacío"
 * }
 * }</pre>
 * 
 * <h2>Códigos de Estado HTTP</h2>
 * <ul>
 *   <li><b>200 OK</b>: Traducción exitosa</li>
 *   <li><b>400 Bad Request</b>: Error de validación o JSON malformado</li>
 *   <li><b>405 Method Not Allowed</b>: Método HTTP no soportado (GET, DELETE, etc.)</li>
 *   <li><b>500 Internal Server Error</b>: Error inesperado del servidor</li>
 * </ul>
 * 
 * <h2>Ejemplo de Petición con cURL</h2>
 * <pre>{@code
 * curl -X POST http://localhost:8080/Braille/api/traducir \
 *   -H "Content-Type: application/json" \
 *   -d '{"texto":"Hola mundo","direccion":"ESPANOL_A_BRAILLE"}'
 * }</pre>
 * 
 * <h2>Inyección de Dependencias</h2>
 * <p>El servlet utiliza inyección manual de dependencias en el método {@link #init()}.
 * Las dependencias son:</p>
 * <ul>
 *   <li>{@link ServicioTraduccionBraille} - Servicio de aplicación</li>
 *   <li>{@link Gson} - Biblioteca de serialización/deserialización JSON</li>
 * </ul>
 * 
 * <p>En una aplicación empresarial, se podría reemplazar con un framework de DI
 * como Spring, CDI o Guice.</p>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 * @see ServicioTraduccionBraille
 * @see SolicitudTraduccion
 * @see RespuestaTraduccion
 */
@WebServlet(name = "ControladorBraille", urlPatterns = {"/api/traducir"})
public class ControladorBraille extends HttpServlet {

    /** Servicio de aplicación para procesar traducciones */
    private ServicioTraduccionBraille servicioTraduccion;
    
    /** Biblioteca Gson para serialización/deserialización JSON */
    private Gson gson;

    /**
     * Inicializa el servlet y sus dependencias.
     * 
     * <p>Este método se ejecuta una sola vez cuando el servlet es cargado
     * por el contenedor de servlets (Tomcat). Realiza la inyección manual
     * de dependencias creando instancias de:</p>
     * <ul>
     *   <li>{@link ServicioTraduccionBraille} - Servicio de negocio</li>
     *   <li>{@link Gson} - Procesador JSON con configuración por defecto</li>
     * </ul>
     * 
     * @throws ServletException si ocurre un error durante la inicialización
     */
    @Override
    public void init() throws ServletException {
        super.init();
        // Inyección manual de dependencias (se puede mejorar con DI framework)
        this.servicioTraduccion = new ServicioTraduccionBraille();
        this.gson = new Gson();
    }

    /**
     * Maneja peticiones HTTP GET.
     * 
     * <p>El método GET no está soportado para este endpoint, ya que la traducción
     * requiere envío de datos en el cuerpo de la petición (JSON). Retorna un
     * código 405 Method Not Allowed.</p>
     * 
     * @param req objeto HttpServletRequest con la petición del cliente
     * @param resp objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        enviarRespuestaError(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Método GET no soportado. Use POST para traducir");
    }

    /**
     * Maneja peticiones HTTP POST para procesar traducciones.
     * 
     * <p>Este es el método principal del controlador. Procesa las peticiones de
     * traducción deserializando el JSON de entrada, delegando al servicio, y
     * serializando el resultado de vuelta a JSON.</p>
     * 
     * <h3>Proceso</h3>
     * <ol>
     *   <li>Configura headers de respuesta (Content-Type: application/json)</li>
     *   <li>Delega a {@link #manejarTraduccion(HttpServletRequest, HttpServletResponse)}</li>
     * </ol>
     * 
     * @param req objeto HttpServletRequest con la petición del cliente, debe contener
     *            JSON válido en el cuerpo
     * @param resp objeto HttpServletResponse para enviar la respuesta en formato JSON
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException si ocurre un error de I/O al leer la petición o escribir la respuesta
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        manejarTraduccion(req, resp);
    }

    /**
     * Maneja peticiones HTTP DELETE.
     * 
     * <p>El método DELETE no está soportado para este endpoint. Retorna un
     * código 405 Method Not Allowed.</p>
     * 
     * @param req objeto HttpServletRequest con la petición del cliente
     * @param resp objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        enviarRespuestaError(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Método DELETE no soportado");
    }

    /**
     * Configura headers estándar de respuesta HTTP para JSON con UTF-8.
     * 
     * <p>Establece los siguientes headers:</p>
     * <ul>
     *   <li><code>Content-Type: application/json; charset=UTF-8</code></li>
     *   <li><code>Character-Encoding: UTF-8</code></li>
     * </ul>
     * 
     * <p>UTF-8 es esencial para soportar caracteres Braille Unicode correctamente.</p>
     * 
     * @param resp objeto HttpServletResponse a configurar
     */
    private void configurarRespuesta(HttpServletResponse resp) {
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }

    /**
     * Maneja la petición de traducción aplicando el flujo completo de procesamiento.
     * 
     * <p>Este método implementa el patrón de flujo:</p>
     * <pre>Deserializar → Validar → Delegar → Serializar</pre>
     * 
     * <h3>Pasos Detallados</h3>
     * <ol>
     *   <li>Deserializar JSON a {@link SolicitudTraduccion} usando Gson</li>
     *   <li>Validar que el JSON no sea nulo o vacío (validación de formato)</li>
     *   <li>Delegar procesamiento a {@link ServicioTraduccionBraille#procesarTraduccion(SolicitudTraduccion)}</li>
     *   <li>Recibir {@link RespuestaTraduccion} del servicio</li>
     *   <li>Serializar respuesta a JSON y enviar al cliente</li>
     *   <li>En caso de excepción, capturar y enviar respuesta de error HTTP 500</li>
     * </ol>
     * 
     * <h3>Manejo de Errores</h3>
     * <ul>
     *   <li><b>JSON vacío o nulo</b>: HTTP 400 Bad Request</li>
     *   <li><b>Error de validación de negocio</b>: HTTP 400 Bad Request (manejado por servicio)</li>
     *   <li><b>Excepción no esperada</b>: HTTP 500 Internal Server Error</li>
     * </ul>
     * 
     * @param req objeto HttpServletRequest con el JSON de entrada en el cuerpo
     * @param resp objeto HttpServletResponse para enviar la respuesta JSON
     * @throws IOException si ocurre un error al leer o escribir datos
     */
    private void manejarTraduccion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            // 1. Deserializar DTO de entrada
            SolicitudTraduccion solicitud = deserializarSolicitud(req);

            // 2. Validación básica del controlador (solo formato)
            if (solicitud == null) {
                enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST,
                        "JSON inválido o vacío");
                return;
            }

            // 3. Delegar al servicio (inversión de dependencia)
            RespuestaTraduccion respuesta = servicioTraduccion.procesarTraduccion(solicitud);

            // 4. Serializar y enviar respuesta
            enviarRespuesta(resp, respuesta);

        } catch (Exception e) {
            // Manejo de errores no esperados
            enviarRespuestaError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * Deserializa el cuerpo de la petición HTTP (JSON) a un objeto {@link SolicitudTraduccion}.
     * 
     * <p>Lee el cuerpo completo de la petición línea por línea y utiliza Gson
     * para convertir el JSON a un POJO. Maneja el cierre automático del BufferedReader
     * mediante try-with-resources.</p>
     * 
     * <h3>Proceso de Lectura</h3>
     * <ol>
     *   <li>Obtiene BufferedReader del request</li>
     *   <li>Lee todas las líneas del cuerpo</li>
     *   <li>Concatena líneas en un StringBuilder</li>
     *   <li>Si el cuerpo está vacío, retorna null</li>
     *   <li>Deserializa JSON usando Gson</li>
     * </ol>
     * 
     * @param req objeto HttpServletRequest del cual leer el cuerpo JSON
     * @return objeto SolicitudTraduccion deserializado, o null si el cuerpo está vacío
     * @throws IOException si ocurre un error al leer el cuerpo de la petición
     */
    private SolicitudTraduccion deserializarSolicitud(HttpServletRequest req) throws IOException {
        StringBuilder cuerpoJson = new StringBuilder();
        String linea;

        try (BufferedReader reader = req.getReader()) {
            while ((linea = reader.readLine()) != null) {
                cuerpoJson.append(linea);
            }
        }

        if (cuerpoJson.length() == 0) {
            return null;
        }

        return gson.fromJson(cuerpoJson.toString(), SolicitudTraduccion.class);
    }

    /**
     * Envía una respuesta JSON al cliente, tanto para casos de éxito como de error de negocio.
     * 
     * <p>Determina automáticamente el código de estado HTTP basándose en el campo
     * {@code exito} de la respuesta:</p>
     * <ul>
     *   <li>Si {@code respuesta.isExito() == true}: HTTP 200 OK</li>
     *   <li>Si {@code respuesta.isExito() == false}: HTTP 400 Bad Request</li>
     * </ul>
     * 
     * <p>El objeto {@link RespuestaTraduccion} se serializa completamente a JSON,
     * incluyendo todos sus campos (exito, textoOriginal, textoTraducido, direccion, error).</p>
     * 
     * @param resp objeto HttpServletResponse para escribir la respuesta
     * @param respuesta objeto RespuestaTraduccion a serializar y enviar
     * @throws IOException si ocurre un error al escribir la respuesta
     */
    private void enviarRespuesta(HttpServletResponse resp, RespuestaTraduccion respuesta)
            throws IOException {
        // Determinar código HTTP según el resultado
        int codigoEstado = respuesta.isExito()
                ? HttpServletResponse.SC_OK
                : HttpServletResponse.SC_BAD_REQUEST;

        resp.setStatus(codigoEstado);
        resp.getWriter().write(gson.toJson(respuesta));
    }

    /**
     * Envía una respuesta de error HTTP con un código de estado específico.
     * 
     * <p>Crea un {@link RespuestaTraduccion} de error con {@code exito=false} y
     * el mensaje de error proporcionado, luego lo serializa a JSON.</p>
     * 
     * <p>Se utiliza para errores HTTP como:</p>
     * <ul>
     *   <li>400 Bad Request - JSON malformado</li>
     *   <li>405 Method Not Allowed - Método HTTP no soportado</li>
     *   <li>500 Internal Server Error - Error inesperado del servidor</li>
     * </ul>
     * 
     * @param resp objeto HttpServletResponse para escribir la respuesta de error
     * @param codigoEstado código de estado HTTP a establecer (400, 405, 500, etc.)
     * @param mensaje mensaje descriptivo del error que se incluirá en el JSON
     * @throws IOException si ocurre un error al escribir la respuesta
     */
    private void enviarRespuestaError(HttpServletResponse resp, int codigoEstado, String mensaje)
            throws IOException {
        RespuestaTraduccion error = new RespuestaTraduccion(false, mensaje);
        resp.setStatus(codigoEstado);
        resp.getWriter().write(gson.toJson(error));
    }
}
