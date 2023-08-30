package org.example.files.filesFactory;

import org.example.files.FileReader;

public interface FilesFactoryInterface {

    FileReader getInstance(String resource) throws Exception;
}
