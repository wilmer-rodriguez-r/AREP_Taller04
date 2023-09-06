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

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/784094e9-4278-47f8-a3d3-255e5ca5f97f)

Si en algún momento escribes mal la URL puede que tengas el siguiente mensaje:
![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/b5c2c1c7-e286-4662-a58f-19e89ac0bf29)


## Corriendo los tests

En este proyecto se hicieron test para corroborar el funcionamiento a la hora de leer los archivos que se necesitan enviar, también hay test que corroboran el funcionamiento del MiniSpark y por último los test que verifican la lógica de movies. Para correr los test se ejecuta el siguiente comando.

```
mvn test
```
Al pasar todos los test debe verse alto parecido a lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/cf47841e-deed-42cc-a0c3-70c7b2ea92c9)

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

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/e795779a-0159-4dfa-abd7-dd8af7f59948)

Como podemos ver al principio definimos una estructura de persistencia para nuestras peticiones.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/e838bcfb-01c6-4082-9224-5b54feb92894)

En este caso guardamos una persona con su apellido. Si yo quisiera tener los datos de esta persona podria definir el siguiente GET.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/7ef3d219-eaac-439d-b87c-469214c12405)

Donde apartir del endpoint */persons* y el parametro *{name}* obtendre dicha persona y la retornare. Si lo buscamos por navegador con el siguiente link http://localhost:5500/persons?name=wilmer , obtendremos lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/887cc7fa-3f0b-40b3-974c-1b97fcbabbf6)

Si yo intentara buscar otra persona como *laura* la petición fallara puesto que no existe en la persistencia. http://localhost:5500/persons?name=laura.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/3f1a58f8-af73-400e-b0e3-bd579b319184)

Ahora bien si yo quisiera agregar una nueva personsa con un POST tendria que definir primero el siguiente endpoint.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/83bcecdc-7219-434a-9894-88730ffc1617)

Donde apartir del body que reciba lo incluire en la estructura de persistencia que defini. Para ver como funciona este endpoint nos apoyaremos en Postman.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/c982c8c9-4aa9-4c65-a48c-7974eb2e67e8)

Como podemos ver vamos a crear a *laura* para poder consultarla posteriormente. Al enviar el post veremos la siguiente respuesta.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/7682da01-2eb4-45cc-a04e-59d03c8fe212)

Lo que significa que nuestro post ha sido exitoso, ahora consultaremos el siguiente link http://localhost:5500/persons?name=laura y podremos observar lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/91ef55e2-a806-481c-9124-ec80b04a5ffd)

Como podemos ver nuestro MiniSpark es capaz de recibir peticiones Get y Post de manera adecuada.
Por ultimo, si quisiera enviar algun archivo por un endpoint puedes usar el siguiente ejemplo.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/a642e76f-3358-4b59-8237-f39d9df660a1)

Y al ingresar por http://localhost:5500/gato podremós ver el archivo cargado.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/a3886f6f-a80c-4ee4-886f-73e6f1761a88)

Ya con esto estamos seguros que nuestro proyecto funciona correctamente.

## Otros Sistemas Operativos.

Por ultimo con ayuda de Kali Linux correremos nuestro proyecto para ver si en diferente sistemas operativos puede ejecutarse.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/fa01d3d1-fc5a-40ce-8459-baa11252e778)

Ahora intentaremos consultar.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/06f79c7d-2053-4f18-94a6-5d78aa1d2528)

Como podemos ver nuestro proyecto funciona en distintos sistemas operativos sin problema.

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
* [perwendel](https://github.com/perwendel)

## Referencias

[1] 	B. N. L. Daniel, "Introducción a esquemas de nombres, redes, clientes y servicios con Java," p. 18, 20 Agosto 2020.


