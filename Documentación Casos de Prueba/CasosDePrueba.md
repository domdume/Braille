# Documentación de Casos de Prueba

## 1. Introducción
Este documento consolida los casos de prueba unitarios localizados en la carpeta `src/test/java` del proyecto. El foco actual está en la lógica de dominio relacionada con la entidad `Traduccion`, pero la intención es ampliar progresivamente la cobertura a otras clases del modelo, servicios, validaciones y eventualmente integración. Todas las ejecuciones del corte actual han sido exitosas.

## 2. Alcance (corte actual)
- Tipo de pruebas: Unitarias (JUnit 5).
- Ámbito analizado: Comportamiento y validaciones de la entidad de dominio `Traduccion` y uso del enum `DireccionTraduccion`.
- Futuras ampliaciones previstas: Casos para servicios, controladores (sin lógica de negocio), validación de DTOs, manejo de errores y traducciones inversas más complejas.

## 3. Entorno de Ejecución
- Lenguaje: Java.
- Framework de pruebas: JUnit Jupiter (JUnit 5).
- Estructura: Código fuente bajo `src/main/java`, pruebas bajo `src/test/java`.
- Ejecución: Maven / IntelliJ IDEA.

## 4. Convenciones de Identificación
Prefijo sugerido para casos de dominio: `TC-DOM-<n>`.
Para futuras capas se propondrá: `TC-SRV-`, `TC-CTL-`, `TC-VIEW-`, `TC-INT-` (integración), manteniendo trazabilidad.

## 5. Resumen Ejecutivo de Resultados (estado al momento)
Todas las pruebas del dominio de traducción ejecutadas han pasado correctamente. No se registran fallos ni bloqueos en este corte. El detalle de casos se mantiene como referencia histórica y base para ampliación; los números pueden variar en futuros commits.

## 6. Casos de Prueba Detallados (Dominio Traducción)

### TC-DOM-001
- Nombre: Crear traducción válida
- Método: `debeCrearTraduccionValida`
- Objetivo: Verificar creación correcta de la entidad con estado inicial PENDIENTE.
- Precondiciones: Clase cargada; parámetros no nulos ni vacíos.
- Datos de entrada: texto="hola", dirección=ESPANOL_A_BRAILLE
- Pasos:
  1. Invocar `Traduccion.crear("hola", ESPANOL_A_BRAILLE)`.
- Resultado esperado:
  - Instancia no nula.
  - Texto original = "hola".
  - Dirección correctamente asignada.
  - Estado = PENDIENTE.
- Resultado obtenido: Conforme.
- Estado: Éxito.
- Evidencia: Asserts todos verdes.

### TC-DOM-002
- Nombre: Rechazar texto null
- Método: `debeLanzarExcepcionSiTextoEsNull`
- Objetivo: Validar que texto null produce `IllegalArgumentException`.
- Entrada: texto=null, dirección=ESPANOL_A_BRAILLE
- Resultado esperado: Excepción lanzada.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-003
- Nombre: Rechazar texto vacío/solo espacios
- Método: `debeLanzarExcepcionSiTextoEstaVacio`
- Entrada: texto="   "
- Resultado esperado: `IllegalArgumentException`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-004
- Nombre: Rechazar dirección null
- Método: `debeLanzarExcepcionSiDireccionEsNull`
- Entrada: dirección=null
- Resultado esperado: `IllegalArgumentException`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-005
- Nombre: Traducir texto simple Español→Braille
- Método: `debeTraducirTextoSimple`
- Entrada: "hola"
- Resultado esperado: Traducción no vacía, estado COMPLETADA.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-006 (Parametrizado - Palabras válidas)
- Nombre: Traducir palabras válidas Español→Braille
- Método: `debeTraducirPalabrasValidas`
- Entradas (`@ValueSource`): a, hola, mundo, café, niño
- Resultado esperado: Para cada palabra, traducción no nula y completada.
- Resultado obtenido: Todas correctas.
- Estado: Éxito.

