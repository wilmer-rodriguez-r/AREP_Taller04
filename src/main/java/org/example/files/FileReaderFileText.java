package org.example.files;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;
/***
 * Clase qu se encarga de leer los archivos texto y enviarlos al cliente.
 */
public class FileReaderFileText extends FileReader {

    /***
     * Constructor de la clase
     * @param content_type (String) el MIME type del archivo a leer.
     */
    public FileReaderFileText(String content_type) {
        super(content_type);
    }


    @Override
    public void readFile(URI path, Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        File file = new File(path.getPath());
        StringBuilder outputLine = new StringBuilder();
        if (file.exists()) {
            Scanner line = new Scanner(file);
            while (line.hasNext()) {
                outputLine.append(line.nextLine()).append("\n");
            }
            line.close();
        }
        out.println(FileReader.getHeaders(content_types) + outputLine);
        out.close();
    }
}
