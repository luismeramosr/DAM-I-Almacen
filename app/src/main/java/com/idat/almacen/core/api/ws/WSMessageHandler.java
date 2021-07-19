package com.idat.almacen.core.api.ws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.util.Console;

import org.reactivestreams.Subscriber;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import kotlinx.coroutines.flow.Flow;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WSMessageHandler {

    private Gson gson;
    private Map<String, Runnable> processors;
    private WebSocket ws;
    private WSMessage msg;
    private NewDataPublishedListener listener;

    public WSMessageHandler() {
        gson = new Gson();
        processors = new HashMap<>();
        msg = new WSMessage();
        processors.put("Ping", this::onPing);
        processors.put("Item", this::onItemUpdated);
    }

    public void handleMessage(String message, WebSocket webSocket) {
        ws = webSocket;
        JsonObject jo = gson.fromJson(message, JsonObject.class);
        msg.setId(jo.get("id").getAsString());
        msg.setBody(jo.get("body").getAsJsonObject());
        processors.get(jo.get("id").getAsString()).run();
    }

    public void onItemUpdated() {
        Item item = gson.fromJson(msg.getBody(), Item.class);
        if (item != null) {
            listener.onNewData(msg.getBody());
        }
    }

    private void onPing() {
        String payload = gson.toJson(new WSMessage("Pong", new JsonObject()), WSMessage.class);
        ws.send(payload);
    }

    public void subscribe(NewDataPublishedListener _listener) {
        listener = _listener;
    }

    public void unsubscribe() {
        listener = new NewDataPublishedListener() {
            @Override
            public void onNewData(JsonObject newData) {
                Console.log("Not listening...");
            }
        };
    }
}
