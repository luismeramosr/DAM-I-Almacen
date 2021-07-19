package com.idat.almacen.core.api.ws;

import com.google.gson.JsonObject;

public interface NewDataPublishedListener {
    void onNewData(JsonObject newData);
}
