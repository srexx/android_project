# PulseRadio

App de radio streaming para Android desarrollada con Jetpack Compose, ExoPlayer y Material Design 3.

## Caracteristicas

- Reproductor de radio streaming con ExoPlayer
- Interfaz moderna con Jetpack Compose
- Navegacion inferior: Inicio, Descubrir, Favoritos
- Pantalla de reproductor fullscreen con caratulas
- 4 emisoras preconfiguradas: Lofi, Deep House, Classic Rock, Synthwave

## Tecnologias

- Kotlin
- Jetpack Compose
- ExoPlayer (Media3)
- Coil (carga de imagenes)
- Navigation Compose

## Compilar localmente

Requisitos:
- Android Studio Panda o posterior
- JDK 17 o 21
- 6GB de RAM libres para Gradle

Pasos:
1. Abre Android Studio
2. Selecciona "Open" y elige esta carpeta
3. Espera la sincronizacion de Gradle
4. Build -> Generate App Bundles or APKs -> Generate APKs

## Compilar con GitHub Actions (recomendado)

No necesitas Android Studio ni Java instalado en tu PC.

1. Sube este codigo a un repositorio de GitHub
2. Ve a la pestana "Actions"
3. Ejecuta el workflow "Build PulseRadio APK"
4. Descarga el APK desde "Artifacts"

Ver GUIA_GITHUB_ACTIONS_PRINCIPIANTES.md para instrucciones detalladas paso a paso.

## Instalacion

1. Descarga app-debug.apk
2. Transfiere a tu telefono Android
3. Habilita "Origenes desconocidos" en ajustes de seguridad
4. Instala y abre

## Estructura del proyecto

```
app/src/main/java/com/pulseradio/
  data/RadioStation.kt          # Modelo de datos
  player/RadioPlayer.kt         # Wrapper de ExoPlayer
  viewmodel/RadioViewModel.kt   # Estado de reproduccion
  ui/
    theme/                      # Colores y tipografia
    home/HomeScreen.kt          # Pantalla principal
    player/PlayerScreen.kt      # Reproductor fullscreen
    navigation/BottomNav.kt     # Barra de navegacion
  MainActivity.kt               # Punto de entrada
```

## Licencia

Proyecto personal de aprendizaje.
