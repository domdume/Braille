# Ambiente de Desarrollo

## Sistema de Transcripción Español–Braille 

## **1. Herramientas Seleccionadas**

El proyecto “Braille” se desarrolla utilizando un stack tecnológico orientado a aplicaciones web basadas en Jakarta EE.

* **Lenguaje de Programación:** Java 17 (LTS).

  * *Justificación:* Ofrece estabilidad a largo plazo, soporte robusto para manejo de cadenas y Unicode (incluyendo patrones Braille U+2800–U+28FF), y compatibilidad con Servlets y JSP modernos.

* **IDE:** IntelliJ IDEA Ultimate.

  * Utilizado por su integración con Maven, Git y soporte nativo para Tomcat.

* **Control de Versiones:** Git + GitHub (repositorio remoto `domdume/Braille`).

* **Framework Web (Backend):**

  * `Jakarta Servlet API 6.1.0` – Controlador REST.
  * `Jakarta JSP 3.0.0` – Vistas.
  * `JSTL 2.0.0` – Librerías de etiquetas para JSP.

* **Serialización JSON:** `Gson 2.10.1`.

* **Testing:**

  * JUnit 5 (Jupiter 5.10.2) + Maven Surefire Plugin.

* **Construcción y Dependencias:**

  * Apache Maven 3.9.x.

* **Servidor de Aplicaciones:**

  * Tomcat, ejecutado mediante `tomcat7-maven-plugin`, simplificando el despliegue en desarrollo.

---

## **2. Estrategia de Ramificación (Branching Strategy)**

Para garantizar la estabilidad del código y cumplir la restricción institucional de **no trabajar directamente sobre `main`**, se adoptó una variante de **GitHub Flow con ramas de entorno**, adaptada al contexto del proyecto.

La estrategia emplea ramas permanentes para backend, frontend y desarrollo, más ramas temporales para features.

### **Convención de nombres**

* `develop` – Rama troncal de integración.
* `backend` – Desarrollo del lado del servidor.
* `frontend` – Desarrollo de la interfaz JSP/JS.
* `feature/<nombre>` – Ramas temporales para nuevas funcionalidades.
* `segunda-iteracion` – Rama de iteración para cambios solicitados de la segunda iteración.
* `documentacion` – Archivos, diagramas y manuales (opcional).
* `main` – Código estable listo para entrega.

---

## **2.2.1. Estructura de Ramas**

| Rama                | Tipo        | Descripción y Reglas                                                                                                                   |
| ------------------- | ----------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **`main`**          | Producción  | Código estable. No se permiten commits directos. Solo recibe fusiones desde `develop`.                                                 |
| **`develop`**       | Integración | Rama troncal donde confluyen `backend` y `frontend` tras completar una funcionalidad.                                                  |
| **`backend`**       | Desarrollo  | Rama permanente dedicada al desarrollo del servidor (Servlets, servicios, DTOs, filtros, utilidades).                                  |
| **`frontend`**      | Desarrollo  | Rama permanente dedicada a la interfaz de usuario (JSP, CSS, JS).                                                                      |
| **`segunda-iteracion`** | Iteración  | Rama para la segunda iteración del proyecto donde se integran cambios solicitados (OCR, UI mejorada, nuevas funcionalidades).          |
| **`feature/*`**     | Temporal    | Ramas cortas para implementar funcionalidades independientes. Crecen desde `backend` o `frontend` y se eliminan después de integrarse. |
| **`documentacion`** | Soporte     | Rama auxiliar para manuales, informes y diagramas.                                                                                     |

---

## **3. Flujo de Trabajo Aplicado**

El flujo real del proyecto siguió esta secuencia:

### **a. Desarrollo Backend**

En la rama `backend` se implementaron:

* Sistema de traducción (`ServicioTraduccionBraille`)
* DTOs (`SolicitudTraduccion`, `RespuestaTraduccion`)
* Controlador REST (`ControladorBraille`)
* Mapeos Braille (`MapeadorBraille`)
* Filtro CORS

Cada uno desarrollado inicialmente en ramas `feature/*`.

### **b. Desarrollo Frontend**

En la rama `frontend` se implementaron:

* Página JSP principal
* Integración con la API mediante JavaScript (Fetch API)
* Estilos (Tailwind CDN o CSS propio)

Las mejoras también partieron de ramas `feature/*`.

### **c. Integración General**

`backend` y `frontend` se integraron regularmente en `develop`, donde se validó el funcionamiento completo.

---

## **4. Diagrama de Flujo de Ramas (aplicado en este proyecto)**

```
main
  ↑
  └── develop
         ↑                    ↑
         │                    │
     backend              frontend
         ↑                    ↑
         │                    │
  feature/*              feature/*
  
  segunda-iteracion ← Rama de Iteración (cambios de la segunda iteración)
  
```

---

## **2.5. Estrategia de Pruebas**

Para asegurar la calidad del motor de traducción, se implementaron pruebas automatizadas con JUnit 5.

### **Implementación:**

1. Las pruebas se desarrollaron dentro del flujo normal en ramas `feature/*`.
2. Se integraron a `backend`.
3. Luego pasaron a `develop` para validación general.

### **Tecnología usada:**

*JUnit 5 (Jupiter 5.10.2)* con ejecución mediante Maven:

```bash
mvn test
```

---

## **6. Generación del Artefacto de Distribución**

El entregable generado por el proyecto es un archivo **WAR**.

### **Comando de construcción:**

```bash
mvn clean package -DskipTests
```

### **Resultado:**

```
target/Braille-1.0-SNAPSHOT.war
```

### **Ejecución del servidor con Maven:**

```bash
mvn org.apache.tomcat.maven:tomcat7-maven-plugin:run
```


