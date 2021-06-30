package com.idat.almacen.ui.operators;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.UserService;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;

public class OperatorsViewModel extends ViewModel {

    private UserService service;
    @Getter
    private MediatorLiveData<ResWrapper<List<User>>> operators = new MediatorLiveData<>();

    public void init() {
        service = UserService.getInstance();
        final LiveData<ResWrapper<List<User>>> source = LiveDataReactiveStreams.fromPublisher(
            service.getAllUsers()
        );

        operators.addSource(source, listResponse -> operators.setValue(listResponse));
    }

    public LiveData<ResWrapper<List<User>>> observeOperators() {
        return operators;
    }
}
