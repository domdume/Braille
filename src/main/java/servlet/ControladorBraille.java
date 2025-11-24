package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DireccionTraduccion;
import service.ServicioTraduccionBraille;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Controlador REST para el sistema de traducción Braille.
 * Expone endpoints para traducir texto.
 */
@WebServlet(name = "ControladorBraille", urlPatterns = {"/api/traducir"})
public class ControladorBraille extends HttpServlet {
    
    private ServicioTraduccionBraille servicioTraduccion;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.servicioTraduccion = new ServicioTraduccionBraille();
        this.gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        enviarRespuestaError(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint no encontrado");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        
        // POST /api/traducir - Realizar traducción
        if (req.getRequestURI().contains("/api/traducir")) {
            manejarTraduccion(req, resp);
        } else {
            enviarRespuestaError(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint no encontrado");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        enviarRespuestaError(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint no encontrado");
    }
    
//Manejador de las solicitudes de traducción
    private void manejarTraduccion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            StringBuilder cuerpoJson = new StringBuilder();
            String linea;
            BufferedReader reader = req.getReader();
            while ((linea = reader.readLine()) != null) {
                cuerpoJson.append(linea);
            }
            
            JsonObject jsonRequest = gson.fromJson(cuerpoJson.toString(), JsonObject.class);
            
            if (jsonRequest == null || !jsonRequest.has("texto") || !jsonRequest.has("direccion")) {
                enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    "Solicitud inválida. Se requiere 'texto' y 'direccion'");
                return;
            }
            
            String texto = jsonRequest.get("texto").getAsString();
            String direccionStr = jsonRequest.get("direccion").getAsString();
            
            if (texto == null || texto.trim().isEmpty()) {
                enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    "El texto no puede estar vacío");
                return;
            }
            
            // Validar dirección
            DireccionTraduccion direccion;
            try {
                direccion = DireccionTraduccion.valueOf(direccionStr);
            } catch (IllegalArgumentException e) {
                enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    "Dirección inválida. Use 'ESPANOL_A_BRAILLE' o 'BRAILLE_A_ESPANOL'");
                return;
            }
            
            // Realizar la traducción
            String resultado;
            
            if (direccion == DireccionTraduccion.ESPANOL_A_BRAILLE) {
                if (!servicioTraduccion.esTextoEspanolValido(texto)) {
                    enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST, 
                        "Texto en español inválido");
                    return;
                }
                resultado = servicioTraduccion.traducirEspanolABraille(texto);
            } else {
                if (!servicioTraduccion.esTextoBrailleValido(texto)) {
                    enviarRespuestaError(resp, HttpServletResponse.SC_BAD_REQUEST, 
                        "Texto en Braille inválido");
                    return;
                }
                resultado = servicioTraduccion.traducirBrailleAEspanol(texto);
            }
            
            // Preparar respuesta
            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("exito", true);
            respuesta.addProperty("textoOriginal", texto);
            respuesta.addProperty("textoTraducido", resultado);
            respuesta.addProperty("direccion", direccion.toString());
            
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(respuesta));
            
        } catch (Exception e) {
            enviarRespuestaError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error al procesar la traducción: " + e.getMessage());
        }
    }
    
    //Respuesta de error
    private void enviarRespuestaError(HttpServletResponse resp, int codigoEstado, String mensaje) 
            throws IOException {
        JsonObject error = new JsonObject();
        error.addProperty("exito", false);
        error.addProperty("error", mensaje);
        
        resp.setStatus(codigoEstado);
        resp.getWriter().write(gson.toJson(error));
    }
}
