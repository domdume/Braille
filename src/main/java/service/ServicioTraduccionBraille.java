package service;

import dto.SolicitudTraduccion;
import dto.RespuestaTraduccion;
import model.DireccionTraduccion;
import model.Traduccion;

/**
 * Servicio de aplicación para gestionar traducciones entre español y Braille.
 * 
 * <p>Esta clase actúa como orquestador entre la capa de presentación y la capa de dominio,
 * coordinando el flujo de datos sin implementar lógica de negocio. Siguiendo los principios
 * de Domain-Driven Design (DDD), toda la lógica de traducción reside en el modelo de dominio
 * {@link Traduccion}.</p>
 * 
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Orquestar operaciones de traducción entre capas</li>
 *   <li>Convertir entre DTOs de transferencia y entidades de dominio</li>
 *   <li>Validar datos de entrada antes de delegar al dominio</li>
 *   <li>Manejar excepciones y convertirlas en respuestas apropiadas</li>
 *   <li>Coordinar la capa de dominio con la capa de presentación</li>
 * </ul>
 * 
 * <h2>Patrón de Arquitectura</h2>
 * <p>Este servicio implementa el patrón Application Service, que se caracteriza por:</p>
 * <ul>
 *   <li><b>Sin lógica de negocio</b>: La lógica reside en {@link Traduccion}</li>
 *   <li><b>Orquestación pura</b>: Coordina llamadas entre capas</li>
 *   <li><b>Conversión de datos</b>: Transforma DTOs ↔ entidades de dominio</li>
 *   <li><b>Manejo de transacciones</b>: (En este caso no hay persistencia activa)</li>
 * </ul>
 * 
 * <h2>Flujo de Procesamiento</h2>
 * <pre>{@code
 * 1. Recibe SolicitudTraduccion (DTO)
 * 2. Valida datos de entrada
 * 3. Convierte dirección String → Enum
 * 4. Crea instancia de Traduccion (dominio)
 * 5. Ejecuta traducción (delegación al dominio)
 * 6. Convierte resultado a RespuestaTraduccion (DTO)
 * 7. Maneja excepciones y retorna respuesta
 * }</pre>
 * 
 * <h2>Ejemplo de Uso</h2>
 * <pre>{@code
 * ServicioTraduccionBraille servicio = new ServicioTraduccionBraille();
 * SolicitudTraduccion solicitud = new SolicitudTraduccion();
 * solicitud.setTexto("Hola mundo");
 * solicitud.setDireccion("ESPANOL_A_BRAILLE");
 * 
 * RespuestaTraduccion respuesta = servicio.procesarTraduccion(solicitud);
 * if (respuesta.isExito()) {
 *     System.out.println("Traducción: " + respuesta.getTextoTraducido());
 * } else {
 *     System.err.println("Error: " + respuesta.getError());
 * }
 * }</pre>
 * 
 * @author Sistema de Traducción Braille
 * @version 1.0
 * @since 1.0
 * @see Traduccion
 * @see SolicitudTraduccion
 * @see RespuestaTraduccion
 */
public class ServicioTraduccionBraille {

