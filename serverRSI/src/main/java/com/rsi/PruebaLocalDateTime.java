package com.rsi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by juanrafael.perez on 28/07/2015.
 */
public class PruebaLocalDateTime {

    private static final DateTimeFormatter DATE_TIME_FORMATTER            = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();

    public static void main (String[] args) {

        LocalDateTime.parse("20091020105903754", DATE_TIME_FORMATTER)

    }
}
