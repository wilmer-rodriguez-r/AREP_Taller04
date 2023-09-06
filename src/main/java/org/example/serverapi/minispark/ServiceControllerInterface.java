package org.example.serverapi.minispark;

import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;

import java.io.IOException;

public interface ServiceControllerInterface {

    Object readFile(Request request, Response response) throws ExceptionFile, IOException;

}
