# BankingLabPipeline — Laboratorio de Testing y Seguridad CI/CD

## 🎯 Propósito del laboratorio

Este laboratorio demuestra **cómo integrar pruebas funcionales y de seguridad en un pipeline CI/CD**, usando contenedores y Jenkins como orquestador
---

## 🧪 ¿Qué valida este laboratorio?

* Disponibilidad de microservicios
* Validación funcional de APIs (Postman / Newman)
* Descubrimiento de endpoints (OWASP ZAP Spider)
* Análisis dinámico de seguridad (OWASP ZAP Active Scan)
* Generación de evidencias automáticas

---

## 🐳 Arquitectura del entorno

```text
┌────────────┐      ┌──────────────┐
│ Jenkins    │────▶ │ Newman Tests │
│ (Pipeline) │────▶ │ ZAP Spider   │
│            │────▶ │ ZAP Scan     │
└─────┬──────┘      └──────────────┘
      │
      ▼
┌───────────────┐   ┌────────────────┐
│ auth-service  │   │ transfer-serv. │
└──────┬────────┘   └────────┬───────┘
       ▼                         ▼
        └──────────┬─────────────┘
                   ▼
             ┌──────────┐
             │ Postgres │
             └──────────┘
```

---

## 🐳 Contenedores y puertos expuestos

| Servicio         | Imagen / Build     | Puerto Host → Contenedor   |
| ---------------- | ------------------ | -------------------------- |
| db-service       | postgres:15        | 5432 → 5432                |
| auth-service     | ./auth-service     | 8081 → 8080                |
| transfer-service | ./transfer-service | 8082 → 8080                |
| jenkins          | ./jenkins          | 8080 → 8080, 50000 → 50000 |
| zap              | ./zap-local        | 8083 → 8083                |

---

## ⚙️ Configuración clave

### Base de datos

* POSTGRES_USER=bankuser
* POSTGRES_PASSWORD=bankpass
* POSTGRES_DB=banking

### Jenkins

* Setup Wizard deshabilitado
* Usuario admin creado automáticamente
* Pipeline creado por script Groovy
* Volumen montado:

  * Proyecto → `/workspace`

### ZAP

* Modo daemon
* API habilitada sin key
* Acceso permitido desde Jenkins

---

## 📥 Descarga de dependencias externas (ZAP)

Por tamaño, el instalador no se versiona.

📦 **Descargar desde Releases:**

* ZAP_2_17_0_unix.sh

👉 Ubicar el archivo en:

```
zap-local/
```

antes de ejecutar el laboratorio.

---

## 🚀 Puesta en marcha

```bash
podman-compose up -d
```

Accesos:

* Jenkins: [http://localhost:8080](http://localhost:8080)
* Usuario: admin
* Password: admin123

---

## 🔁 Flujo del pipeline CI/CD

1️⃣ Inicialización

* Verifica entorno
* Espera servicios

2️⃣ Pruebas funcionales (Newman)

* Ejecuta colecciones Postman
* Valida respuestas y contratos

3️⃣ ZAP Spider

* Descubre endpoints expuestos
* auth-service
* transfer-service

4️⃣ ZAP Active Scan

* Analiza vulnerabilidades OWASP Top 10
* Ataques controlados

5️⃣ Reporte de seguridad

* Genera `zap-security-report.html`
* Se archiva como artefacto

---

## 📊 Evidencias generadas

| Evidencia           | Ubicación         |
| ------------------- | ----------------- |
| Reporte ZAP HTML    | Artifacts Jenkins |
| Logs Newman         | Consola Jenkins   |
| Resultados pipeline | Build History     |

---

## 🎓 Valor educativo del laboratorio

✔ Pipeline CI/CD realista
✔ Integración DevSecOps
✔ Evidencias automáticas
✔ Jenkins como orquestador puro
✔ Ideal para cursos ISTQB, QA, DevOps

---

## 📦 Versión del laboratorio

**v2.0 — Enero 2026**

* Pipeline corregido
* Scripts Groovy robustos
* Documentación estructurada
* Enfoque didáctico mejorado

---

👨‍💻 Autor: Oscar Castro
🔬 Proyecto: Laboratorio de Pruebas de Arquitecturas Modernas
