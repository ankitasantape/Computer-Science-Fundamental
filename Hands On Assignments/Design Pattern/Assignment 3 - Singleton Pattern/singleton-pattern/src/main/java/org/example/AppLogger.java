package org.example;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum AppLogger {

    INSTANCE;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String message){
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] INFO: " + message);
    }
}
