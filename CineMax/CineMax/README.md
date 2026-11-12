# CineMax · Sistema de ventas de entradas y dulcería

Proyecto JavaFX + FXML con arquitectura por capas para el caso de estudio **CINE MAX**
(venta de entradas, snacks, bebidas y combos), desarrollado para el curso de
Programación Orientada a Objetos.

El sistema permite iniciar sesión, administrar la cartelera, las salas y las
funciones, mantener el catálogo de productos con su stock y registrar ventas con
IGV, utilidad y devolución automática de stock.

| Dato | Valor |
|---|---|
| Lenguaje | Java 21 |
| Interfaz | JavaFX 21.0.6 + FXML |
| Build | Maven |
| Base de datos | H2 embebida (modo PostgreSQL) |
| Paquete | `pe.edu.upeu.cinemax` |

---

## 1. Credenciales de prueba

| Usuario | Clave | Perfil |
|---|---|---|
| `admin` | `admin123` | Administrador |
| `vendedor` | `vendedor123` | Vendedor |
| `maria` | `maria123` | Vendedor |

---

## 2. Cómo ejecutar

### Opción A: botón verde de IntelliJ IDEA

1. Abrir la carpeta `CineMax` como proyecto Maven.
2. Esperar a que Maven descargue las dependencias.
3. Ejecutar la clase **`pe.edu.upeu.cinemax.SysCineMax`**.

> `App` no extiende `Application` a propósito y la ventana está en `App.Ventana`.
> El lanzador `SysCineMax` evita el error *"JavaFX runtime components are missing"*
> al usar el botón Run del IDE.

### Opción B: Maven

```bash
mvn clean javafx:run
```

### Opción C: JAR

```bash
mvn clean package
java -cp "target/classes;target/dependency/*" pe.edu.upeu.cinemax.SysCineMax
```

> La base de datos se crea sola en `./data/cinemax.mv.db` la primera vez que se
> ejecuta la aplicación, junto con los datos de ejemplo.

---

## 3. Módulos de la aplicación

La ventana principal tiene un menú y siete pestañas. Cada módulo muestra el
formulario a la izquierda y la tabla a la derecha.

| Módulo | Qué permite hacer |
|---|---|
| **Películas** | Registrar, editar y eliminar películas de la cartelera. |
| **Salas** | Administrar salas 2D, 3D y VIP con su capacidad. |
| **Funciones** | Programar una película en una sala, con fecha y hora. |
| **Productos** | Catálogo de dulcería y entradas, con costo, % utilidad, precio y stock. |
| **Clientes** | Alta y mantenimiento de clientes por DNI. |
| **Ventas** | Registrar una venta con varias líneas, entradas asociadas a una función, IGV y total. |
| **Historial** | Consultar ventas, ver su detalle y eliminarlas devolviendo el stock. |

---

## 4. Reglas de negocio

Las fórmulas del caso de estudio están implementadas en el código y se validan en
los servicios (`service/impl`):

```
precio de venta = costo × (1 + % utilidad)
importe         = precio de venta × cantidad
total de venta  = suma de los importes del detalle
neto            = total / 1.18
IGV             = total − neto          (equivale a neto × 0.18)
utilidad        = (precio − costo) × cantidad
```

Reglas adicionales:

- Las **entradas** pertenecen a la categoría `Entradas`; no controlan stock y
  exigen seleccionar una **función**.
- Los productos de dulcería **sí** descuentan stock; la venta se rechaza si no
  hay unidades suficientes.
- La venta se guarda en una **transacción**: cabecera + detalles + descuento de
  stock. Si algo falla, no se guarda nada.
- Al **eliminar una venta** se devuelve el stock de los productos que lo
  controlan.
- Una sala no puede tener dos funciones en la misma fecha y hora.

Con los datos de ejemplo del Excel: **total vendido S/ 86.50** y
**utilidad S/ 27.50**.

---

## 5. Modelo de datos

El modelo tiene **11 tablas** y **10 relaciones**:

```
perfil ──< usuario ──< venta >── cliente
categoria ──< producto >── unidad_medida
pelicula ──< funcion >── sala
venta ──< venta_detalle >── producto
                    └─────> funcion
```

