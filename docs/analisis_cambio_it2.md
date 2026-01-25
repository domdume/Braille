# Informe Técnico: Análisis de Cambio - Iteración 2 (Actualizado)

**Proyecto:** Sistema de Transcripción Braille
**Curso:** Construcción y Evolución de Software
**Fecha:** 25 de enero de 2026

## 1. Módulo de Transcripción Directa (Braille a Español)

### Descripción del Cambio
Se requiere habilitar la funcionalidad de traducción inversa, permitiendo que el sistema interprete caracteres Braille Unicode y los convierta a texto en español, respetando mayúsculas, números y signos de puntuación.

---

## 2. Módulo de Escritura Manual (Efecto Espejo) y Exportación

### Descripción del Cambio
Implementar la generación de Braille en espejo para facilitar la escritura manual con punzón y regleta. A diferencia de la propuesta inicial, el efecto espejo se ha movido **exclusivamente a la fase de exportación (Descarga PNG)** para no saturar la interfaz de usuario con múltiples recuadros de texto.

### Componentes Afectados
- **`util.MapeadorBraille`**: Implementar el método `espejarBraille(String)` que realice la transformación de bits sobre el rango Unicode. Replicado en JavaScript para exportación inmediata.
- **`webapp/index.jsp`**: 
    - Eliminación del botón de modo de visualización "Efecto Espejo".
    - Inclusión de una opción (checkbox) "Modo Espejo" en la sección de descarga.
    - Actualización de la lógica de descarga para aplicar la inversión de puntos y el revés de línea solo si la opción está activa.

---

## 3. Módulo de Entrada por Cámara (OCR)

### Descripción del Cambio
Se solicita la capacidad de usar la cámara para tomar una foto y traducir automáticamente el contenido, ya sea de Español a Braille o viceversa.

### Componentes Afectados
- **Bibliotecas Externas**: Integración de **Tesseract.js** (v5) para procesamiento OCR del lado del cliente.
- **`webapp/index.jsp`**: 
    - Adición de un modal de cámara con pre-visualización en tiempo real.
    - Implementación de controles para captura de frame y procesamiento de imagen.
    - Lógica de integración que inserta el texto detectado en el selector principal y dispara la traducción automática.

### Impacto
El impacto es alto en la interactividad del usuario. Mejora significativamente la accesibilidad al permitir traducir cartelería o textos impresos rápidamente.

---

## 4. Teclado Braille Virtual (Perkins)

### Descripción del Cambio
Para facilitar la entrada de Braille Unicode cuando no se dispone de un teclado físico especializado, se implementa un teclado virtual estilo Perkins. Este teclado permite al usuario seleccionar los puntos (1 al 6) de una celda y componer caracteres Braille directamente en el área de texto.

### Componentes Afectados
- **`webapp/index.jsp`**: 
    - Inclusión de un panel de teclado virtual con 6 botones de puntos.
    - Lógica de composición de caracteres mediante bitmasking (U+2800 + puntos).
    - Botones de acción para insertar caracteres, espacios y limpiar la selección actual.

### Mejoras en OCR y Entrada Multimedia
- Se implementó un algoritmo de pre-procesamiento de imagen antes de enviar la captura a Tesseract.js.
- **Optimización de Rendimiento**: 
    - Se implementó un **Worker Persistente** para Tesseract.js, eliminando el tiempo de carga de 2-3 segundos en cada captura posterior.
    - Se añadió **Re-escalado Inteligente** de imágenes: las fotos de alta resolución se comprimen a un máximo de 1000px antes del análisis, reduciendo drásticamente el uso de CPU y memoria sin sacrificar la precisión del OCR.
- **Detección Automática**: El sistema ahora analiza el texto extraído; si detecta una alta densidad de caracteres del bloque Unicode Braille, cambia automáticamente la dirección a "Braille a Español". En caso contrario, lo mantiene como "Español a Braille".
- **Carga de Archivos**: Se añadió una nueva funcionalidad para subir imágenes (`.jpg`, `.png`, etc.) directamente desde el almacenamiento del dispositivo, permitiendo procesar fotos de documentos o capturas de pantalla sin usar la cámara en vivo.
