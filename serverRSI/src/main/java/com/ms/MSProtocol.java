package com.ms;

public class MSProtocol {
    private static final int WAITING = 0;
    private static final int WORKING = 1;

    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Estas conectado al multiserver";
            state = WORKING;
        } else if (state == WORKING) {
            if (theInput.equalsIgnoreCase("ADIOS")) {
                theOutput = "Hasta luego";
                state = WAITING;
            } else {
                theOutput = "Dejame que estoy trabajando!!";
            }
        }
        return theOutput;
    }
}
