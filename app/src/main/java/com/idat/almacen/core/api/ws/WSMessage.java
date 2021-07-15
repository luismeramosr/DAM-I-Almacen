package com.idat.almacen.core.api.ws;

import com.google.gson.JsonObject;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WSMessage {

    private String id;
    private JsonObject body;

    public WSMessage(String _id) {
        id = _id;
    }

    public WSMessage(String _id, JsonObject _body) {
        id = _id;
        body = _body;
    }

}
