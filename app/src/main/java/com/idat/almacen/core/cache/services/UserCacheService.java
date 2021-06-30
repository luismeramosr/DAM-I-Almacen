package com.idat.almacen.core.cache.services;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.cache.CacheDatabase;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.util.Helpers;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserCacheService {

    private static UserCacheService instance;
    private CacheDatabase db;

    private UserCacheService() {
        db = CacheDatabase.getInstance();
    }

    public static UserCacheService getInstance() {
        if (instance == null) instance = new UserCacheService();
        return instance;
    }

    public void cacheData(UserCache data) {
        List<UserCache> entries = (List<UserCache>) db.authCacheRepository().getEntries();
        if (entries.size() == 0) db.authCacheRepository().insert(data);
        else {
            db.authCacheRepository().deleteCache();
            db.authCacheRepository().insert(data);
        }
    }

    public Flowable<UserCache> getCachedData() {
        return db.authCacheRepository().getAuthCache().subscribeOn(Schedulers.io());
    }

    public void deleteCache() {
        Helpers.getInstance().runOnBackgroundThread(() -> {
            db.authCacheRepository().deleteCache();
        });
    }

}
