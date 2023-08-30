package org.example.files.filesFactory;

import org.example.files.FileReader;
import org.example.files.FileReaderFileImage;
import org.example.files.FileReaderFileText;
import org.example.files.exception.ExceptionFile;
import java.util.*;
import java.util.regex.*;

/***
 * Implementación de la fábrica para que retorne las clases correspondientes
 */
public class FilesFactoryImplementation implements FilesFactoryInterface {

    private final Map<String, String> contentTypeMap = new HashMap<String, String>() {{
        put(".html", "text/html");
        put(".js", "text/javascript");
        put(".css", "text/css");
        put(".jpg", "image/jpg");
        put(".png", "image/javascript");
        put(".gif", "image/gif");
    }};
   private String contentType;
    @Override
    public FileReader getInstance(String resource) throws ExceptionFile {
        if (matchRegex(".(jpg|png|ico|gif)$", resource)) {
            return new FileReaderFileImage(contentType);
        }
        if(matchRegex(".(html|js|css)$", resource)) {
            return new FileReaderFileText(contentType);
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
            contentType = contentTypeMap.get(matcher.group());
            return true;
        }
        return false;
    };
}
