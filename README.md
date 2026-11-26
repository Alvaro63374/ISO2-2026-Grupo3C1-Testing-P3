# 🌱 ISO2-2026-Grupo3C1-Testing-P3 — Sistema de Riego Inteligente para Invernaderos

🧪 **Segundo Ejercicio Teórico y Segunda Parte de Prácticas (ET.02)** · 🎓 **Ingeniería del Software II (Curso 2026/27)** · 👥 **Equipo 3C1 (ESI · UCLM)**

Repositorio independiente correspondiente al **Problema 3 (ET.02.04)** dentro del proyecto colaborativo de pruebas del Equipo 3C1.

---

## 👥 Reparto de Responsabilidades (Tabla 1)

| Etapa | Actividad | Rama / Entregable | Pareja Responsable |
| :--- | :--- | :--- | :--- |
| **Paso 1** | Implementación del programa en Java + Maven (SOLID) | Rama `develop` | **Pareja 3:** Álvaro y [Nombre de tu pareja] |
| **Paso 2** | Diseño de casos de prueba (clases equiv., límites, PICT, MC/DC) | `ISO2-2026-Grupo3C1-Testing-P3.pdf` + Wiki | **Pareja 1:** [Nombres de la pareja 1] |
| **Paso 3** | Implementación de pruebas JUnit 5 y cobertura JaCoCo ($\ge 80\%$) | Rama `hotfix` + Informes en Wiki | **Pareja 2:** [Nombres de la pareja 2] |

---

## 📋 Reglas de Negocio y Suposiciones de Diseño

El sistema evalúa los datos ambientales de un invernadero (`humedadSuelo`, `temperaturaAmbiente`, `previsionLluvia`, `hora`) para determinar la recomendación del ciclo de riego aplicable:

* **Drenaje de Emergencia:** `humedadSuelo > 60%` y `temperaturaAmbiente < 5 ºC` *(prevenir la congelación de las raíces)*.
* **Franja de Máxima Insolación (`12:00` a `16:00`):**
  * **Riego Crítico:** Si `humedadSuelo < 10%` dentro de esta franja horaria.
  * **Posponer Riego:** Para cualquier otro valor de humedad (`>= 10%`) dentro de esta franja horaria.
* **No Regar:** `humedadSuelo < 40%` y `previsionLluvia == true` *(previsión de lluvia inminente)*.
* **Riego Intenso:** `humedadSuelo < 20%`, `temperaturaAmbiente > 25 ºC` y `previsionLluvia == false`.
* **Riego Ligero:** `20% <= humedadSuelo <= 40%`, `temperaturaAmbiente > 25 ºC` y `previsionLluvia == false`.

### Decisiones ante ambigüedades y validación (`es tu diseño, es tu decisión`)

1. **Jerarquía de prioridades ante solapamientos:** Dado que un mismo estado ambiental puede satisfacer simultáneamente varias reglas (por ejemplo, estar en la franja `12:00-16:00` y tener previsión de lluvia o temperatura `> 25 ºC`), el sistema aplica un orden estricto de prioridad basado en la seguridad del cultivo y el ahorro hídrico:
   1. `DRENAJE_EMERGENCIA` (prioridad máxima para evitar la pérdida del cultivo por helada).
   2. `RIEGO_CRITICO` / `POSPONER_RIEGO` (control estricto en horas de máxima insolación `[12:00, 16:00]` inclusive).
   3. `NO_REGAR` (si existe previsión de lluvia inminente y la humedad es `< 40%`, se cancela el riego normal).
   4. `RIEGO_INTENSO` y `RIEGO_LIGERO` (condiciones normales de riego).
2. **Ausencia de acción (`RecomendacionRiego.SIN_ACCION`):** Si los datos son válidos pero no cumplen ninguna de las reglas anteriores (por ejemplo, `humedadSuelo > 40%` con temperatura templada, o `temperaturaAmbiente <= 25 ºC` sin lluvia), se devuelve `RecomendacionRiego.SIN_ACCION`.
3. **Encapsulamiento y validación de dominio (`IllegalArgumentException`):** El constructor de `DatosInvernadero` valida que los parámetros estén en rangos físicamente coherentes (`humedadSuelo` en `[0, 100]`, `temperaturaAmbiente` en `[-50, 70]` ºC y `hora != null`). `GestorRiegoImpl` rechaza entradas `null`.

---

## 🏗️ Estructura del Proyecto Maven

```text
ISO2-2026-Grupo3C1-Testing-P3/
├── pom.xml                                                 # Java 21, JUnit 5, Surefire y JaCoCo
└── src/
    ├── main/java/es/uclm/esi/iso2/riego/
    │   ├── RecomendacionRiego.java                         # Enumerado con recomendaciones y descripciones
    │   ├── DatosInvernadero.java                           # Entidad inmutable con validación en constructor
    │   ├── IGestorRiego.java                               # Contrato de servicio (desacoplamiento SOLID)
    │   ├── GestorRiegoImpl.java                            # Lógica de negocio de evaluación de riego
    │   └── Main.java                                       # Interfaz CLI desacoplada del dominio
    └── test/java/es/uclm/esi/iso2/riego/                   # Pruebas unitarias JUnit 5 (Paso 3 - rama hotfix)