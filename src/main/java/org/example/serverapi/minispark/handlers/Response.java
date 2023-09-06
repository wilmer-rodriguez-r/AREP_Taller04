package org.example.serverapi.minispark.handlers;

import org.example.serverapi.files.FileText;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.minispark.MiniSpark;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que se encarga de armar la respuesta respectiva a la petición
 */
public class Response {

    private final Map<String, String> contentTypes = new HashMap<String, String>() {{
        put(".html", "html");
        put(".js", "text/javascript");
        put(".css", "text/css");
        put(".jpg", "image/jpg");
        put(".png", "image/png");
        put(".gif", "image/gif");
        put(".ico", "image/ico");
    }};

    private final String query;
    private String status = "200";
    private byte[] response;
    private byte[] header;
    private byte[] body = new byte[1024];
    private String contentType = "text/plain";

    /**
     * Constructor de la clase.
     * @param query String que es el nombre del recurso a enviar.
     */
    public Response(String query) {
        this.query = query;
        setContentType();
        getHeaders();
    }

    /**
     * Método que guarda el body de la respuesta.
     * @param body Object el body de la respuesta.
     */
    public void setBody(Object body) {
        try {
            this.body = (byte[]) body;
            assembler();
        } catch (ClassCastException e) {
            this.body = body.toString().getBytes();
            assembler();
        }
    }

    /**
     * Obtiene la respuesta completa para enviar.
     * @return byte[] el contenido de la respuesta en byte.
     */
    public byte[] getResponse() {
        return response;
    }

    /**
     * Guarda el tipo de contenido de la respuesta.
     */
    private void setContentType() {
        Pattern pattern = Pattern.compile(".(html|js|css|jpg|png|gif|ico)$");
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            contentType = contentTypes.get(matcher.group());
        }
        assembler();
    }

    /**
     * Guarda el tio de contenido de la respuesta
     * @param contentType String que corresponde al MIME del contenido.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
        assembler();
    }

    /***
     * Headers para retornar la respuesta
     */
    private void getHeaders() {
        String header;
        if (!Objects.equals(status, "404")) {
            header = "HTTP/1.1 " + status + " OK \r\n"
                    + "Access-Control-Allow-Origin: *\r\n"
                    + "Content-Type: " + contentType + "\r\n"
                    + "\r\n";
        } else {
            header = "HTTP/1.1 404 Not Found \r\n" +
                    "Content-Type: text/html \r\n" +
                    "\r\n";
        }
        this.header = header.getBytes();
    }

    /**
     * Método que se encarga de ensamblar la respuesta a enviar.
     */
    private void assembler() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getHeaders();
            byteArrayOutputStream.write(header);
            byteArrayOutputStream.write(body);
            response = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que ensambla una mala respuesta para enviar.
     * @throws ExceptionFile cuando no se puede leer los archivos.
     */
    public void badRequest() throws ExceptionFile {
        try {
            status = "404";
            FileText fileText = new FileText();
            this.body = fileText.readFile(URI.create(MiniSpark.path + "/NotFound.html"));
            assembler();
        } catch (IOException e) {
            throw new ExceptionFile(ExceptionFile.NOT_FOUND);
        }
    }
}
