package org.example;

import org.example.files.exception.ExceptionFile;

public interface ServiceControllerInterface {
    public void send(String str) throws ExceptionFile;
}
