package com.idat.almacen.core.util;

import android.annotation.SuppressLint;
import android.os.Build;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Helpers {

    private static Helpers instance;

    private Helpers() { }

    public static Helpers getInstance() {
        if (instance == null) {
            instance = new Helpers();
        }
        return instance;
    }

    public void setTimeout(Runnable function, int delay) {
        new Thread(() -> {
           try {
               Thread.sleep(delay);
               function.run();
           } catch (Exception err) {
               System.err.println(err);
           }
        }).start();
    }

    @SuppressLint("NewApi")
    public LocalDateTime timestampToDateTime(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(-5));
    }

}
