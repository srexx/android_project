# Estructura Completa del Proyecto PulseRadio

Este archivo describe cada archivo generado y su proposito.

## Archivos de configuracion de Gradle (raiz)

| Archivo | Proposito |
|---------|-----------|
| build.gradle.kts | Configuracion del proyecto raiz, aplica plugins |
| settings.gradle.kts | Define nombre del proyecto y repositorios |
| gradle.properties | Memoria JVM (6GB), cache, AndroidX |
| gradle/libs.versions.toml | Catalogo de versiones: Compose, ExoPlayer, Coil |
| gradlew | Script Unix para ejecutar Gradle (Linux/Mac/GitHub) |
| gradlew.bat | Script Windows para ejecutar Gradle |
| download-wrapper.sh | Descarga gradle-wrapper.jar si falta |

## Configuracion del modulo app

| Archivo | Proposito |
|---------|-----------|
| app/build.gradle.kts | Dependencias del modulo, SDK 35, minSdk 26 |
| app/proguard-rules.pro | Reglas de ofuscacion (vacio por ahora) |

## Recursos Android

| Archivo | Proposito |
|---------|-----------|
| app/src/main/AndroidManifest.xml | Permisos INTERNET, FOREGROUND_SERVICE, actividad principal |
| app/src/main/res/values/strings.xml | Nombre de la app: "PulseRadio" |
| app/src/main/res/values/themes.xml | Tema sin ActionBar, colores de barras de sistema |

## Codigo fuente Kotlin

### Capa de datos
| Archivo | Proposito |
|---------|-----------|
| data/RadioStation.kt | Data class con 4 emisoras predefinidas |

### Capa de reproduccion
| Archivo | Proposito |
|---------|-----------|
| player/RadioPlayer.kt | Wrapper de ExoPlayer con StateFlow para estado de reproduccion |

### Capa de ViewModel
| Archivo | Proposito |
|---------|-----------|
| viewmodel/RadioViewModel.kt | AndroidViewModel que conecta UI con RadioPlayer |

### Capa de UI (Jetpack Compose)

| Archivo | Proposito |
|---------|-----------|
| ui/theme/Theme.kt | Esquema de colores oscuros (cyan #06b6d4 sobre negro) |
| ui/theme/Type.kt | Tipografia por defecto |
| ui/home/HomeScreen.kt | Pantalla principal: header, sugerido, grid de vibes |
| ui/player/PlayerScreen.kt | Reproductor fullscreen: caratula, nombre, boton play/pause |
| ui/navigation/BottomNav.kt | Barra inferior con 3 pestanas: Inicio, Descubrir, Favoritos |
| MainActivity.kt | Punto de entrada, Scaffold, navegacion entre pantallas |

## GitHub Actions

| Archivo | Proposito |
|---------|-----------|
| .github/workflows/build.yml | Compila APK automaticamente en cada push |

## Guia de usuario

| Archivo | Proposito |
|---------|-----------|
| README.md | Descripcion del proyecto para visitantes de GitHub |
| GUIA_GITHUB_ACTIONS_PRINCIPIANTES.md | Tutorial paso a paso desde cero |
| ESTRUCTURA_PROYECTO.md | Este archivo: explica cada archivo |

## Que NO incluye este proyecto (debes agregar)

- gradle-wrapper.jar: archivo binario necesario solo para compilar localmente con ./gradlew. GitHub Actions NO lo necesita porque instala Gradle directamente.
- Iconos de launcher (mipmap-*): Android Studio los genera automaticamente con Image Asset Studio.
- Tests unitarios: se pueden agregar despues.
- Firma de release: para Google Play Store se necesita un keystore.

## Como verificar que todo esta correcto

1. Abre esta carpeta en Android Studio Panda
2. File -> Sync Project with Gradle Files
3. Si aparece BUILD SUCCESSFUL, todo esta bien
4. Si pide gradle-wrapper.jar, ejecuta en terminal: gradle wrapper (si tienes Gradle instalado)
