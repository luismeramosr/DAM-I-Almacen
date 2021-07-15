package com.idat.almacen.ui.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.ItemService;
import com.idat.almacen.core.api.services.UserService;
import com.idat.almacen.core.util.Console;

import java.util.List;

import lombok.Getter;

public class InventoryViewModel extends ViewModel {

    private ItemService service;
    @Getter
    private MediatorLiveData<ResWrapper<List<Item>>> items = new MediatorLiveData<>();

    public void init() {
        service = ItemService.getInstance();
        final LiveData<ResWrapper<List<Item>>> source = LiveDataReactiveStreams.fromPublisher(
                service.findAllItems()
        );

        items.addSource(source, listResponse -> {
            items.setValue(listResponse);
            items.removeSource(source);
        });
    }

    public LiveData<ResWrapper<List<Item>>> observeItems() {
        return items;
    }

}
