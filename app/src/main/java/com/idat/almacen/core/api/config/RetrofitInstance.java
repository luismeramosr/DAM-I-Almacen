package com.idat.almacen.core.api.config;

import com.idat.almacen.BuildConfig;

import java.io.IOException;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitInstance {

    private static RetrofitInstance instance;
    private final Retrofit api;
    private RequestInterceptor interceptor;
    private OkHttpClient client;

    private RetrofitInstance() {
        interceptor = RequestInterceptor.getInstance();
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api =  new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitInstance getInstance() {
        if(instance==null) {
            instance = new RetrofitInstance();
        }
        return instance;
    }

    public <S> S createRepository(Class<S> IRepository) {
        return api.create(IRepository);
    }

    public <T> Response<T> execute(Call<T> call) {
        try {
            return call.execute();
        } catch (IOException err) {
            err.printStackTrace();
            return null;
        }
    }

}