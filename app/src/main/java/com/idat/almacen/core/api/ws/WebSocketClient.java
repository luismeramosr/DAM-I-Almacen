package com.idat.almacen.core.api.ws;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.idat.almacen.BuildConfig;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.util.Console;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {

    private static WebSocketClient instance;
    public WSHandler handler;
    private WebSocket ws;
    public Gson gson;

    private WebSocketClient() {
        gson = new Gson();
    }

    public static WebSocketClient getInstance() {
        if (instance == null)
            instance = new WebSocketClient();
        return instance;
    }

    public void initConnection() {
        OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .pingInterval(4, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
        Request request = new Request.Builder()
                .url(BuildConfig.WSSERVER_URL).build();
        handler = new WSHandler(ws);
        ws = client.newWebSocket(request, handler);
    }

    public void disconnect() {
        ws.close(1000, "Logging out...");
    }

    public <T> void sendMessage (T message) {
        String payload = gson.toJson(message, message.getClass());
        ws.send(payload);
    }

    public class WSHandler extends WebSocketListener {

        @Getter
        private WSMessageHandler messageHandler;

        public WSHandler(WebSocket webSocket) {
            messageHandler = new WSMessageHandler();
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Console.log("WebSocket connected!");
        }

        @Override
        public void onMessage(WebSocket webSocket, String message) {
            messageHandler.handleMessage(message, webSocket);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Console.log(reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Console.log(reason);
        }
    }

}
