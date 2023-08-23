# Taller 1 AREM
Este proyecto es sobre la implementación de una arquitectura que se nos propone, donde debemos poder implementar varios aspectos como lo son tener un cache, poder atender a varios usuarios concurrentemente y hacer peticiones a una API externa. A continuación se explicara con más detalle la forma de instalar y ejecutar el proyecto.

## Iniciando


### Prerrequisitos

* Git 
* Java
* Maven
* Algun IDE, este es importante si se quiere realizar el test de concurrencia

### Instalando el proyecto

Lo primero sera traer del repositorio remoto el proyecto a la maquina local, para esto ejecutamos el siguiente comando por medio de consola.

```
git clone https://github.com/wilmer-rodriguez-r/AREM_Taller01
```

Esto creara un directorio nuevo donde accederemos y ejecutaremos el siguiente comando.

```
mvn package
```
Esto es para traer dependencias y demas que puedan ser necesarios para el proyecto, despues de esto ejecutamos el siguiente comando para poder correr tanto el servidor web como el cliente.
```
mvn exec:java
```
Eso hara que ya esten en ejecución los servicios. Para corroborar esto puedes entrar al siguiente enlace donde esta predeterminado el [servidor web](http://localhost:5500) o en caso contrario ingresa la siguiente url en tu navegador http://localhost:5500.

Si buscamos una película deberia mostrarnos algo parecido a lo siguiente.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/cbacf6c1-13f7-4a16-82e1-9aa28db8df82)

## Corriendo los tests

En este proyecto se implementaron dos tipos de test, los primeros son los Unitest que se pueden ejecutar con el siguiente comando

```
mvn test
```
Estos test se realizaron sobre el back para ver si la logica esta trabajando como se debe.

El otro tipo de test fue el que nos planteo la arquitectura, donde debiamos crear una especie de cliente con ayuda de java para que enviara multiples peticiones y asi ver la capacidad concurrente de nuestro servicio de la fachada.
Para poder ejecutar estos debemos entrar en el IDE e irnos al paquete de **clientTestConcurrency**.

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/daf67ed4-2bcb-4929-8605-b7e9bd65c881)

Luego nos dirigiremos a la clase MainTest, donde con ayuda de nuestro IDE, la ejecutaremos.
![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/0051186f-6d29-44df-80ca-981c0c8ee0c0)

Esto enviara tantas peticiones queramos, en este caso 10000, y nos devolvera el tiempo en segundo, de lo que tardo nuestro servidor fachada en responderlas todas.

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

Antes de con
![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/28672f4e-d027-4afc-9440-d1d50eecbc31)
En este caso para hacer más sencillo el despliegue de dicha arquitectura se hizo una división logica por medio de los paquetes como se muestra a continuación:

![image](https://github.com/wilmer-rodriguez-r/AREM_Taller01/assets/77862048/35488609-cc7b-4039-bd97-6f8851a9a5e7)


* El paquete **backend** hace referencia al **Web Server With REST API**
* El paquete **frontend** hace referencia al **JS Web Client**
* Y por ultimo el paquete **clientTestConcurreny** es **Concurrent Java Test Client**
### Extencibilidad

### Patrones


### Modularización

  
## Agradecimientos
