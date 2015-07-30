package com.rsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FrontelClient {

    // RSI host
    public static final String HOST_INTELYGENZ = "192.168.130.152";
    // RSI port
    public static final int PORT_INTELYGENZ = 1556;

    // Frame characters
    public static final char ESC_FRAME_CHAR = (char) 0x1B;
    public static final char END_FRAME_CHAR = (char) 0x03;
    public static final char SPACE_FRAME_CHAR = (char) 0x20;

    // Frame fields
    public static final String DATA_FRAME_FIELD = "DATA";
    public static final String EVENT_FRAME_FIELD = "EVENT";

    // Frame char[]
    public static final char[] END_FRAME_ARR = new char[]{END_FRAME_CHAR};
    public static final char[] SPACE_FRAME_ARR = new char[]{SPACE_FRAME_CHAR};
    public static final String PANEL_SERIAL_NUMBER = "7899988444411108";
    public static final String CUSTOMER_ACOUNT = "500555";

    public static void main(String[] args) throws IOException {

        String hostName = HOST_INTELYGENZ;
        int portNumber = PORT_INTELYGENZ;

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String clientInput;
            StringBuffer userFrame = new StringBuffer();

            System.out.println("Introduce una instrucción para enviar al server y pulsta intro");

            while ((clientInput = stdIn.readLine()) != null) {

                userFrame = new StringBuffer();

                switch (clientInput) {
                    case "1":  userFrame.append(EVENT_FRAME_FIELD).append(SPACE_FRAME_ARR)
                            .append(PANEL_SERIAL_NUMBER).append(SPACE_FRAME_ARR)
                            .append(CUSTOMER_ACOUNT).append(SPACE_FRAME_ARR)
                            .append("20090511142935574").append(SPACE_FRAME_ARR)
                            .append("VIDEO,1,1724080").append(SPACE_FRAME_ARR)
                            .append(END_FRAME_ARR);
                        break;
                    case "2":  userFrame.append(EVENT_FRAME_FIELD).append(SPACE_FRAME_ARR)
                            .append(PANEL_SERIAL_NUMBER).append(SPACE_FRAME_ARR)
                            .append(CUSTOMER_ACOUNT).append(SPACE_FRAME_ARR)
                            .append("").append(SPACE_FRAME_ARR)
                            .append("VIDEO,1,1724080").append(SPACE_FRAME_ARR)
                            .append(END_FRAME_ARR);
                        break;
                    default: userFrame = new StringBuffer();
                        break;
                }

                if ( "".equals(userFrame.toString()) ) {

                    System.out.println("No has introducido una entrada válida!!");

                }else{

                    System.out.println("El client dice: " + userFrame.toString());
                    out.println(userFrame.toString());
                    System.out.println("El server dice: " + in.readLine());

                }

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
