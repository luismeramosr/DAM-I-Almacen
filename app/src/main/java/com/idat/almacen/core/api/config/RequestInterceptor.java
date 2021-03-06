package com.idat.almacen.core.api.config;

import androidx.lifecycle.LifecycleOwner;

import com.idat.almacen.core.app.Almacen;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.services.UserCacheService;
import com.idat.almacen.core.util.Console;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private static RequestInterceptor instance;
    private UserCacheService cacheService;
    private UserCache cache;

    private RequestInterceptor() {
        cacheService = UserCacheService.getInstance();
        cacheService.getCachedData()
                    .subscribe(
                        _cache -> cache = _cache,
                        err -> err.printStackTrace()
                    );
    }

    public static RequestInterceptor getInstance() {
        if (instance == null)
            instance = new RequestInterceptor();
        return instance;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (cache != null) {
            Request request = chain.request().newBuilder()
                    .header("Authorization", "Bearer "+ cache.jwt)
                    .build();
            return chain.proceed(request);
        } else {
            Request request = chain.request().newBuilder().build();
            return chain.proceed(request);
        }
    }

}
