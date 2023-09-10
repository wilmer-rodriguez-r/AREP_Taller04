package org.example.files.filesFactory;

import org.example.files.FileHandler;
import org.example.files.FileHandlerImage;
import org.example.files.FileHandlerText;
import org.example.files.exception.ExceptionFile;

import java.util.*;
import java.util.regex.*;

/***
 * Implementación de la fábrica para que retorne las clases correspondientes
 */
public class FileFactoryImpl implements FileFactoryInterface {

    private final List<String> typeImage = new ArrayList<>() {{
        add(".jpg");
        add(".png");
        add(".ico");
        add(".gif");
    }};
    private String type;
    @Override
    public FileHandler getInstance(String resource) throws ExceptionFile {
        if (matchRegex(".(jpg|png|ico|gif)$", resource)) {
            return new FileHandlerImage(type);
        }
        if(matchRegex(".(html|js|css)$", resource)) {
            return new FileHandlerText();
        }
        throw new ExceptionFile(ExceptionFile.NOT_FOUND);
    }

    /***
     * Se encarga de validar si el string cuenta con la expresión regular dada
     * @param regex (String) expresión regular que validara el string dado
     * @param resource (String) nombre del recurso que se validara
     * @return (boolean) un valor boolean.
     */
    private Boolean matchRegex(String regex, String resource) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(resource);
        if (matcher.find()) {
            if (typeImage.contains(matcher.group())) {
                type = matcher.group();
            }
            return true;
        }
        return false;
    }
}
