package com.idat.almacen.core.api.repositories;

import com.idat.almacen.core.api.constants.Endpoints;
import com.idat.almacen.core.api.dto.requests.LoginRequest;
import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.Response;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthRepository {

    @POST(Endpoints.login)
    Flowable<Response<LoginResponse>> login(@Body LoginRequest request);
}
