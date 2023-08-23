# Taller 1 AREM
Este proyecto es sobre la implementación de una arquitectura que se nos propone, donde debemos poder implementar varios aspectos como lo son tener un cache, poder atender a varios usuarios concurrentemente y hacer peticiones a una API externa. A continuación, se explicará con más detalle la forma de instalar y ejecutar el proyecto.

## Iniciando

### Prerrequisitos

* Git 
* Java
* Maven
* Algun IDE, este es importante si se quiere realizar el test de concurrencia

### Instalando el proyecto

Lo primero será traer del repositorio remoto el proyecto a la maquina local, para esto ejecutamos el siguiente comando por medio de consola.

```
git clone https://github.com/wilmer-rodriguez-r/AREM_Taller01
```

Esto creara un directorio nuevo donde accederemos y ejecutaremos el siguiente comando.

```
mvn package
```
Lo anterior fue para traer dependencias y demás que puedan ser necesarios para el proyecto, después de esto ejecutamos el siguiente comando para poder correr tanto el servidor web como el cliente.
```
mvn exec:java
```
Eso hará que ya estén en ejecución los servicios. Para corroborar esto puedes entrar al siguiente enlace donde esta predeterminado el [servidor web](http://localhost:5500) o en caso contrario ingresa la siguiente url en tu navegador http://localhost:5500.

Si buscamos una película debería mostrarnos algo parecido a lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/cbacf6c1-13f7-4a16-82e1-9aa28db8df82)

## Corriendo los tests

En este proyecto se implementaron dos tipos de test, los primeros son los Unitest que se pueden ejecutar con el siguiente comando.

```
mvn test
```
Estos test se realizaron sobre el back para ver si la lógica está trabajando como se debe.

El otro tipo de test fue el que nos planteó la arquitectura, donde debíamos crear una especie de cliente con ayuda de java para que enviara múltiples peticiones y así ver la capacidad concurrente de nuestro servicio de la fachada.
Para poder ejecutar estos debemos entrar en el IDE e irnos al paquete de **clientTestConcurrency**.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/daf67ed4-2bcb-4929-8605-b7e9bd65c881)

Luego nos dirigiremos a la clase MainTest, donde con ayuda de nuestro IDE, la ejecutaremos.
![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/0051186f-6d29-44df-80ca-981c0c8ee0c0)

Esto enviará tantas peticiones queramos, en este caso 10000, y nos devolverá el tiempo en milisegundos de lo que tardo nuestro servidor fachada en responderlas todas como se muestra a continuación.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/0070d655-d6b0-4912-869a-d40115ffadc1)

En caso de que algún hilo no pueda consultar el servidor fachada el test se detendrá automáticamente y marcara que la respuesta de los request fue **false**, es decir que fallaron.
## Documentacion

## Construido con

* [Maven](https://maven.apache.org/) - Administrador de dependencias
* [OMDAPI](https://www.omdbapi.com) - API externa de consulta

## Version

1.0-SNAPSHOT

## Autores

Wilmer Arley Rodríguez Ropero - [wilmer-rodriguez-r](https://github.com/wilmer-rodriguez-r)

## Licencia

GNU General Public License family

## Diseño

Para ver con más detalle el diseño, debemos ver la arquitectura que nos propusieron construir para dicho proyecto.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/28672f4e-d027-4afc-9440-d1d50eecbc31)

En este caso para hacer más sencillo el despliegue de dicha arquitectura se hizo una división lógica por medio de los paquetes como se muestra a continuación:

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/35488609-cc7b-4039-bd97-6f8851a9a5e7)


* El paquete **backend** hace referencia al **Web Server With REST API**, y es el servidor fachada que encapsula las consultas para poder preguntarle a la API externa.
* El paquete **frontend** hace referencia al **JS Web Client**, este servidor es el que se muestra al cliente por medio de la página web y simplemente se encarga de hacer peticiones a nuestro servidor fachada.
* Y por último el paquete **clientTestConcurreny** es **Concurrent Java Test Client**, este es el test que ya mencionamos anteriormente que ejecuta en varios hilos consultas concurrentemente para ver qué tan rapida es nuestra fachada al responderlas todas.
### Extensibilidad

En este caso la construcción de la arquitectura está dividida capas lo que permite facilmente extender alguna funcionalidad, por ejemplo:
 * La capa controller es la encargada de realizar y consultar por medio de los verbos de html, por lo que si se quiere agregar alguna funcionalidad adicional simplemente seria crear una nueva función para que la capa controller sea capaz de atender una consulta distinta sin necesidad de alterar las otras capas.
 * En la persistencia, si queremos que las peticiones realizadas se guarden de manera distinta simplemente modificamos dicha capa, agregando una nueva función, sin la necesidad de alterar la lógica anterior.

### Patrones
  * Singleton, esto se hizo con la intención de hacer menos acoplado el proyecto y también para que no tener varias instancias de algunas clases, como en el caso del cache donde no tiene sentido tener más de una instancia para este.
  * En la arquitectura se puede apreciar que es un estilo arquitectónico Cliente-Servidor, por lo que de fondo se premió más a los atributos de calidad de desempeño y disponibilidad introduciendo la concurrencia para que pueda el servidor fachada atender múltiples peticiones sin generar mucha latencia. 
### Modularización
Como ya mencionamos anteriormente el proyecto está dividido en capas, por lo que cada capa atiende una función en específico.
* Service, es la que se encarga de comunicarse directamente con la persistencia para que otras capas no accedan directamente a esta.
* Controller, es la que ofrece los REST endpoints o interfaces de la API.
* Persistence, esta se encarga de exponer las funcionalidades de la base de datos, en este caso sería con la clase que guarda las consultas en cache.

Adicionalmente en cada clase se construyó pensando en el principio de single responsability para cada función, lo que permite que sea más modularizado.


También para respetar la arquitectura que se nos planteó, se hizo que el Web Client ejecutara en un puerto distinto al Backend para poder tener separada lo mejor posible a nivel lógico el proyecto.

## Agradecimientos

* Luis Daniel Benavides Navarro

## Referencias

[1] 	E. Alberto, "medium," 21 January 2018. [Online]. Available: https://medium.com/@erwinalberto/part-3-spring-boot-components-controller-service-persistence-dao-7e8c8f1c844b. [Accessed 23 August 2023].

[2] 	baeldung, "Baeldung," 15 June 2023. [Online]. Available: https://www.baeldung.com/a-guide-to-java-sockets.

[3] 	B. N. L. Daniel, "Introducción a esquemas de nombres, redes, clientes y servicios con Java," p. 18, 20 Agosto 2020.


