package com.idat.almacen.core.api.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;

public interface ICrudRepository<T> {
    Call<MutableLiveData<ArrayList<T>>> findAll();
    Call<MutableLiveData<T>> findById(int id);
    Call<MutableLiveData<T>> findById(String id);
    Call<Boolean> save(T obj);
    Call<Boolean> update(T obj);
    void delete(T obj);
}