### TC-DOM-007
- Nombre: Traducción exacta "hola"
- Método: `debeTraducirHolaExactamente`
- Entrada: "hola"
- Esperado: "⠓⠕⠇⠁"
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-008
- Nombre: Traducción exacta "hola mundo"
- Método: `debeTraducirHolaMundoExactamente`
- Entrada: "hola mundo"
- Esperado: "⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕"
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-009 (Parametrizado - Tabla Español→Braille)
- Nombre: Traducción exacta palabras específicas
- Método: `debeTraducirPalabrasEspanolABrailleExacto`
- Entradas (`@CsvSource`):
  - hola → ⠓⠕⠇⠁
  - mundo → ⠍⠥⠝⠙⠕
  - café → ⠉⠁⠋⠮ (nota: representa la tilde mediante símbolo adecuado según mapeo interno)
  - niño → ⠝⠊⠻⠕
  - a → ⠁
  - España → ⠠⠑⠎⠏⠁⠻⠁
- Resultado esperado: Coincidencia exacta de cada salida.
- Resultado obtenido: Todas correctas.
- Estado: Éxito.

### TC-DOM-010 (Parametrizado - Números)
- Nombre: Traducción con números embebidos
- Método: `debeTraducirTextoConNumeros`
- Entradas (`@CsvSource`):
  - 123 → ⠼⠁⠃⠉
  - hola 123 → ⠓⠕⠇⠁⠀⠼⠁⠃⠉
  - año 2024 → ⠁⠻⠕⠀⠼⠃⠚⠃⠙
- Resultado esperado: Prefijo numérico ⠼ seguido de dígitos correctos.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-011
- Nombre: Traducción Braille→Español exacta
- Método: `debeTraducirBrailleHola`
- Entrada: "⠓⠕⠇⠁"
- Esperado: "hola"
- Obtenido: Conforme.
- Estado: Éxito.

(El detalle completo anterior se conserva para trazabilidad; futuros casos se agregarán aquí manteniendo el mismo esquema.)

## 7. Análisis de Cobertura y Calidad (Dinámico)
Estado actual:
- Validaciones de creación correctas.
- Traducción Español→Braille: amplia para palabras y números.
- Traducción Braille→Español: mínima (solo un caso); requiere expansión.
Pendiente ampliar a: símbolos no válidos, puntuación, estados y comportamiento repetido.

## 8. Riesgos y Próximas Acciones
| Riesgo | Descripción | Severidad | Mitigación Propuesta |
|--------|-------------|-----------|----------------------|
| Decodificación limitada | Poca cobertura Braille→Español | Media | Agregar batería de casos invertidos parametrizados |
| Validación de símbolos | No se prueba rechazo de Braille inválido en dominio (solo observado en runtime) | Media | Añadir casos que confirmen `IllegalArgumentException` al ejecutar con caracteres no mapeados |
| Puntuación | Ausencia de pruebas con comas, puntos, interrogaciones | Baja | Extender mapa y pruebas |
| Persistencia futura | Cambios futuros podrían romper invariantes sin pruebas | Media | Añadir pruebas de invariantes antes de incorporar repositorios |

(La tabla de riesgos se mantiene vigente hasta nueva ampliación.)

## 9. Plan de Evolución de Pruebas
1. Ampliar Braille→Español con batería parametrizada.
2. Incorporar pruebas de error por caracteres inválidos.
3. Añadir pruebas de servicios (coordinación y reglas adicionales).
4. Verificar controladores: sólo delegación y validación superficial (sin lógica de negocio).
5. Añadir pruebas de DTO: mapeo y formatos.
6. Preparar pruebas de integración (cuando exista persistencia real / repositorios).
7. Métrica futura: cobertura por línea y mutación (pitest) para asegurar robustez de reglas.

## 10. Conclusión
El estado actual de las pruebas de dominio es sólido para los escenarios básicos de creación y traducción Español→Braille. El documento se mantendrá vivo y se actualizará en futuros commits al agregarse nuevas áreas (servicios, controladores, validaciones, errores). Se recomienda priorizar la ampliación de cobertura inversa (Braille→Español) y casos negativos.

## 11. Formato para Documentar Re-Ejecuciones
En caso de fallo futuro, agregar bajo el caso:
- Causa raíz.
- Ajuste aplicado (código / configuración).
- Evidencia de nueva ejecución exitosa.

Plantilla rápida para futuros fallos:
```
[Re-ejecución] TC-DOM-XYZ
Fallo original: <mensaje>
Causa raíz: <análisis>
Solución: <cambio>
Resultado tras corrección: Éxito / Nuevo fallo
Fecha re-ejecución: <fecha>
```

---
Documento mantenido de forma evolutiva. Próximas actualizaciones pueden reorganizar secciones para reflejar nuevas capas.
