package org.example.miniSpring.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación para mapear los post
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PostMapping {

    /**
     * Valor asociado a la anotación.
     * @return String con el valor.
     */
    String value();
}
