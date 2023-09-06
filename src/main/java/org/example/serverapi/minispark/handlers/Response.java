package org.example.serverapi.minispark.handlers;

import org.example.serverapi.files.FileText;
import org.example.serverapi.minispark.MiniSpark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final String resource;
    private String status = "200";
    private byte[] response;
    private byte[] header;
    private byte[] body = new byte[1024];
    private String contentType = "text/plain";

    public Response(String resource) {
        this.resource = resource;
        setContentType();
        getHeaders();
    }

    public void setBody(Object body) {
        try {
            this.body = (byte[]) body;
            assembler();
        } catch (ClassCastException e) {
            this.body = body.toString().getBytes();
            assembler();
        }
    }

    public byte[] getResponse() {
        return response;
    }

    private void setContentType() {
        Pattern pattern = Pattern.compile(".(html|js|css|jpg|png|gif|ico)$");
        Matcher matcher = pattern.matcher(resource);
        if (matcher.find()) {
            contentType = contentTypes.get(matcher.group());
        }
        assembler();
    }
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

    public void badRequest() throws IOException {
        status = "404";
        FileText fileText = new FileText();
        this.body = fileText.readFile(URI.create(MiniSpark.path + "/NotFound.html"));
        assembler();
    }
}
