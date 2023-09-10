package org.example.miniSpring.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación para los métodos que mapean el get
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {

    /**
     * Valor asociado a la anotación.
     * @return String con el valor.
     */
    String value();
}
