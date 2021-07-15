package com.idat.almacen.core.api.repositories;

import com.idat.almacen.core.api.constants.Endpoints;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.Item;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemRepository {

    @GET(Endpoints.findAllItems)
    Flowable<ResWrapper<List<Item>>> findAllItems();

    @POST(Endpoints.addItem)
    Flowable<ResWrapper<Item>> addItem(@Body Item item);

    @PUT(Endpoints.updateItem)
    Flowable<ResWrapper<Item>> updateItem(@Body Item item);

    @DELETE(Endpoints.deleteItem)
    Call<Void> deleteItem(@Path("barcode") String barcode);

}
