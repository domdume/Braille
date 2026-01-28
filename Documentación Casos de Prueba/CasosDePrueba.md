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
- Nombre: Crear traducción válida con parámetros correctos
- Método: `debeCrearTraduccionValida`
- Objetivo: Verificar creación correcta de la entidad con estado inicial PENDIENTE y atributos asignados.
- Precondiciones: N/A.
- Datos de entrada: texto="hola", dirección=ESPANOL_A_BRAILLE.
- Pasos:
  1. Invocar `Traduccion.crear("hola", ESPANOL_A_BRAILLE)`.
- Resultado esperado:
  - Instancia no es `null`.
  - Texto original = "hola".
  - Dirección = `ESPANOL_A_BRAILLE`.
  - Estado = `PENDIENTE`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-002
- Nombre: Rechazar texto null
- Método: `debeLanzarExcepcionSiTextoEsNull`
- Objetivo: Validar que texto null produce `IllegalArgumentException`.
- Entrada: texto=null, dirección=ESPANOL_A_BRAILLE.
- Resultado esperado: `IllegalArgumentException`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-003
- Nombre: Rechazar texto vacío/solo espacios
- Método: `debeLanzarExcepcionSiTextoEstaVacio`
- Objetivo: Validar que texto vacío o espacios produce excepción.
- Entrada: texto="   ".
- Resultado esperado: `IllegalArgumentException`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-004
- Nombre: Rechazar dirección null
- Método: `debeLanzarExcepcionSiDireccionEsNull`
- Objetivo: Validar que dirección nula produce excepción.
- Entrada: dirección=null.
- Resultado esperado: `IllegalArgumentException`.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-005
- Nombre: Traducir texto simple Español→Braille
- Método: `TraduccionEspanolABraille.debeTraducirTextoSimple`
- Entrada: "hola".
- Resultado esperado: Traducción completada, texto traducido no nulo ni vacío.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-006 (Parametrizado - Palabras válidas)
- Nombre: Traducir palabras válidas Español→Braille
- Método: `TraduccionEspanolABraille.debeTraducirPalabrasValidas`
- Entradas (`@ValueSource`): a, hola, mundo, café, niño.
- Resultado esperado: Para cada palabra, traducción completada y resultado no nulo.
- Resultado obtenido: Todas correctas.
- Estado: Éxito.

### TC-DOM-007
- Nombre: Traducción exacta "hola"
- Método: `TraduccionEspanolABraille.debeTraducirHolaExactamente`
- Entrada: "hola".
- Esperado: "⠓⠕⠇⠁".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-008
- Nombre: Traducción exacta "hola mundo"
- Método: `TraduccionEspanolABraille.debeTraducirHolaMundoExactamente`
- Entrada: "hola mundo".
- Esperado: "⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-009 (Parametrizado - Tabla Español→Braille)
- Nombre: Traducción exacta palabras y frases específicas
- Método: `TraduccionEspanolABraille.debeTraducirPalabrasEspanolABrailleExacto`
- Entradas (`@CsvSource`):
  - hola → ⠓⠕⠇⠁
  - mundo → ⠍⠥⠝⠙⠕
  - café → ⠉⠁⠋⠮
  - niño → ⠝⠊⠻⠕
  - a → ⠁
  - España → ⠨⠑⠎⠏⠁⠻⠁
- Resultado esperado: Coincidencia exacta de cada salida Braille.
- Resultado obtenido: Todas correctas.
- Estado: Éxito.

### TC-DOM-010 (Parametrizado - Números)
- Nombre: Traducción con números embebidos
- Método: `TraduccionEspanolABraille.debeTraducirTextoConNumeros`
- Entradas (`@CsvSource`):
  - 123 → ⠼⠁⠃⠉
  - hola 123 → ⠓⠕⠇⠁⠀⠼⠁⠃⠉
  - año 2024 → ⠁⠻⠕⠀⠼⠃⠚⠃⠙
