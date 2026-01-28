# 🔤 Sistema de Traducción Braille

Sistema web para traducción bidireccional entre español y Braille, desarrollado con Java EE y Jakarta Servlet API.

## 📋 Requisitos Previos

- **Java JDK 8 o superior**
- **Apache Maven 3.6+**
- **Apache Tomcat 10.1.x** (compatible con Jakarta EE 10)

## 🚀 Compilación y Despliegue

### 1. Clonar el Repositorio

```bash
git clone https://github.com/domdume/Braille.git
cd Braille
```

### 2. Compilar el Proyecto

```bash
mvn clean package
```

Este comando:
- Limpia compilaciones anteriores
- Compila el código fuente
- Ejecuta las pruebas
- Genera el archivo WAR en `target/Braille-1.0-SNAPSHOT.war`

### 3. Desplegar en Tomcat

**Opción A: Despliegue Manual**

1. Copiar el WAR a Tomcat:
   ```bash
   # Windows
   copy target\Braille-1.0-SNAPSHOT.war C:\apache-tomcat-10.1.33\webapps\
   
   # Linux/Mac
   cp target/Braille-1.0-SNAPSHOT.war /ruta/a/tomcat/webapps/
   ```

2. Iniciar Tomcat:
   ```bash
   # Windows
   cd C:\apache-tomcat-10.1.33\bin
   catalina.bat run
   
   # Linux/Mac
   cd /ruta/a/tomcat/bin
   ./catalina.sh run
   ```

**Opción B: Usando PowerShell (Windows)**

```powershell
# Compilar y desplegar en un solo comando
cd "ruta\al\proyecto\Braille"
mvn clean package -DskipTests
Copy-Item "target\Braille-1.0-SNAPSHOT.war" "C:\apache-tomcat-10.1.33\webapps\" -Force
```

### 4. Acceder a la Aplicación

Una vez desplegado, abrir el navegador en:

```
http://localhost:8080/Braille-1.0-SNAPSHOT/
```

## 🎯 Uso de la Aplicación

### Interfaz Web

1. Ingresar el texto a traducir
2. Seleccionar la dirección de traducción:
   - **Español → Braille**
   - **Braille → Español**
3. Hacer clic en "Traducir"
4. (Opcional) Descargar imagen PNG, con opción de **Modo Espejo 🪞** para escritura manual

### API REST

**Endpoint:** `POST /api/traducir`

**Request:**
```json
{
  "texto": "Hola mundo",
  "direccion": "ESPANOL_A_BRAILLE"
}
```

**Response:**
```json
{
  "exito": true,
  "textoOriginal": "Hola mundo",
  "textoTraducido": "⠠⠓⠕⠇⠁⠀⠍⠥⠝⠙⠕",
  "direccion": "ESPANOL_A_BRAILLE"
}
```

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/Braille-1.0-SNAPSHOT/api/traducir \
  -H "Content-Type: application/json" \
  -d '{"texto":"Hola","direccion":"ESPANOL_A_BRAILLE"}'
```

**Ejemplo con PowerShell:**
```powershell
$body = @{texto="Hola mundo";direccion="ESPANOL_A_BRAILLE"} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/Braille-1.0-SNAPSHOT/api/traducir" `
  -Method POST -Body $body -ContentType "application/json; charset=UTF-8"
```

## 📦 Estructura del Proyecto

```
src/main/java/
├── dto/                              # Data Transfer Objects
│   ├── SolicitudTraduccion.java     # Request DTO
│   └── RespuestaTraduccion.java     # Response DTO
├── model/                            # Modelos de dominio
│   ├── Traduccion.java              # Entidad principal
│   └── DireccionTraduccion.java     # Enum direcciones
├── service/                          # Lógica de negocio
│   └── ServicioTraduccionBraille.java
├── servlet/                          # Controladores REST
│   └── ControladorBraille.java
├── filter/                           # Filtros HTTP
│   └── FiltroCors.java              # CORS configuration
└── util/                             # Utilidades
    └── MapeadorBraille.java         # Mapeo de caracteres

src/main/webapp/
├── index.jsp                        # Interfaz principal
├── css/
│   └── styles.css                   # Estilos
└── WEB-INF/
    └── web.xml                      # Configuración web
```

## ✨ Características

- ✅ Alfabeto completo español (a-z, ñ)
- ✅ Vocales acentuadas (á, é, í, ó, ú, ü)
- ✅ Números (0-9)
- ✅ Signos de puntuación (. , ; : ? ! - ( ) ")
- ✅ Mayúsculas y minúsculas
- ✅ Traducción bidireccional
- ✅ Modo Espejo para escritura manual
- ✅ API REST con JSON
- ✅ CORS habilitado

## 🛠️ Tecnologías

- Jakarta EE 10
- Jakarta Servlet API 6.1.0
- Jakarta Persistence API 3.1.0
- Hibernate 6.2.7.Final
- H2 Database 2.2.224
- Gson 2.10.1
- Maven 3.9+

## 🧪 Probar Solo la Compilación

```bash
# Compilar sin ejecutar tests
mvn clean compile

# Compilar y ejecutar tests
mvn clean test

# Empaquetar sin tests
mvn clean package -DskipTests
```

## 📝 Notas

- El proyecto usa **Jakarta EE 10**, por lo que requiere **Tomcat 10.x** o superior
- Tomcat 9.x y anteriores usan **javax** en lugar de **jakarta** y **NO** son compatibles
- La base de datos H2 se crea automáticamente en `./data/brailledb.mv.db`

## 📄 Licencia

Este proyecto es de código abierto y está disponible para uso educativo.
