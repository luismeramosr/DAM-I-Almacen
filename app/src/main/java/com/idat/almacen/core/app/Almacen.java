package com.idat.almacen.core.app;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

public class Almacen extends Application {

    private static Almacen instance;

    public static Almacen getInstance() {
        if (instance == null)
            instance = new Almacen();
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static LifecycleOwner getLifeCycleOwner() { return (LifecycleOwner) instance; }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
