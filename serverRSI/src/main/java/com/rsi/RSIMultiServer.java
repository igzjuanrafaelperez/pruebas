package com.rsi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * Pruebas de multi servicio RSI
 */
public class RSIMultiServer {

    // RSI port
    public static final int PORT_RSI = 1556;

    public static void main(String[] args) throws IOException {

        // start the server
        startServer();
    }

    /**
     * Method that start the RSI multiserver
     */
    private static void startServer() {
        int portNumber = PORT_RSI;
        boolean listening = true;

        try (
                ServerSocket serverSocket = new ServerSocket();
        ) {

            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(portNumber));

            while (listening) {
                new RSIMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
