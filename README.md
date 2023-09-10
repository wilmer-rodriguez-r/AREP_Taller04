# Taller 3 AREP
En este taller se creó un proyecto similar al framework de Spring, esto es con el objetivo de que apartir de anotaciones podamos crear métodos que se puedan llamar para que resuelvan peticiones GET y POST.
## Iniciando

### Prerrequisitos

* Git 
* Java
* Maven
* PostMan(Opcional)

### Instalando el proyecto

Lo primero será traer del repositorio remoto el proyecto a la máquina local, para esto ejecutamos el siguiente comando por medio de consola.

```
git clone https://github.com/wilmer-rodriguez-r/AREP_Taller04.git
```

Esto creará un directorio nuevo donde accederemos y ejecutaremos el siguiente comando.

```
mvn package
```
Lo anterior fue para traer dependencias y demás que puedan ser necesarios para el proyecto, después de esto ejecutamos el siguiente comando para poder correr tanto el servidor web junto a MiniSpring.
```
java -cp target/classes org.example.Main
```
Eso hará que ya estén en ejecución los servicios. Para corroborar esto puedes entrar al siguiente enlace donde está predeterminado el [servidor web](http://localhost:5500/index.html) o en caso contrario ingresa la siguiente url en tu navegador http://localhost:5500/index.html.

Al ingresar verás lo siguiente:

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/784094e9-4278-47f8-a3d3-255e5ca5f97f)

Si en algún momento escribes mal la URL puede que tengas el siguiente mensaje:
![image](https://github.com/wilmer-rodriguez-r/AREP_Taller02/assets/77862048/b5c2c1c7-e286-4662-a58f-19e89ac0bf29)


## Corriendo los tests

En este proyecto se hicieron test para corroborar el funcionamiento a la hora de leer los archivos que se necesitan enviar, también hay test que corroboran el funcionamiento del MiniSpring y por último los test que verifican la lógica de movies. Para correr los test se ejecuta el siguiente comando.

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

En la clase ExampleController encontraremos el siguiente código.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/e795779a-0159-4dfa-abd7-dd8af7f59948)

Como se puede observar poseemos dos mapeos, uno para GET y otro para POST.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/e838bcfb-01c6-4082-9224-5b54feb92894)

Ahora, si ingresamos en el siguiente enlace http://localhost:5500/ejemplo.html veremos la siguiente página.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/7ef3d219-eaac-439d-b87c-469214c12405)

Vemos que tenemos un GET y otro para POST.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/887cc7fa-3f0b-40b3-974c-1b97fcbabbf6)

Si intentamos buscar una persona nos podrá salir el siguiente aviso, puesto que aún no tenemos agregada ninguna.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/3f1a58f8-af73-400e-b0e3-bd579b319184)

Para solucionar esto vamos donde el post y postearemos una nueva persona.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/83bcecdc-7219-434a-9894-88730ffc1617)

Como podemos ver no dice que la persona fue agregada.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/c982c8c9-4aa9-4c65-a48c-7974eb2e67e8)

Para probar esto, podemos pedir la persona.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/7682da01-2eb4-45cc-a04e-59d03c8fe212)

Y observaremos que nos devuelve tanto el nombre como el apellido de esta.

![image](https://github.com/wilmer-rodriguez-r/AREP_Taller03/assets/77862048/91ef55e2-a806-481c-9124-ec80b04a5ffd)

Y por medio de la consola podemos corroborar que las peticiones que se han realizarón son GET y POST.

## Otros Sistemas Operativos.

Por último con ayuda de Kali Linux correremos nuestro proyecto para ver si en diferentes sistemas operativos puede ejecutarse.

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

## Referencias

[1] 	B. N. L. Daniel, "Introducción a esquemas de nombres, redes, clientes y servicios con Java," p. 18, 20 Agosto 2020.


