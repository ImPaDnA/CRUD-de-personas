# Lista de Personas — CRUD (Android, Java)

Aplicación Android para **crear, leer, editar y eliminar** personas usando **SQLite** y **RecyclerView**.
La app inicia con una **lista** (MainActivity).

* **Crear**: en `AgregarPersonaActivity`.
* **Editar**: en `EditarPersonaActivity`.
* **Eliminar**: desde la lista con **AlertDialog** de confirmación.

---

## 📱 Características

* Lista de personas con **RecyclerView** y **Adapter**.
* **Create/Read/Update/Delete** con controlador de datos dedicado.
* **AlertDialog** para confirmar eliminaciones.
* **Refresco** automático de la lista al volver de crear/editar.
* Listener de toques (`RecyclerTouchListener`) para gestionar clics en ítems.

---

## 🧱 Arquitectura y Tecnologías

* **Lenguaje:** Java
* **UI:** AppCompat + Material Components
* **Datos:** SQLite con `SQLiteOpenHelper`
* **Mínimo SDK:** 21+
* **Paquete base:** `com.matias.proyectocrud`
* **Nombre de la app (strings):** `Lista de Personas`

---

## 🗂️ Estructura principal

```
app/src/main/
├─ AndroidManifest.xml
├─ java/com/matias/proyectocrud/
│  ├─ MainActivity.java
│  ├─ AgregarPersonaActivity.java
│  ├─ EditarPersonaActivity.java
│  ├─ AdaptadorPersonas.java
│  ├─ RecyclerTouchListener.java
│  ├─ Persona.java
│  ├─ ControladorPersonas.java
│  ├─ AyudanteBaseDeDatos.java
│  └─ PersonasContract.java
└─ res/layout/
   ├─ activity_main.xml
   ├─ activity_agregar_persona.xml
   ├─ activity_editar_persona.xml
   └─ mi_fila.xml
```

---

## 🧾 Modelo de datos

**Tabla:** `personas`
**Columnas:**

* `id` (INTEGER, PK autoincremental)
* `nombre` (TEXT, NOT NULL)
* `edad` (INTEGER)

**SQL de creación (aprox.):**

```sql
CREATE TABLE personas (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre TEXT NOT NULL,
  edad INTEGER
);
```

> Nombres centralizados en `PersonasContract.PersonaEntry`:
> `NOMBRE_TABLA`, `COLUMNA_ID`, `COLUMNA_NOMBRE`, `COLUMNA_EDAD`.

---

## 🔌 Capas y responsabilidades

* **`AyudanteBaseDeDatos`**: crea/actualiza el esquema SQLite.
* **`ControladorPersonas`**: operaciones CRUD (insertar, listar, obtener por id, actualizar, eliminar).
* **`MainActivity`**: lista personas, navega a agregar/editar y elimina con confirmación.
* **`AgregarPersonaActivity`**: formulario para crear persona.
* **`EditarPersonaActivity`**: formulario para editar persona (recibe **ID** del ítem).
* **`AdaptadorPersonas`**: une datos (`List<Persona>`) con la vista (`RecyclerView`).
* **`RecyclerTouchListener`**: gestiona taps en la lista.

---

## 🧭 Flujo de usuario

1. **Inicio**: `MainActivity` muestra la lista.
2. **Crear**: botón **+** → `AgregarPersonaActivity` → guardar → vuelve y refresca lista.
3. **Editar**: tocar una persona → `EditarPersonaActivity` con el **ID** → guardar → vuelve y refresca.
4. **Eliminar**: mantener presionado en la persona → **AlertDialog** → confirmar → se elimina y refresca.

---

## ▶️ Cómo ejecutar

1. Abrir el proyecto en **Android Studio**.
2. Sincronizar Gradle.
3. Ejecutar en emulador/dispositivo **API 21+**.

**Dependencias clave (ejemplo):**

```gradle
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'com.google.android.material:material:1.12.0'
```

---

## ✅ Pruebas manuales sugeridas

* Crear varias personas y verificar que aparecen en la lista.
* Editar un registro y confirmar persistencia tras cerrar/abrir la app.
* Intentar guardar con campos vacíos (debe mostrar error y bloquear).
* Eliminar con **Cancelar** y **Aceptar** para comprobar ambos caminos.

---

## 🛠️ Mejoras futuras (roadmap)

* Búsqueda/filtrado por nombre.
* Ordenamiento por nombre/edad.
* Validaciones más estrictas (rango de edad, trimming de texto).
* Exportar/Importar CSV.
* Migración a **Room** (Entity/Dao/Database) con actualizaciones reactivas.

---

## 👤 Alumno

**Matías Aravena**

---

> **Resumen:** CRUD sencillo y didáctico con SQLite y RecyclerView. Pantalla inicial de lista; crear y editar en Activities separadas; eliminar con **AlertDialog**. Base sólida para tareas universitarias o para expandir hacia una app real.
