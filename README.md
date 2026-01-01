# CineScope Android App

Aplicación Android para la gestión y consulta de películas, directores, actores y productoras cinematográficas. Proyecto desarrollado para la asignatura de Programación Multimedia y Dispositivos Móviles.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Ejecución](#ejecución)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Funcionalidades](#funcionalidades)

## Descripción

CineScope es una aplicación Android completa que permite:
- Navegar por un catálogo de películas
- Ver detalles completos de cada película
- Registrar y editar películas
- Gestionar favoritos con base de datos local
- Visualizar estudios cinematográficos en un mapa interactivo

El proyecto implementa el patrón **MVP (Model-View-Presenter)** con integración a API REST y base de datos local para favoritos.

## Tecnologías

- **Java 17**
- **Android SDK 34**
- **Retrofit** - Cliente HTTP para consumo de API REST
- **Room Database** - Base de datos local SQLite
- **Mapbox** - Mapas interactivos con marcadores
- **Glide** - Carga y caché de imágenes
- **Material Design 3** - Componentes UI
- **RecyclerView** - Listas optimizadas

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Android Studio**
    - Descarga desde: https://developer.android.com/studio
- **JDK 17** o superior
    - Verifica con: `java -version`
- **Git**
    - Verifica con: `git --version`
- **Backend API corriendo**
    - Repositorio: https://github.com/cristinasevi/acceso-datos-aa1
    - URL base: `http://10.0.2.2:8080/` (para emulador) o `http://<TU_IP>:8080/` (dispositivo físico)
- **Mapbox Access Token**
    - Regístrate en: https://account.mapbox.com/

## Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/cristinasevi/programacion-multimedia-aa1.git
cd programacion-multimedia-aa1
```

### 2. Configurar Mapbox Token

Edita el archivo `.gradle/gradle.properties` y escribe tu token de mapbox. 

Además en el archivo `settings.gradle.kts` cambia el campo de password con el secret token.

Y en el archivo `app/src/main/res/values/developer-config.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mapbox_access_token" translatable="false">TU_MAPBOX_ACCESS_TOKEN_AQUI</string>
</resources>
```

## Ejecución

### Opción 1: Emulador de Android Studio

1. Abre el proyecto en Android Studio
2. Crea un Android Virtual Device:
   - `Tools > Device Manager > Create Device`
   - Selecciona un dispositivo (ej: Pixel 4a)
   - Descarga una imagen del sistema (API 34 recomendado)
3. Haz clic en **Run**

### Opción 2: Dispositivo físico

1. Habilita **Opciones de Desarrollador** en tu dispositivo:
   - `Ajustes > Acerca del teléfono > Toca 7 veces en "Número de compilación"`
2. Habilita **Depuración USB**:
   - `Ajustes > Opciones de desarrollador > Depuración USB`
3. Conecta el dispositivo por USB
4. Acepta la autorización en el dispositivo
5. Selecciona tu dispositivo en Android Studio
6. Haz clic en **Run**

### Verificar conexión con el backend
```bash
# Verifica que el backend esté corriendo
curl http://localhost:8080/movies

# O desde el navegador del emulador
http://10.0.2.2:8080/movies
```

## Estructura del Proyecto

```
programacion-multimedia-aa1/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/programacion/multimedia/aa1/
│   │   │   │   ├── adapter/         # Adaptadores RecyclerView
│   │   │   │   ├── api/             # Interfaces Retrofit
│   │   │   │   ├── contract/        # Contratos MVP
│   │   │   │   ├── db/              # Room Database
│   │   │   │   ├── domain/          # Entidades de dominio
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── model/           # Modelos MVP
│   │   │   │   ├── preferences/     # Gestión de preferencias
│   │   │   │   ├── presenter/       # Presenters MVP
│   │   │   │   ├── utils/           # Utilidades y constantes
│   │   │   │   └── view/            # Activities (View)
│   │   │   ├── res/
│   │   │   │   ├── drawable/        # Iconos e imágenes
│   │   │   │   ├── layout/          # XML de layouts
│   │   │   │   ├── menu/            # Menús de opciones
│   │   │   │   ├── mipmap/          # Imagen del marcador del mapa
│   │   │   │   ├── values/          # Strings, colores, estilos
│   │   │   │   ├── xml/             # Configuración de preferencias
│   │   │   └── AndroidManifest.xml  # Configuración de la app
│   ├── build.gradle                 # Dependencias y configuración
├── gradle/                          # Configuración Gradle
├── .gitignore
├── build.gradle                     # Configuración global
├── settings.gradle
└── README.md
```

## Funcionalidades

### Características Principales

#### 1. **Navegación entre Activities**
- `MovieListView` - Pantalla principal con lista de películas
- `MovieDetailView` - Detalles completos de película
- `RegisterMovieView` - Formulario de registro de película
- `EditMovieView` - Edición de películas existentes
- `FavoritesView` - Lista de películas favoritas
- `StudiosMapView` - Mapa interactivo con ubicaciones
- `ReviewListView` - Lista de reseñas
- `RegisterReviewView` - Formulario de registro de reseña
- `EditReviewView` - Edición de reseñas existentes
- `PreferencesActivity` - Pantalla de preferencias

#### 2. **Consumo de API REST**
- **GET** `/movies` - Listar películas
- **GET** `/movies/{id}` - Detalle de película
- **GET** `/studios` - Listar estudios
- **POST** `/movies` - Crear película
- **PUT** `/movies/{id}` - Actualizar película
- **DELETE** `/movies/{id}` - Eliminar película
- **GET** `/movies/{movieId}/reviews` - Listar reseñas de película
- **POST** `/movies/{movieId}/reviews` - Crear reseña
- **PUT** `/reviews/{id}` - Actualizar reseña
- **DELETE** `/reviews/{id}` - Eliminar reseña

#### 3. **Base de Datos Local (Room)**
- **CRUD completo**: Create, Read, Update, Delete
- **Entidad**: `FavoriteMovie` con `movieId` como clave primaria
- **RecyclerView**: Lista de favoritos con adaptador personalizado

#### 4. **Mapas con Mapbox**
- Visualización de estudios cinematográficos
- Marcadores personalizados
- Coordenadas GPS reales

#### 5. **Arquitectura MVP**
- **Contract**: Interfaces View-Presenter
- **View**: Activities (UI)
- **Presenter**: Coordinación entre View y Model
- **Model**: Lógica de negocio (API + Room)

#### 6. **Material Design**
- `CardView` - Tarjetas de películas
- `FloatingActionButton` - Acción flotante
- `TextInputLayout` - Campos con validación
- `Toolbar` con ActionBar
- Tema Material Design 3

#### 7. **Multiidioma**
- **Español** - `res/values-es/strings.xml`
- **Inglés** - `res/values/strings.xml`

#### 8. **Preferencias de usuario**
- Configuración de tema (claro/oscuro)
- Personalización de la aplicación
- Almacenamiento con SharedPreferences
```