- Resultado esperado: Prefijo numérico ⠼ seguido de dígitos correctos.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-011 (Parametrizado - Caracteres Especiales)
- Nombre: Traducción palabras con acentos y especiales
- Método: `TraduccionEspanolABraille.debeTraducirPalabrasConCaracteresEspeciales`
- Entradas variadas: José, María, bebé, papá, Ramón, Raúl, pingüino.
- Resultado esperado: Mapeo correcto de tildes (⠮, ⠌, ⠷, ⠬, ⠾) y diéresis (⠳).
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-012
- Nombre: Traducción Braille→Español exacta simple
- Método: `TraduccionBrailleAEspanol.debeTraducirBrailleHola`
- Entrada: "⠓⠕⠇⠁".
- Esperado: "hola".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-013
- Nombre: Traducción Braille→Español frase con espacio
- Método: `TraduccionBrailleAEspanol.debeTraducirHolaMundo`
- Entrada: "⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕".
- Esperado: "hola mundo".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-014
- Nombre: Traducción Braille→Español números
- Método: `TraduccionBrailleAEspanol.debeTraducirNumeros`
- Entrada: "⠼⠁⠃⠉".
- Esperado: "123".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-015
- Nombre: Traducción Braille→Español mayúscula simple
- Método: `TraduccionBrailleAEspanol.debeTraducirMayusculas`
- Entrada: "⠨⠓⠕⠇⠁".
- Esperado: "Hola".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-016
- Nombre: Traducción Braille→Español todo mayúsculas
- Método: `TraduccionBrailleAEspanol.debeTraducirTodoMayusculas`
- Entrada: "⠨⠑⠨⠎⠨⠏⠨⠁⠨⠻⠨⠁".
- Esperado: "ESPAÑA".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-017 (Parametrizado - Braille a Especiales)
- Nombre: Traducción Braille→Español caracteres especiales
- Método: `TraduccionBrailleAEspanol.debeTraducirCaracteresEspeciales`
- Entradas: Signos de interrogación, paréntesis, operaciones matemáticas.
- Resultado esperado: Coincidencia con texto español (ej: "+Hola+", "(texto)").
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-018
- Nombre: Traducción a Espejo - Carácter simple
- Método: `TraduccionEspanolABrailleEspejo.debeTraducirAEspejo`
- Entrada: "a".
- Esperado: "⠈" (Espejo de punto 1 es punto 4).
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-019
- Nombre: Traducción a Espejo - Inversión cadena
- Método: `TraduccionEspanolABrailleEspejo.debeTraducirAbEspejo`
- Entrada: "ab".
- Esperado: "⠘⠈" (Cadena invertida y puntos espejados).
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-020
- Nombre: Traducción a Espejo - Palabra completa
- Método: `TraduccionEspanolABrailleEspejo.debeTraducirHolaEspejo`
- Entrada: "hola".
- Esperado: "⠈⠸⠪⠚".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-021
- Nombre: Traducción signo igual
- Método: `debeTraducirSignoIgual`
- Entrada: "=".
- Esperado: "⠶".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-022
- Nombre: Traducción compleja (Mayúsculas, guiones, números)
- Método: `debeTraducirMayusculasYNumerosBien`
- Entrada: "DOME-123".
- Esperado: "⠨⠙⠨⠕⠨⠍⠨⠑⠤⠼⠁⠃⠉".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-023
- Nombre: Preservar salto de línea
- Método: `debePreservarSaltoDeLinea`
- Entrada: "hola\nmundo".
- Resultado esperado: Contiene `\n` y traduce texto circundante.
- Resultado obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-024
- Nombre: Traducción signos matemáticos (Ecuación)
- Método: `debeTraducirSignosMatematicos`
- Entrada: "2+2=4".
- Esperado: "⠼⠃⠖⠼⠃⠶⠼⠙".
- Obtenido: Conforme.
- Estado: Éxito.

### TC-DOM-025 (Parametrizado - Especiales Final)
- Nombre: Cobertura completa signos puntuación y matemáticos
- Método: `debeTraducirCaracteresEspeciales`
- Entradas: ¡Hola!, ¿Cómo?, (texto), a-b, 5*3, 10/2.
- Resultado esperado: Traducción Braille correcta para cada símbolo especial y contexto.
- Resultado obtenido: Conforme.
- Estado: Éxito.

## 7. Análisis de Cobertura y Calidad (Dinámico)
Estado actual:
- Validaciones de creación correctas (nulos, vacíos).
- Traducción Español→Braille: Muy alta cobertura (palabras, frases, números, acentos, diéresis, símbolos).
- Traducción Braille→Español: Cobertura media (básica, números, mayúsculas, algunos símbolos).
- Modo Espejo: Cobertura funcional básica.
- Casos Borde: Se han cubierto saltos de línea y combinaciones alfanuméricas complejas.

## 8. Riesgos y Próximas Acciones
| Riesgo | Descripción | Severidad | Mitigación Propuesta |
|--------|-------------|-----------|----------------------|
| Validación Braille Inválido | No hay casos negativos para inputs Braille mal formados (ej: caracteres no Braille) | Media | Agregar tests que espera excepciones o comportamiento definido ante input sucio |
| Rendimiento textos largos | No hay pruebas de carga o textos muy extensos | Baja | Crear test con Lorem Ipsum largo para verificar tiempos |
| Consistencia bidireccional | No se valida automáticamente que `traducir(traducir(x)) == x` | Media | Implementar Property-Based Testing o test de ciclo completo |

## 9. Plan de Evolución de Pruebas
1.  **Reforzar Braille → Español**: Añadir más combinaciones complejas y casos de error.
2.  **Validación de Integridad**: Tests de ida y vuelta (Español->Braille->Español).
3.  **Capas Superiores**: Extender pruebas a `ServicioTraduccionBraille` cuando incorpore lógica de negocio real.
4.  **Integración**: Probar flujo completo desde Servlet/Controller.

## 10. Conclusión
El conjunto actual de pruebas unitarias en `TraduccionTest` demuestra una robustez significativa en la lógica central de traducción. Se cubren casos felices, casos de borde comúnmente esperados y escenarios de mezcla de tipos de caracteres. La base es sólida para continuar el desarrollo de las capas de servicio y presentación.

## 11. Formato para Documentar Re-Ejecuciones
En caso de fallo futuro, agregar bajo el caso:
```
[Re-ejecución] TC-DOM-XYZ
Fallo original: <mensaje>
Causa raíz: <análisis>
Solución: <cambio>
Resultado tras corrección: Éxito / Nuevo fallo
Fecha re-ejecución: <fecha>
```

Documento mantenido de forma evolutiva. Próximas actualizaciones pueden reorganizar secciones para reflejar nuevas capas.
