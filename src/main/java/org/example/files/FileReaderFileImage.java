package org.example.files;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;

/***
 * Clase que se encarga de leer los archivos imágenes y enviarlas al cliente.
 */
public class FileReaderFileImage extends FileReader {

    /***
     * Constructor de la clase
     * @param content_type (String) el MIME type del archivo a leer.
     */
    public FileReaderFileImage(String content_type) {
        super(content_type);
    }

    @Override
    public void readFile(URI path, Socket clientSocket) throws IOException {
        File file = new File(path.getPath());
        OutputStream out = clientSocket.getOutputStream();
        BufferedImage image = ImageIO.read(file);

        byte[] headers = FileReader.getHeaders(content_types).getBytes();
        out.write(headers);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        out.write(byteArrayOutputStream.toByteArray());
        out.close();
    }
}
