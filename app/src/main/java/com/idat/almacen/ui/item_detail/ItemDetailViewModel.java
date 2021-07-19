package com.idat.almacen.ui.item_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.services.ItemService;
import com.idat.almacen.core.api.ws.NewDataPublishedListener;
import com.idat.almacen.core.api.ws.WSMessage;
import com.idat.almacen.core.api.ws.WebSocketClient;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.SharedData;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    }

    public LiveData<ResWrapper<Item>> observeItem() {
        return item;
    }

    public void subscribeToRTItem(NewDataPublishedListener listener) {
        ws.sendMessage(new WSMessage("Item",
                ws.gson.fromJson(ws.gson.toJson(selectedItem), JsonObject.class)));

        ws.handler.getMessageHandler().subscribe(listener);
    }

    public void unsubscribeToRTItem() {
        ws.sendMessage(new WSMessage("unsubscribe-Item",
                ws.gson.fromJson(ws.gson.toJson(selectedItem), JsonObject.class)));
        ws.handler.getMessageHandler().unsubscribe();
    }

    public LiveData<ResWrapper<Item>> updateItem(Item _item) {
        final LiveData<ResWrapper<Item>> source = LiveDataReactiveStreams.fromPublisher(
            service.updateItem(_item)
        );

        item.addSource(source, response -> {
            item.setValue(response);
            item.removeSource(source);
        });

        return observeItem();
    }

    public void deleteItem(String barcode) {
        service.deleteItem(barcode).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
