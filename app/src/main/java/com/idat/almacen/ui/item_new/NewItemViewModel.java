package com.idat.almacen.ui.item_new;

import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.services.ItemService;

public class NewItemViewModel extends ViewModel {

    private ItemService service;

    public void init() {
        service = ItemService.getInstance();
    }

    public void saveItem(Item item) {
        service.addItem(item).subscribe();
    }

}
