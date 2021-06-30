package com.idat.almacen.core.util;

import com.idat.almacen.core.api.models.User;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class SharedData {

    private static SharedData instance;
    @Getter
    @Setter
    private User user;

    private SharedData() {

    }

    public static SharedData getInstance() {
        if (instance == null)
            instance = new SharedData();
        return instance;
    }

}
