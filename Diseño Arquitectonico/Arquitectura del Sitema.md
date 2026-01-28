# Diseño Arquitectónico de Alto Nivel

## 1. Propósito
Este documento describe la arquitectura de alto nivel del Sistema Traductor Braille. Proporciona una visión clara de sus componentes, capas, patrones de diseño aplicados y flujo principal de traducción, sirviendo como base para la evolución futura (persistencia, integración externa, auditoría, seguridad).

## 2. Alcance
Incluye:
- Estructura lógica (paquetes y responsabilidades).
- Diagramas de componentes, clases y secuencia (PlantUML).
- Patrones de diseño aplicados.
- Descripción del flujo de petición/respuesta de traducción.

## 3. Visión General de la Arquitectura
La aplicación adopta un estilo MVC reforzado con una 
capa de Servicio que desacopla la interfaz 
web (Servlet/JSP) de la lógica de dominio. El 
modelo contiene una Entidad de Dominio (`Traduccion`) que encapsula reglas y estados. Los DTO (`SolicitudTraduccion`, `RespuestaTraduccion`) aíslan la capa de presentación del dominio y facilitan evolución sin romper contratos.

Capas actuales:
- Presentación (JSP + recursos estáticos).
- Controlador Web (Servlet + Filtro CORS).
- Servicio de Aplicación (orquestación + DTOs).
- Dominio (Entidad, Enums, Utilidades de mapeo Braille).
- Infraestructura mínima (configuración web.xml, librerías).

## 4. Diagramas (PlantUML)
Los diagramas fuente se ubican en esta misma carpeta:
- `componentes.puml` – Vista de componentes y relaciones.
- `clases.puml` – Vista de clases lógicas.
- `secuencia.puml` – Flujo de petición de traducción.
``

## 5. Componentes Principales
| Componente | Paquete | Rol | Responsabilidad Clave |
|------------|---------|-----|-----------------------|
| index.jsp | webapp | Vista | Renderiza interfaz y dispara peticiones AJAX/POST. Sin lógica de negocio. |
| ControladorBraille (Servlet) | servlet | Controlador | Recibe solicitudes, valida datos básicos, construye DTO y delega al servicio. |
| FiltroCors | filter | Filtro | Gestiona encabezados CORS para permitir acceso desde clientes. |
| ServicioTraduccionBraille | service | Servicio | Orquesta la operación de traducción, crea entidad de dominio, maneja estados y retorna DTO respuesta. |
| SolicitudTraduccion | dto | DTO Entrada | Transporta texto y dirección solicitada sin lógica. |
| RespuestaTraduccion | dto | DTO Salida | Transporta resultado, estado y mensajes. |
| Traduccion | model | Entidad de Dominio | Encapsula validaciones, estado (PENDIENTE, COMPLETADA, ERROR) y ejecución de la traducción. |
| DireccionTraduccion (Enum) | model | Selector | Define orientación ESPAÑOL_A_BRAILLE / BRAILLE_A_ESPANOL y condiciona reglas. |
| MapeadorBraille | util | Utilidad | Provee mapeos símbolo↔caracter, validaciones y soporte para números/mayúsculas. |

## 6. Patrones de Diseño Utilizados
1. Service Layer: Separación estricta de presentación, coordinación y dominio.
2. Rich Domain Model: `Traduccion` conserva lógica y estados evitando Anemia.
3. DTO (Data Transfer Object): Intercambio desacoplado entre capas externas y servicio.
4. Dependency Inversion: El Servlet depende del servicio (interfaz/comportamiento), evitando acoplarse a detalles del dominio interno.
5. Facade / Orchestrator: El Servicio actúa como fachada que simplifica interacciones hacia el dominio.
6. Utility Pattern: `MapeadorBraille` concentra lógica de mapeo simbólico reutilizable.
7. Enum Strategy Selector Light: `DireccionTraduccion` direcciona la lógica de ejecución sin ramificaciones dispersas.
## 7. Diagrama de Capas y Módulos
Las capas se organizan para asegurar que:
- La Vista nunca accede directamente a clases de dominio o utilidades.
- El Controlador no implementa reglas de traducción, solo delega.
- El Servicio coordina, aplicando reglas de aplicación (no de presentación) y traduciendo estados a DTO.
- El Dominio es autosuficiente para validar y ejecutar la traducción.
Ver `clases.puml` y `componentes.puml` para representación visual.

## 8. Flujo de Traducción (Resumen)
1. Usuario ingresa texto y dirección.
2. `index.jsp` envía POST al Servlet.
3. Servlet valida (no nulo/vacío, dirección válida) y crea `SolicitudTraduccion`.
4. Servicio construye `Traduccion` y ejecuta `ejecutar()`.
5. La entidad consulta `MapeadorBraille` para traducir cada símbolo según la dirección.
6. La entidad actualiza su estado a COMPLETADA o ERROR según validaciones.
7. Servicio genera `RespuestaTraduccion` y la retorna al Servlet.
8. Servlet responde JSON; la vista actualiza la interfaz.

## 9. Estados y Ciclo de Vida de Traducción
- PENDIENTE: Tras creación vía fábrica `Traduccion.crear()`.
- COMPLETADA: Después de ejecución exitosa.
- ERROR: Si se detectan caracteres inválidos / reglas violadas.

## 10. Decisiones Arquitectónicas Clave
| ID | Decisión | Justificación | Alternativas | Impacto Futuro |
|----|----------|---------------|--------------|----------------|
| ARQ-01 | Servicio intermedio | Desacoplar UI de reglas y permitir evolución de dominio | Servlet llamando dominio directo | Facilita añadir seguridad, logging, cache |
| ARQ-02 | Dominio rico | Centralizar reglas y validaciones | Lógica en servicio / util | Mayor coherencia e invariantes claras |
| ARQ-03 | DTOs separados | Evitar exposición directa de entidad | Usar entidad en UI | Estabilidad de contrato mientras dominio evoluciona |
| ARQ-04 | Utilidad mapeo | Reuso y mantenimiento único | Mapear en la entidad | Facilita ampliar tablas y soportar localización |
| ARQ-05 | Enum dirección | Simplicidad estructural | Strategy por clases | Fácil de extender a más direcciones |

## 11. Consideraciones de Seguridad y Calidad (Futuras)
- Validación robusta de entrada (longitudes máximas, caracteres prohibidos).
- Sanitización de salida ante futuros HTML enriquecido.
- Registro de auditoría de traducciones (persistencia pendiente).
- Tests adicionales de Braille inverso, mutación y rendimiento (listas largas).

## 12. Extensibilidad
Estrategias previstas:
- Añadir adaptadores de persistencia (Repositorio) manteniendo el dominio intacto.
- Incluir nuevas direcciones (Inglés↔Braille) expandiendo `DireccionTraduccion` y tablas del `MapeadorBraille`.
- Integrar API REST pura con JSON (el Servlet podría delegar a un controlador REST adicional).

## 13. Trazabilidad
Este documento se actualizará con cada cambio sustantivo de arquitectura. Las modificaciones de dominio se vincularán a IDs ARQ para mantener historial.

## 14. Referencias
- Diagramas PlantUML en esta carpeta.
- Pruebas unitarias iniciales en `src/test/java` (ver documento de casos de prueba).

