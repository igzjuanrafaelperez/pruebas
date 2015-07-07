package com.rsi;

import java.text.SimpleDateFormat;

public class RSIProtocol {
    // Frame characters
    public static final char ESC_FRAME_CHAR = (char) 0x1B;
    public static final char END_FRAME_CHAR = (char) 0x3E;
    public static final char SPACE_FRAME_CHAR = (char) 0x20;

    // Frame fields
    public static final String DATA_FRAME_FIELD = "DATA";
    public static final String EVENT_FRAME_FIELD = "EVENT";

    public BeanFrame processInput(String theInput) throws FormatFrameException {

        BeanFrame bFrame = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        // TODO I don´t know if we have the ESC_FRAME_CHAR too.
        // Replace the END_FRAME_CHAR
        theInput.replaceAll(Character.toString(ESC_FRAME_CHAR) + Character.toString(END_FRAME_CHAR), "");

        // TODO I don´t know if we have the ESC_FRAME_CHAR too.
        // Split the frame by the SPACE_FRAME_CHAR
        String[] frame = theInput.split(Character.toString(ESC_FRAME_CHAR)+Character.toString(SPACE_FRAME_CHAR));

        if ( frame.length == 5 ){ throw new FormatFrameException("Frame invalid format.");}

        try {

            bFrame = new BeanFrame(frame[0], frame[1], frame[2], dateFormat.parse(frame[3]) , frame[4].split(",") );

        }catch (java.text.ParseException e ){

            throw new FormatFrameException("Date field invalid format.");

        }

        return bFrame;
    }
}
