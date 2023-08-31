package org.example;

import java.util.*;

public class MiniSpark {

    static Map<String, ServiceControllerInterface> services = new HashMap<>();

    public static void get(String query, ServiceControllerInterface service) {
        services.put(query, service);
    }

    public static ServiceControllerInterface search(String query) {
        System.out.println(services.keySet());
        return services.get(query);
    }
}
