package com.idat.almacen.ui.home_operator;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.services.UserCacheService;

import lombok.Getter;

public class HomeOperatorViewModel extends ViewModel {

    @Getter
    private LiveData<UserCache> userData;
    private UserCacheService service;

    public void init(Context context) {
        service = UserCacheService.getInstance();
    }

}
