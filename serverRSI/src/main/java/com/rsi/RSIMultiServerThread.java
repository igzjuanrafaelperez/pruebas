package com.rsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RSIMultiServerThread extends Thread {
    private Socket socket = null;

    public RSIMultiServerThread(Socket socket) {
        super("RSIMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        // Thread id
        long threadId = Thread.currentThread().getId();

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            System.out.println("Thread #" + threadId + "# start.");
            String inputLine, outputLine;
            BeanFrame beanFrame;
            RSIProtocol rsiProtocol = new RSIProtocol();

            while ((inputLine = in.readLine()) != null) {

                try {
                    beanFrame = rsiProtocol.processInput(inputLine);
                    System.out.println(beanFrame);
                    // Response OK
                    out.println("OK");
                }catch (FormatFrameException e) {

                    System.out.println("Error");
                    // Response KO
                    out.println("KO");
                }

            }

            socket.close();
            System.out.println("Thread #" + threadId + "# finish.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
