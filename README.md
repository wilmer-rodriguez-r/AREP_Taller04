# Taller 3 AREP
En este taller se creó un tipo de servidor que sea capaz de controlar peticiones de tipo GET y POST, esto está basado en el proyecto que es conocido como [Spark](https://sparkjava.com).

## Iniciando

### Prerrequisitos

* Git 
* Java
* Maven
* PostMan(Opcional)

### Instalando el proyecto

Lo primero será traer del repositorio remoto el proyecto a la máquina local, para esto ejecutamos el siguiente comando por medio de consola.

```
git clone https://github.com/wilmer-rodriguez-r/AREP_Taller03.git
```

Esto creará un directorio nuevo donde accederemos y ejecutaremos el siguiente comando.

```
mvn package
```
Lo anterior fue para traer dependencias y demás que puedan ser necesarios para el proyecto, después de esto ejecutamos el siguiente comando para poder correr tanto el servidor web como el cliente.
```
mvn exec:java
```
Eso hará que ya estén en ejecución los servicios. Para corroborar esto puedes entrar al siguiente enlace donde está predeterminado el [servidor web](http://localhost:5500/index.html) o en caso contrario ingresa la siguiente url en tu navegador http://localhost:5500/index.html.

Al ingresar verás lo siguiente:

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/69bee8ff-2dcb-4eee-a2e5-1ab4065a8d20)

Si en algún momento escribes mal la URL puede que tengas el siguiente mensaje:
![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/b5c2c1c7-e286-4662-a58f-19e89ac0bf29)


## Corriendo los tests

En este proyecto se hicieron test para corroborar el funcionamiento a la hora de leer los archivos que se necesitan enviar, también hay test que corroboran el funcionamiento del MiniSpark y por último los test que verifican la lógica de movies. Para correr los test se ejecuta el siguiente comando.

```
mvn test
```
Al pasar todos los test debe verse alto parecido a lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/40802f9d-c711-4bcc-8d75-2741a1af148a)


## Documentación
Primero debemos ejecutar el siguiente comando para crear la documentación.
```
mvn javadoc:javadoc
```
En la siguiente ruta desde nuestra carpeta del proyecto podemos encontrar la documentación.

```
./target/site/apidocs
```
Si ingresamos a esta podemos ver que hay un index.html que al abrir nos mostrara la siguiente pagina.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/51c5b988-4b01-4a6d-9143-077fc6d6bab1)

## Diseño

En este caso tenemos dos "servidores" corriendo en paralelo, el de movies que se encarga de todo lo relacionado con peticiones y demás a la API externa, y el de MiniSpark que se encarga de todas las peticiones relacionadas con el servidor API.

Esto se hace con el fin de tener modularizado cada componente y poder trabajar sin tener riesgos de interferir en la lógica de cada uno.

## Como usar

Para entender un poco como funciona nuestro MiniSpark lo explicaremos con un ejemplo.

En la clase Main encontraremos el siguiente método.



## Construido con

* [Maven](https://maven.apache.org/) - Administrador de dependencias

## Version

1.0-SNAPSHOT

## Autores

Wilmer Arley Rodríguez Ropero - [wilmer-rodriguez-r](https://github.com/wilmer-rodriguez-r)

## Licencia

GNU General Public License family

## Agradecimientos

* Luis Daniel Benavides Navarro

## Referencias

[1] 	B. N. L. Daniel, "Introducción a esquemas de nombres, redes, clientes y servicios con Java," p. 18, 20 Agosto 2020.


