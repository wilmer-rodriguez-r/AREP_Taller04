package org.example.miniSpring;

import org.example.handlers.Request;
import org.example.handlers.Response;
import org.example.miniSpring.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class LoadComponents {
    public static Map<String, Method> serviceGet = new HashMap<>();
    public static Map<String, Method> servicePost = new HashMap<>();

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

    public static List<Class<?>> getClasses(String path) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String packageNamePath = path.replace(".", "/");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        URL packageURL = classLoader.getResource(packageNamePath);
        assert packageURL != null;
        String packagePath = packageURL.getPath();
        java.io.File file = new java.io.File(packagePath);
        getClasses(classLoader, classes, file, path.split("\\.")[0]);

        return classes;
    }

    public static void getClasses(ClassLoader classLoader, List<Class<?>> classes, java.io.File file, String path) throws ClassNotFoundException {
        String fileName = file.getName();
        if (fileName.contains(".class")) {
            classes.add(classLoader.loadClass(path + "." + fileName.replace(".class", "")));
        }
        if (file.isDirectory()) {
            java.io.File[] files = file.listFiles();
            assert files != null;
            for (java.io.File f : files) {
                String newPath = path + "." + file.getName();
                getClasses(classLoader, classes, f, newPath);
            }
        }
    }

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
