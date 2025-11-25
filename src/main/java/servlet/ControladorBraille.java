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
 * Controlador REST para el sistema de traducción Braille.
 *
 * Responsabilidades (patrón MVC):
 * - Recibir peticiones HTTP
 * - Deserializar DTOs de entrada
 * - Delegar procesamiento al servicio
 * - Serializar DTOs de respuesta
 * - Manejar errores HTTP
 *
 * NO contiene lógica de negocio ni acceso a datos.
 */
@WebServlet(name = "ControladorBraille", urlPatterns = {"/api/traducir"})
public class ControladorBraille extends HttpServlet {

    private ServicioTraduccionBraille servicioTraduccion;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inyección manual de dependencias (se puede mejorar con DI framework)
        this.servicioTraduccion = new ServicioTraduccionBraille();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        enviarRespuestaError(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Método GET no soportado. Use POST para traducir");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        manejarTraduccion(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarRespuesta(resp);
        enviarRespuestaError(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Método DELETE no soportado");
    }

    /**
     * Configura headers de respuesta HTTP.
     */
    private void configurarRespuesta(HttpServletResponse resp) {
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }

    /**
     * Maneja la petición de traducción.
     * Aplica el flujo: deserializar -> delegar -> serializar
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
     * Deserializa el cuerpo JSON a un DTO de solicitud.
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
     * Envía una respuesta exitosa o con error de negocio.
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
     * Envía una respuesta de error HTTP.
     */
    private void enviarRespuestaError(HttpServletResponse resp, int codigoEstado, String mensaje)
            throws IOException {
        RespuestaTraduccion error = new RespuestaTraduccion(false, mensaje);
        resp.setStatus(codigoEstado);
        resp.getWriter().write(gson.toJson(error));
    }
}
