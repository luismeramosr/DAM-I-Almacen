package com.idat.almacen.core.cache.services;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.idat.almacen.core.cache.CacheDatabase;
import com.idat.almacen.core.cache.models.AuthCache;
import com.idat.almacen.core.util.Console;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthCacheService {

    private static AuthCacheService instance;
    private CacheDatabase db;
    private Context context;

    private AuthCacheService(Context _context) {
        context = _context;
        db = CacheDatabase.getInstance(context);
    }

    public static AuthCacheService getInstance(Context _context) {
        if (instance == null) instance = new AuthCacheService(_context);
        return instance;
    }

    public void cacheAuthData(AuthCache data) {
        List<AuthCache> entries = (List<AuthCache>) db.authCacheRepository().getEntries();
        Console.log(entries);
        if (entries.size() == 0) db.authCacheRepository().insert(data);
        else {
            db.authCacheRepository().deleteCache();
            db.authCacheRepository().insert(data);
        }
    }

    public LiveData<AuthCache> getAuthCache() {
        return db.authCacheRepository().getAuthCache();
    }

    public void updateCache(AuthCache data) {
        db.authCacheRepository().updateCache(data);
    }

}
