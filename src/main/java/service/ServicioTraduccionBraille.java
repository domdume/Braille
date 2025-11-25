package service;

import dto.SolicitudTraduccion;
import dto.RespuestaTraduccion;
import model.DireccionTraduccion;
import model.Traduccion;

/**
 * Servicio de aplicación para gestionar traducciones Braille.
 *
 * Responsabilidades:
 * - Orquestar operaciones de traducción
 * - Convertir entre DTOs y entidades de dominio
 * - Coordinar la capa de dominio con la capa de presentación
 *
 * Este servicio NO contiene lógica de negocio, solo orquestación.
 * La lógica de negocio reside en la clase de dominio Traduccion.
 */
public class ServicioTraduccionBraille {

    /**
     * Procesa una solicitud de traducción completa.
     *
     * @param solicitud DTO con los datos de entrada
     * @return DTO con el resultado de la traducción
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
     * Valida que la solicitud contenga todos los datos necesarios.
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
     * Convierte la dirección de String a enum.
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