package com.idat.almacen.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.cache.services.AuthCacheService;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel{

    private MutableLiveData<String> mText;
    private AuthCacheService service;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        service = AuthCacheService.getInstance(getApplication().getApplicationContext());
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void updateData(LifecycleOwner owner) {
        service.getAuthCache().observe(owner, (data) -> {
            mText.setValue(data.toString());
        });
    }
}