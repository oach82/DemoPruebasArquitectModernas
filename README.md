# BankingLabPipeline

## 🚀 Acceso a Jenkins
- **URL de Jenkins**: `http://localhost:8080` (ajusta según tu despliegue).
- **Usuario**: `admin`  
- **Contraseña**: `admin123`  

Una vez dentro, verás el job **BankingLabPipeline** creado automáticamente por el script `jobs.groovy`.

---

## 📋 ¿Qué hace el pipeline?
Este pipeline automatiza pruebas de seguridad y funcionalidad sobre los microservicios del laboratorio bancario:

1. **API Tests - Postman**  
   Ejecuta colecciones de Postman con **Newman** para validar endpoints de autenticación.

2. **ZAP Spider (Auth y Transfer)**  
   Lanza un spider de OWASP ZAP sobre los servicios `auth-service` y `transfer-service` para descubrir endpoints.

3. **Wait Spider**  
   Espera hasta que el spider termine (status 100%).

4. **ZAP Active Scan (Auth y Transfer)**  
   Ejecuta un escaneo activo de seguridad sobre los endpoints descubiertos.

5. **Wait Active Scans**  
   Monitorea hasta que los escaneos activos finalicen.

6. **Generate ZAP Report**  
   Genera un reporte HTML (`zap-security-report.html`) con los hallazgos de seguridad.

7. **Post Actions**  
   Archiva el reporte como artefacto para descargarlo desde Jenkins.

---

## 📊 Interpretación de Resultados

### Resumen de Alertas
| Risk Level     | Número de Alertas |
|----------------|-------------------|
| High           | 0 |
| Medium         | 1 |
| Low            | 0 |
| Informational  | 1 |
| False Positives| 0 |

👉 Esto significa que:
- No se encontraron vulnerabilidades críticas (High).
- Existe **1 alerta de severidad media** que debe revisarse.
- Varias observaciones informativas sobre el comportamiento de los endpoints.

---

### Insights principales
- **Auth Service**
  - **98%** de las respuestas fueron errores **4xx** → indica que la mayoría de las peticiones fallaron (posible problema de autenticación o configuración).
  - **66%** de endpoints devuelven `application/json`.
  - **33%** devuelven `application/vnd.spring-boot.actuator.v3+json`.
  - Todos los endpoints usan método **GET**.
  - Total de endpoints: **3**.

- **Transfer Service**
  - Igual patrón: **98%** de respuestas con código 4xx.
  - **1%** de respuestas lentas.
  - **3 endpoints** detectados, todos con método GET.

## 📂 Cómo ver los resultados
- Al finalizar el pipeline, Jenkins archivará el archivo:  
  **`zap-security-report.html`**
- Para verlo:
  1. Entra al job **BankingLabPipeline** en Jenkins.
  2. Selecciona la última ejecución.
  3. Haz clic en **Artifacts** → descarga el reporte.
  4. Ábrelo en tu navegador para ver el detalle de las vulnerabilidades.