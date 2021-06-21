package com.idat.almacen.core.api.services;

import com.idat.almacen.core.api.RetrofitInstance;
import com.idat.almacen.core.api.dto.requests.LoginRequest;
import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.Response;
import com.idat.almacen.core.api.repositories.IAuthRepository;


import io.reactivex.rxjava3.core.Observable;

public class AuthService {

    private IAuthRepository repository;

    public AuthService() {
        repository = RetrofitInstance.getInstance().createRepository(IAuthRepository.class);
    }

    public Observable<Response<LoginResponse>> login(LoginRequest request) {
        return repository.login(request).toObservable();
    }

}
