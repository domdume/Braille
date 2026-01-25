# Actualización de Elementos de Configuración - Iteración 2

Este documento identifica los ítems de configuración (Software Configuration Items - SCI) que son creados o modificados durante la segunda iteración.

## 1. Código Fuente (Source Code)

### Clases Java (Modificadas)
- **[src/main/java/util/MapeadorBraille.java](src/main/java/util/MapeadorBraille.java)**: Lógica central para traducción inversa y espejado de puntos.
- **[src/main/java/service/ServicioTraduccionBraille.java](src/main/java/service/ServicioTraduccionBraille.java)**: Adaptación del flujo para soportar las nuevas direcciones.

### Interfaz Web (Modificada)
- **[src/main/webapp/index.jsp](src/main/webapp/index.jsp)**: 
    - Eliminación del selector de modo espejo en UI principal.
    - Implementación del Modal de Cámara con pre-procesamiento.
    - **Nuevo**: Botón de subida de archivos de imagen para procesamiento offline.
    - **Nuevo**: Lógica de detección automática de idioma/escritura tras OCR.
    - Implementación del botón de descarga con selector de modo.
    - Implementación del Teclado Braille Virtual.

## 2. Bibliotecas y Recursos (Libraries & Resources)

### Bibliotecas de Terceros (Nuevas)
- **Tesseract.js (v5)**: Utilizada para el Reconocimiento Óptico de Caracteres (OCR) desde la cámara.
- **html2canvas**: Utilizada para la generación de archivos PNG de señalética.

## 3. Documentación Técnica y de Usuario

### Especificación de Requisitos (Nuevos Items)
- **RF03**: Traducción Bidireccional (Español ↔ Braille).
- **RF04**: Exportación de Señalética en Modo Espejo (Escritura).
- **RF05**: Entrada de datos mediante Cámara y Reconocimiento Táctil/Visual.
