package com.idat.almacen.core.api.repositories;

import com.idat.almacen.core.api.constants.Endpoints;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface IUserRepository {
    @GET(Endpoints.operators)
    Flowable<ResWrapper<List<User>>> getAllUsers();

    @PUT(Endpoints.updateUser)
    Flowable<ResWrapper<User>> updateUser(@Body User user);
}
