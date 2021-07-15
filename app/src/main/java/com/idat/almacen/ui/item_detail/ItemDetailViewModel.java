package com.idat.almacen.ui.item_detail;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.services.ItemService;
import com.idat.almacen.core.api.ws.WSMessage;
import com.idat.almacen.core.api.ws.WebSocketClient;
import com.idat.almacen.core.util.SharedData;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Optional;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemDetailViewModel extends ViewModel {

    private ItemService service;
    private WebSocketClient ws;
    private MediatorLiveData<ResWrapper<Item>> item;
    private MutableLiveData<Item> rtItem;
    private Item selectedItem;

    public void init() {
        service = ItemService.getInstance();
        ws = WebSocketClient.getInstance();
        item = new MediatorLiveData<>();
        rtItem = new MutableLiveData<>();
        selectedItem = SharedData.getInstance().getItem();
        item.setValue(new ResWrapper<>(
                selectedItem,
                System.currentTimeMillis(),
                null
        ));
        subscribeToRTItem();
    }

    public LiveData<ResWrapper<Item>> observeItem() {
        return item;
    }

    public MutableLiveData<Item> observeRTItem() { return rtItem; }

    @SuppressLint("NewApi")
    public LiveData<Item> subscribeToRTItem() {
        ws.sendMessage(new WSMessage("Item",
            ws.gson.fromJson(ws.gson.toJson(selectedItem), JsonObject.class)));

        ws.handler.getMessageHandler().onItemUpdated()
            .subscribe(newData -> {
                rtItem.setValue(newData);
            }, err -> {
                err.printStackTrace();
            });

        return observeRTItem();
    }
}
