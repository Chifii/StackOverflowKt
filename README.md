# Stack Overflow App
## Descripcion
Es una aplicacion de prueba basada en la [API de stack overflow](https://api.stackexchange.com/docs). La idea original era usar el [auth de stack overflow](https://api.stackexchange.com/docs/authentication) para autenticarse y en base a eso poder consultar los detalles del usuario como por ejemplo sus medallas, respuestas, preguntas, etc...

## Features
- Buscar usuarios en stack overflow
- Consulta sobre sus medallas e informacion

## Tech
Entre varias tecnologias que se usaron, las mas relevantes son:
- [Kotlin] - 100% hecho en Kotlin!
- [Detekt] - Analizador de codigo estatico
- [Leak Canary] - Para problemas de leaks de memoria
- [Android]
- [JUnit]
- [Retrofit]
- [OkHttp]

## Development
En cuanto al desarrollo, el inicio fue bastante sencillo y rapido. Decidi empezar por la pantalla de medallas, la cual me parecia la mas sencilla y de hecho lo fue.

La pantalla de medallas consistia basicamente en una pantalla con un Recycler View el cual mostraba las medallas de stack overflow. Ademas tenia 3 botones que eran para el filtrado, ya sea por rango, nombre o si queriamos ascendente/decendente.

Luego empezo el problema. **El login**

Con el Login surgio el problema de la [api de stack overflow](https://api.stackexchange.com/docs/authentication). Que si bien en su documentacion parece bastante sencilla no lo es para la implementacion mobile. El mayor problema que surgio aca fue que la redireccion no era posible de manera "natural", la auth de stack consiste en 3 puntos importantes:

1. En la division de flujos (Implicito/Explicito)
2. El registro de la app
3. Redireccion

La api de stack overflow nos devuelve un codigo html totalmente funcional cuando hacemos la consulta en cualquier flujo (Implicito/Explicito). Lo que hace es mostrar su dialogo de autenticacion el cual te permite logearte de varias formas distintas (facebook, google, github, mail) y luego en caso de que sea exitoso te va a redireccionar al dominio que vos seleccionaste. En nuestro caso eso es el primer problema, no tenemos dominio, somos una app mobile, por lo que se probo pasandole un deeplink (para navegacion), registrar una app nueva en 0auth, registrar la app en Stack. Ninguna funciono.

Luego, salio la brillante idea de sobreescribir la webview en la que se levantaba y mostraba el de stack overflow y en un punto logramos dar con el clavo, pero luego para poder avanzar teniamos que hacer la segunda parte, solicitar el `access_token`. En este punto no encontre solucion viable, al menos en el corto plazo que tenia para terminar esta app. Por lo que se decidio ir por el enfoque que esta actualmente. 

En la doc de stack overflow podemos ver un endpoint que nos devuelve los usuarios, y estos pueden ser buscados y encontrados por el nombre. Eso nos devuelve la data del usuario y con esa data usando el `userId` podemos consultar todas las medallas del mismo. 

En conclusion, lamentablemente la app no tiene el login de stack overflow, pero si tiene la informacion de los usuarios y sus medallas. Incluso el filtro por rango de las mismas.

## Apk
Para poder descargar el APK tendran que acceder al siguiente [google drive](https://drive.google.com/drive/folders/1VqVOWWBy59ShPUO1MoxEeQ_nJRNYt8Z3?usp=drive_link)

## To improve
Basicamente los puntos a mejorar son:
1. Test Unitarios: Mas completos y que funcionen bien (Estos fallan por un tema de configuracion de las corrutinas)
2. Limpieza de codigo avanzada: Correr detekt detenidamente y solucionar todos los warnings leves que quedaron. Tambien revisar los metodos y cada clase para poder cumplir correctamente con el principio S.O.L.I.D
3. Optimizacion de imagenes y llamadas a la A.P.I
4. Optimizacion del uso de memoria
5. Implementacion completa de R8 y Proguard para el release de la app de manera segura
6. Implementacion de un CI/CD en Git

## License
**Licencia ? Que es eso ?**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Kotlin]: <https://kotlinlang.org/>
   [git-repo-url]: <https://github.com/Chifii/StackOverflowKt.git>
   [Android]: https://developer.android.com/studio
   [Detekt]: https://detekt.dev/
   [Leak Canary]: https://square.github.io/leakcanary/
   [JUnit]: https://junit.org/junit5/
   [Retrofit]: https://square.github.io/retrofit/
   [OkHttp]: https://square.github.io/okhttp/
