package com.idat.almacen.core.api.models;

import android.annotation.SuppressLint;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private int id;
    private String start;
    private String stop;

    @SuppressLint("NewApi")
    public LocalTime getStart() {
        return LocalTime.parse(start);
    }

    @SuppressLint("NewApi")
    public LocalTime getStop() {
        return LocalTime.parse(stop);
    }
}
