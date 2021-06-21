package com.idat.almacen.core.cache.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.idat.almacen.core.cache.models.AuthCache;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface AuthCacheDao {

    @Query("select * from authcache limit 1")
    LiveData<AuthCache> getAuthCache();

    @Insert
    void insert(AuthCache authCache);

    @Update
    void updateCache(AuthCache authCache);

    @Query("delete from authcache")
    void deleteCache();

    @Query("select * from authcache")
    List<AuthCache> getEntries();

}
