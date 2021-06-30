package com.idat.almacen.ui.home_admin;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.services.UserCacheService;

import lombok.Getter;

public class HomeAdminViewModel extends ViewModel {

    private MediatorLiveData<UserCache> userData = new MediatorLiveData<>();
    private UserCacheService service;

    public void init(Context context) {
        service = UserCacheService.getInstance();
        final LiveData<UserCache> source = LiveDataReactiveStreams.fromPublisher(
            service.getCachedData()
        );
        userData.addSource(source, userCache -> {
            userData.setValue(userCache);
            userData.removeSource(source);
        });
    }

    public LiveData<UserCache> getUserCache() {
        return userData;
    }

}