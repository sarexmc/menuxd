# Sarexsafio 4 - Menú de pausa (Fabric 1.21.1)

Al presionar ESC en el juego aparece:
1. Logo de Sarex Studio
2. Logo de Sarexsafio 4 (grande)
3. Botones: Volver al juego / Opciones / Desconectarse

## Compilar
Requiere JDK 21.
    gradle wrapper --gradle-version 8.10   (una sola vez, si no tienes el wrapper)
    ./gradlew build          (Windows: gradlew.bat build)
El .jar queda en build/libs/sarexsafio4-pausemenu-1.0.0.jar -> ponlo en la carpeta mods (Fabric Loader 0.15.11+).

## Cambiar tamaño del logo
En SarexPauseScreen.java: `mainH = (int) (H * 0.40);` (0.40 = 40% de la altura de la pantalla).
Imágenes: src/main/resources/assets/sarexsafio4/textures/gui/

## Compilar con GitHub (sin instalar nada)
Sube esta carpeta a un repositorio de GitHub. En la pestaña Actions corre "build"
y al terminar descarga el .jar desde "Artifacts".
