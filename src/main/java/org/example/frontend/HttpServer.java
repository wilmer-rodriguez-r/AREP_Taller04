package org.example.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer extends Thread {

    public int port;

    public HttpServer(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    new HttpRequestThread(serverSocket.accept()).start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 35000.");
        }
    }

    /***
     * Obtiene el html que mostrara en el browser
     * @return un String con el html y headers
     */
    public static String getResponse(){
        return "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: text/html \r\n"
                + "\r\n"
                +"<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Movie Search</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <script src=\"https://code.jquery.com/jquery-3.6.4.min.js\"\n" +
                "        integrity=\"sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=\" crossorigin=\"anonymous\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <script>\n" +
                "apiclient = (function() {\n" +
                "\n" +
                "    let _apiURL = \"http://localhost:35000\";\n" +
                "\n" +
                "    return {\n" +
                "        getMovieByTittle:function(tittle, callback){\n" +
                "            $.get(`${_apiURL}/?${tittle}`, (data) => {\n" +
                "                callback(data);\n" +
                "            }).fail();\n" +
                "\t\t},\n" +
                "    }\n" +
                "})()"+
                "        </script>\n" +
                "        <script>\n" +
                "app = (function(api){\n" +
                "    let _publicFunctions = {};\n" +
                "\n" +
                "    let _renderSearch = function(data) {\n" +
                "        $(document).ready(() => {\n" +
                "            $(\"#data tbody\").text(\"\");\n" +
                "            Object.keys(data).forEach(function(k){\n" +
                "                let row = `<tr><td>${k}</td><td>${data[k]}</td></tr>`;\n" +
                "                $(\"#data tbody\").append(row);\n" +
                "            });\n" +
                "        });\n" +
                "    }\n" +
                "\n" +
                "    _publicFunctions.searchMovieByTittle = function(tittle) {\n" +
                "        api.getMovieByTittle(tittle, _renderSearch);\n" +
                "    }\n" +
                "\n" +
                "    return _publicFunctions;\n" +
                "})(apiclient);" +
                "        </script>\n" +
                "        <h1>Search Movie</h1>\n" +
                "        <form>\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"app.searchMovieByTittle($('#name').val())\">\n" +
                "        </form> \n" +
                "        <div></div>\n" +
                "        <table id= \"data\">\n" +
                "            <thead>\n" +
                "                <th>Key</th>\n" +
                "                <th>Value</th>\n" +
                "            </thead>\n" +
                "            <tbody></tbody>\n" +
                "        </table>\n" +
                "    </body>\n" +
                "</html>";
    }

    /***
     * Clase embebida en HttpServer que se ejecuta para mantener la concurrencia del servidor
     */
    private static class HttpRequestThread extends Thread{
        private final Socket clientSocket;

        /***
         * Constructor de la clase HttpRequestThread
         * @param socket un Socket que le otorga el servidor para comunicarse con el
         */
        public HttpRequestThread(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String outputLine;

                outputLine = getResponse();
                out.println(outputLine);

                out.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Oopss, something was wrong");
            }
        }
    }
}