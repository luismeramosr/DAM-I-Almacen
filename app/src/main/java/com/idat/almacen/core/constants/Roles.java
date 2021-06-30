package com.idat.almacen.core.constants;

public class Roles {

    private static Roles instance;

    public static Roles getInstance() {
        if (instance == null)
            instance = new Roles();
        return instance;
    }
}
