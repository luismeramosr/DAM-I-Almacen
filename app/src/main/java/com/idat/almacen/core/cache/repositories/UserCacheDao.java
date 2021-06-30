package com.idat.almacen.core.cache.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.idat.almacen.core.cache.models.UserCache;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface UserCacheDao {

    @Query("select * from usercache limit 1")
    Flowable<UserCache> getAuthCache();

    @Insert
    void insert(UserCache authCache);

    @Update
    void updateCache(UserCache authCache);

    @Query("delete from usercache")
    void deleteCache();

    @Query("select * from usercache")
    List<UserCache> getEntries();

}