| Tabla | Descripción | Clave |
|---|---|---|
| `unidad_medida` | Kilo, unidad, litros | `id_unidad_medida` |
| `categoria` | Snacks, bebidas, combos, entradas | `id_categoria` |
| `perfil` | Administrador, vendedor | `id_perfil` |
| `usuario` | Personal que inicia sesión | `id_usuario` |
| `cliente` | Clientes por DNI | `dni` |
| `pelicula` | Cartelera | `id_pelicula` |
| `sala` | Salas 2D, 3D, VIP | `id_sala` |
| `funcion` | Película + sala + fecha + hora | `id_funcion` |
| `producto` | Dulcería y entradas | `id_producto` |
| `venta` | Cabecera de la venta | `id_venta` |
| `venta_detalle` | Líneas de la venta | `id_venta_detalle` |

### Archivos del modelo

| Archivo | Uso |
|---|---|
| `src/main/resources/schema.sql` | Esquema que usa la aplicación con H2. |
| `src/main/resources/seed.sql` | Datos de ejemplo y usuarios. |
| `database/CineMax_PowerArchitect.sql` | Modelo físico limpio para PostgreSQL. |
| `database/CineMax_PowerArchitect.architect` | Modelo para abrir en SQL Power Architect. |

### Abrir el modelo en SQL Power Architect

1. Abrir SQL Power Architect.
2. `File → Open Project...` y elegir `database/CineMax_PowerArchitect.architect`.
3. El diagrama muestra las 11 tablas distribuidas y sus relaciones.
4. Para generar el DDL: `File → Generate DDL...` o comparar con
   `database/CineMax_PowerArchitect.sql`.

---

## 6. Estructura del proyecto

```
CineMax/
├── pom.xml
├── database/
│   ├── CineMax_PowerArchitect.sql
│   └── CineMax_PowerArchitect.architect
├── docs/
│   ├── GUIA_EXPOSICION.md
│   └── capturas/            (8 capturas de la aplicación)
└── src/main/
    ├── java/pe/edu/upeu/cinemax/
    │   ├── App.java                 arranque y navegación
    │   ├── SysCineMax.java          lanzador para el IDE
    │   ├── config/                  DatabaseConfig, AppContext
    │   ├── components/              Dialogos
    │   ├── controller/              controladores FXML
    │   ├── enums/                   estados de película, función y producto
    │   ├── exception/               BusinessException
    │   ├── model/                   entidades del dominio
    │   ├── repository/              acceso a datos con JDBC
    │   ├── service/                 interfaces e implementaciones
    │   └── util/                    Formato, Sesion, Validador
    └── resources/
        ├── application.properties
        ├── schema.sql
        ├── seed.sql
        ├── css/app.css
        └── view/                    login, main y 7 módulos FXML
```

### Capas

```
Vista (FXML + CSS)
      ↓
Controlador (JavaFX)
      ↓
Servicio (reglas de negocio y validaciones)
      ↓
Repositorio (JDBC + SQL)
      ↓
Base de datos H2 / PostgreSQL
```

`AppContext` arma el grafo de dependencias (repositorios → servicios →
controladores) sin frameworks de inyección.

---

## 7. Capturas

| Archivo | Pantalla |
|---|---|
| `docs/capturas/01_login.png` | Inicio de sesión |
| `docs/capturas/02_peliculas.png` | Cartelera |
| `docs/capturas/03_salas.png` | Salas |
| `docs/capturas/04_funciones.png` | Programación de funciones |
| `docs/capturas/05_productos.png` | Productos y precios |
| `docs/capturas/06_clientes.png` | Clientes |
| `docs/capturas/07_ventas.png` | Registro de venta |
| `docs/capturas/08_historial.png` | Historial y detalle |

---

## 8. Uso con PostgreSQL (opcional)

1. Crear la base de datos:

   ```sql
   CREATE DATABASE cinemax;
   ```

2. Ejecutar `database/CineMax_PowerArchitect.sql`.

3. Editar `src/main/resources/application.properties`:

   ```properties
   db.url=jdbc:postgresql://localhost:5432/cinemax
   db.username=postgres
   db.password=postgres
   db.ddl.auto=false
   db.seed.demo=false
   ```
