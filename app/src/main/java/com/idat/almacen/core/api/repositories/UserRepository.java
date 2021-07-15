package com.idat.almacen.core.api.repositories;

import com.idat.almacen.core.api.constants.Endpoints;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserRepository {

    @GET(Endpoints.operators)
    Flowable<ResWrapper<List<User>>> getAllUsers();

    @POST(Endpoints.saveUser)
    Flowable<ResWrapper<User>> saveUser(@Body User user);

    @PUT(Endpoints.updateUser)
    Flowable<ResWrapper<User>> updateUser(@Body User user);

    @DELETE(Endpoints.deleteUser)
    Call<Void> deleteUserById(@Path("id") int id);

}
