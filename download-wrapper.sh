#!/bin/bash
# Script para descargar gradle-wrapper.jar oficial desde Gradle
# Ejecutar: chmod +x download-wrapper.sh && ./download-wrapper.sh

WRAPPER_URL="https://raw.githubusercontent.com/gradle/gradle/v8.13.0/gradle/wrapper/gradle-wrapper.jar"
DEST="gradle/wrapper/gradle-wrapper.jar"

mkdir -p gradle/wrapper

echo "Descargando gradle-wrapper.jar oficial..."
curl -L -o "$DEST" "$WRAPPER_URL"

if [ $? -eq 0 ]; then
    echo "Descarga exitosa: $DEST"
    chmod +x gradlew
    echo "Listo. Puedes ejecutar: ./gradlew assembleDebug"
else
    echo "Error en descarga. Intentando con wget..."
    wget -O "$DEST" "$WRAPPER_URL"
    if [ $? -eq 0 ]; then
        echo "Descarga exitosa con wget: $DEST"
        chmod +x gradlew
    else
        echo "Fallo la descarga. Instala curl o wget, o descarga manualmente:"
        echo "$WRAPPER_URL"
        exit 1
    fi
fi
