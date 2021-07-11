package com.idat.almacen.ui.operator_new;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.UserService;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.SharedData;

public class NewOperatorViewModel extends ViewModel {

    private UserService service;
    private MediatorLiveData<ResWrapper<User>> userData = new MediatorLiveData<>();

    public void init() {
        service = UserService.getInstance();
        userData.setValue(new ResWrapper<>(
                SharedData.getInstance().getUser(),
                System.currentTimeMillis(),
                null));
    }

    public LiveData<ResWrapper<User>> observeUser() { return userData; }

    public LiveData<ResWrapper<User>> saveUser(User user) {
        final LiveData<ResWrapper<User>> source = LiveDataReactiveStreams.fromPublisher(
                service.saveUser(user)
        );

        userData.addSource(source, response -> {
            userData.setValue(response);
            userData.removeSource(source);
        });
        return observeUser();
    }

}
