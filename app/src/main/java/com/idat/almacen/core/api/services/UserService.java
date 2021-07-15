package com.idat.almacen.core.api.services;


import com.idat.almacen.core.api.config.RetrofitInstance;
import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.repositories.UserRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserService implements UserRepository {

    private UserRepository repository;
    private static UserService instance;

    private UserService() {
        repository = RetrofitInstance.getInstance().createRepository(UserRepository.class);
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public Flowable<ResWrapper<List<User>>> getAllUsers() {
        return repository.getAllUsers().subscribeOn(Schedulers.io());
    }

    public Flowable<ResWrapper<User>> saveUser(User user) {
        return repository.saveUser(user).subscribeOn(Schedulers.io());
    }

    public Flowable<ResWrapper<User>> updateUser(User user) {
        return repository.updateUser(user).subscribeOn(Schedulers.io());
    }

    public Call<Void> deleteUserById(int id) {
        return repository.deleteUserById(id);
    }

}
