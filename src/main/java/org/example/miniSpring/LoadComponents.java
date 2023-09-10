package org.example.miniSpring;

import org.example.handlers.Request;
import org.example.miniSpring.annotations.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.io.*;

/**
 * Clase que carga los componentes de un directorio dado.
 */
public class LoadComponents {

    /**
     * Guarda los endpoint con sus respectivos métodos.
     */
    public static Map<String, Method> serviceGet = new HashMap<>();
    /**
     * Guarda los endpoint con sus respectivos métodos.
     */
    public static Map<String, Method> servicePost = new HashMap<>();

    /**
     * Busca entre una lista de clases las que posean anotaciones para cargarlas.
     * @param classes List que son todas las clases del paquete.
     * @throws ClassNotFoundException Cuando la clase no se encuentra
     */
    public static void loadAnnotations(List<Class<?>> classes) throws ClassNotFoundException {
        for(Class<?> classObject: classes) {
            Class<?> c = Class.forName(classObject.getName());
            if (c.isAnnotationPresent(Component.class)) {
                Method[] methods = c.getDeclaredMethods();
                for (Method method: methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        String endpoint = method.getAnnotation(GetMapping.class).value();
                        serviceGet.put(endpoint, method);
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        String endpoint = method.getAnnotation(PostMapping.class).value();
                        servicePost.put(endpoint, method);
                    }
                }
            }
        }
    }

    /**
     * A partir del path del paquete principal, se busca recursivamente las clases.
     * @param path el path del paquete.
     * @return List de clases con todas las clases que se encontraron.
     * @throws ClassNotFoundException Cuando la clase no se encuentra.
     */
    public static List<Class<?>> getClasses(String path) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String packageNamePath = path.replace(".", "/");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        URL packageURL = classLoader.getResource(packageNamePath);
        assert packageURL != null;
        String packagePath = packageURL.getPath();
        File file = new File(packagePath);
        getClasses(classLoader, classes, file, path.split("\\.")[0]);

        return classes;
    }

    /**
     * Obtiene todas las clases y las carga.
     * @param classLoader Classloader que ayuda a cargar las clases.
     * @param classes List con todas las clases que se hayan encontrado.
     * @param file File donde estarán las clases.
     * @param path String con el path actual.
     * @throws ClassNotFoundException Cuando la clase no se encuentra.
     */
    public static void getClasses(ClassLoader classLoader, List<Class<?>> classes, File file, String path) throws ClassNotFoundException {
        String fileName = file.getName();
        if (fileName.contains(".class")) {
            classes.add(classLoader.loadClass(path + "." + fileName.replace(".class", "")));
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                String newPath = path + "." + file.getName();
                getClasses(classLoader, classes, f, newPath);
            }
        }
    }

    /**
     * Método que se encarga de invocar los métodos solicitados.
     * @param request Request con el contenido de la solicitud realizada.
     * @return byte[] con los datos a enviar.
     * @throws InvocationTargetException Cuando no se pueda invocar al método.
     * @throws IllegalAccessException Cuando no se pueda acceder al método.
     */
    public static byte[] invokeMethod(Request request) throws InvocationTargetException, IllegalAccessException {
        String endpoint = request.getEndpoint();
        String response;
        if (request.isFile()) {
            response = (String) serviceGet.get("/file").invoke(null, request);
            return Base64.getDecoder().decode(response);
        } else if(request.getVerb().equals("GET")) {
            response = (String) serviceGet.get(endpoint).invoke(null, request);
        } else {
            response = (String) servicePost.get(endpoint).invoke(null, request);
        }
        return response.getBytes();
    }
}
