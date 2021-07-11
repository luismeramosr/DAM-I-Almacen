package com.idat.almacen.core.api.services;

import com.idat.almacen.core.api.config.RetrofitInstance;
import com.idat.almacen.core.api.dto.requests.LoginRequest;
import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.repositories.IAuthRepository;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthService {

    private IAuthRepository repository;
    private static AuthService instance;

    private AuthService() {
        repository = RetrofitInstance.getInstance().createRepository(IAuthRepository.class);
    }

    public static AuthService getInstance() {
        if (instance == null)
            instance = new AuthService();
        return instance;
    }

    public Flowable<ResWrapper<LoginResponse>> login(LoginRequest request) {
        return repository.login(request).subscribeOn(Schedulers.io());
    }

}
