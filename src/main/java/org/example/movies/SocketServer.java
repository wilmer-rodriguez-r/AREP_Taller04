package org.example.movies;


import java.io.IOException;
import java.net.ServerSocket;

/***
 * Clase que se encarga de iniciar el socket server y adicionalmente crear los clients
 */
public class SocketServer extends Thread {

    private final int port;

    /***
     * Constructor de la clase SocketServer
     * @param port un int que recibe el puerto por donde correrá el Server Socket
     */
    public SocketServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    new ClientSocket(serverSocket.accept()).start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 35000.");
        }
    }


}
