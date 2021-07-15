package com.idat.almacen.core.api.services;


import com.idat.almacen.core.api.config.RetrofitInstance;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.repositories.ItemRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class ItemService implements ItemRepository {

    private static ItemService instance;
    private ItemRepository repository;

    private ItemService() {
        repository = RetrofitInstance.getInstance().createRepository(ItemRepository.class);
    }

    public static ItemService getInstance() {
        if (instance == null)
            instance = new ItemService();
        return instance;
    }

    @Override
    public Flowable<ResWrapper<List<Item>>> findAllItems() {
        return repository.findAllItems().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<ResWrapper<Item>> addItem(Item item) {
        return repository.addItem(item).subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<ResWrapper<Item>> updateItem(Item item) {
        return repository.updateItem(item).subscribeOn(Schedulers.io());
    }

    @Override
    public Call<Void> deleteItem(String barcode) {
        return repository.deleteItem(barcode);
    }
}
