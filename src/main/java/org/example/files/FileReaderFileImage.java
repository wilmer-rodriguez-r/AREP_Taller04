package org.example.files;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;

public class FileReaderFileImage extends FileReader {

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
