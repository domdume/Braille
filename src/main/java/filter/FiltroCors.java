package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filtro Servlet para gestionar la configuración CORS (Cross-Origin Resource Sharing).
 * 
 * <p>Este filtro intercepta todas las peticiones dirigidas a la API y configura
 * los encabezados HTTP necesarios para permitir solicitudes desde dominios externos.
 * Es esencial para que el frontend pueda comunicarse con el backend sin
 * restricciones de política de mismo origen.</p>
 * 
 * <p>Encabezados configurados:</p>
 * <ul>
 *   <li>Access-Control-Allow-Origin: Permite solicitudes desde cualquier origen</li>
 *   <li>Access-Control-Allow-Methods: Define métodos HTTP permitidos</li>
 *   <li>Access-Control-Allow-Headers: Especifica encabezados permitidos</li>
 *   <li>Access-Control-Max-Age: Tiempo de caché para solicitudes preflight</li>
 * </ul>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 * @see Filter
 */
@WebFilter(urlPatterns = { "/api/*" })
public class FiltroCors implements Filter {

    /**
     * Inicializa el filtro.
     * Puede ser utilizado para configuración inicial si es necesario.
     * 
     * @param filterConfig Configuración del filtro proporcionada por el contenedor
     * @throws ServletException Si ocurre un error durante la inicialización
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesaria
    }

    /**
     * Intercepta cada petición HTTP dirigida a la API y aplica los encabezados CORS.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Convierte la petición y respuesta a sus tipos HTTP específicos</li>
     *   <li>Configura todos los encabezados CORS necesarios</li>
     *   <li>Maneja peticiones OPTIONS (preflight) retornando 200 OK</li>
     *   <li>Permite que la cadena de filtros continúe para otras peticiones</li>
     * </ol>
     * 
     * @param request La petición del cliente
     * @param response La respuesta del servidor
     * @param chain La cadena de filtros a ejecutar
     * @throws IOException Si ocurre un error de I/O
     * @throws ServletException Si ocurre un error del servlet
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Configuración de los encabezados CORS
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // Manejo de preflight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}