    /**
     * Procesa una solicitud de traducción completa de principio a fin.
     * 
     * <p>Este método es el punto de entrada principal del servicio. Coordina
     * todo el flujo de traducción desde la validación de entrada hasta la
     * generación de la respuesta final.</p>
     * 
     * <p>El procesamiento incluye:</p>
     * <ol>
     *   <li>Validación exhaustiva de la solicitud</li>
     *   <li>Conversión de la dirección de String a enum</li>
     *   <li>Creación de la entidad de dominio {@link Traduccion}</li>
     *   <li>Ejecución de la traducción (delegada al dominio)</li>
     *   <li>Construcción del DTO de respuesta</li>
     *   <li>Manejo de excepciones con mensajes descriptivos</li>
     * </ol>
     * 
     * <h3>Manejo de Errores</h3>
     * <ul>
     *   <li><b>Errores de validación</b>: Retorna RespuestaTraduccion con exito=false
     *       y mensaje específico del error de validación</li>
     *   <li><b>Errores de dominio</b>: Captura excepciones del modelo de dominio
     *       y las convierte en respuestas de error</li>
     *   <li><b>Errores inesperados</b>: Captura cualquier excepción no prevista
     *       y retorna mensaje genérico de error</li>
     * </ul>
     *
     * @param solicitud DTO con los datos de entrada (texto y dirección de traducción).
     *                  No puede ser null ni contener datos vacíos.
     * @return DTO con el resultado de la traducción. Si es exitosa, contiene
     *         textoOriginal, textoTraducido y direccion. Si hay error, contiene
     *         exito=false y mensaje de error descriptivo.
     * @throws NullPointerException si solicitud es null (capturado internamente
     *         y convertido a RespuestaTraduccion de error)
     */
    public RespuestaTraduccion procesarTraduccion(SolicitudTraduccion solicitud) {
        try {
            // Validar DTO de entrada
            validarSolicitud(solicitud);

            // Convertir dirección de String a Enum
            DireccionTraduccion direccion = parsearDireccion(solicitud.getDireccion());

            // Crear y ejecutar la traducción (dominio)
            Traduccion traduccion = Traduccion.crear(solicitud.getTexto(), direccion);
            traduccion.ejecutar();

            // Convertir resultado del dominio a DTO de respuesta
            return new RespuestaTraduccion(
                    traduccion.getTextoOriginal(),
                    traduccion.getTextoTraducido(),
                    traduccion.getDireccion().toString()
            );

        } catch (IllegalArgumentException e) {
            // Error de validación
            return new RespuestaTraduccion(false, e.getMessage());

        } catch (Exception e) {
            // Error inesperado
            return new RespuestaTraduccion(false, "Error al procesar la traducción: " + e.getMessage());
        }
    }

    /**
     * Valida que la solicitud de traducción contenga todos los datos necesarios y válidos.
     * 
     * <p>Realiza las siguientes validaciones:</p>
     * <ul>
     *   <li>La solicitud no es null</li>
     *   <li>El texto no es null ni está vacío (después de trim)</li>
     *   <li>La dirección no es null ni está vacía (después de trim)</li>
     * </ul>
     * 
     * <p>Esta validación se ejecuta antes de cualquier procesamiento de dominio
     * para garantizar que los datos de entrada sean consistentes y seguros.</p>
     * 
     * @param solicitud DTO de entrada a validar
     * @throws IllegalArgumentException si alguna validación falla, con un mensaje
     *         específico indicando qué campo es inválido
     */
    private void validarSolicitud(SolicitudTraduccion solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        if (solicitud.getTexto() == null || solicitud.getTexto().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }

        if (solicitud.getDireccion() == null || solicitud.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
    }

    /**
     * Convierte la dirección de traducción de String a enum {@link DireccionTraduccion}.
     * 
     * <p>Este método actúa como adaptador entre la representación de texto de la
     * dirección (recibida del cliente HTTP) y la representación interna como enum
     * utilizada por el modelo de dominio.</p>
     * 
     * <p>Valores válidos de entrada:</p>
     * <ul>
     *   <li><code>"ESPANOL_A_BRAILLE"</code> → {@link DireccionTraduccion#ESPANOL_A_BRAILLE}</li>
     *   <li><code>"BRAILLE_A_ESPANOL"</code> → {@link DireccionTraduccion#BRAILLE_A_ESPANOL}</li>
     * </ul>
     * 
     * <p>La conversión distingue mayúsculas/minúsculas y debe coincidir exactamente
     * con los nombres de los valores del enum.</p>
     * 
     * @param direccionStr representación en texto de la dirección, debe coincidir
     *                     exactamente con uno de los valores del enum
     * @return el valor enum correspondiente a la cadena de entrada
     * @throws IllegalArgumentException si direccionStr no coincide con ningún valor
     *         válido del enum DireccionTraduccion. El mensaje de error incluye los
     *         valores aceptados.
     */
    private DireccionTraduccion parsearDireccion(String direccionStr) {
        try {
            return DireccionTraduccion.valueOf(direccionStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Dirección inválida. Use 'ESPANOL_A_BRAILLE' o 'BRAILLE_A_ESPANOL'"
            );
        }
    }
}