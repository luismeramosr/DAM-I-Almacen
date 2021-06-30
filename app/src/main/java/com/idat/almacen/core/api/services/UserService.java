package com.idat.almacen.core.api.services;


import com.idat.almacen.core.api.config.RetrofitInstance;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.repositories.IUserRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserService {

    private IUserRepository repository;
    private static UserService instance;

    private UserService() {
        repository = RetrofitInstance.getInstance().createRepository(IUserRepository.class);
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public Flowable<ResWrapper<List<User>>> getAllUsers() {
        return repository.getAllUsers().subscribeOn(Schedulers.io());
    }
}
