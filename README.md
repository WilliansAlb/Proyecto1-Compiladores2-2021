<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">MUSICOMPI</h3>
</p>
# MUSICOMPI

Musicompi es un proyecto del curso de Organización de Lenguajes y Compiladores, que abarca el análisis de texto para la creación de pistas y listas. Cuenta también con un módulo cliente, escrito en Kotlin, cuya funcionalidad es enviarle solicitudes al módulo servidor, una aplicación de escritorio escrita en Java.

## Modulo servidor

Aplicación de escritorio escrita en Java, usando el JDK 8. Usa las siguientes librerías:
- RichTextFX: para darle estilo a los editores de texto para los lenguajes de pista y lista.
- Midi: para reproducir las distintas notas.
- Line Chart FX: para mostrar las frecuencias que se reproducen.
- JFlex: para el análisis léxico de los textos que se envíen para la creación de pistas o listas.
- Cup: para el análisis sintáctico de los tokens que JFlex devuelva.

## Modulo cliente

Aplicación móvil escrita con Kotlin para dispositivos con SDK 21 o Lollipop. Usa las siguientes librerías.
- MediaPlayer: para lograr reproducir las notas que el usuario presione al momento de estar en la vista de piano.

## Datos de la conexión
Para la conexión entre las aplicaciones se utilizaron sockets, montandose el servidor en la aplicación de escritorio y el cliente en la aplicación movil. Un pequeño punto a tomar en cuenta de dicha conexión, es que para que no existan errores se debe comprobar el host a la que la aplicación móvil hace las solicitudes. 

Lo anterior se logra fácilmente ejecutando el siguiente comando en una terminal

```
$ ipconfig
```
Del resultado solo nos importará la Dirección IPv4. La copiamos y nos dirigimos al proyecto de Android Studio, y en la clase Cliente, cambiamos el host, pegando la dirección que copiamos previamente.
## Contribución
Todo comentario es bueno, por lo tanto se agradecerían los pulls request.

## License
[MIT](https://choosealicense.com/licenses/mit/)
