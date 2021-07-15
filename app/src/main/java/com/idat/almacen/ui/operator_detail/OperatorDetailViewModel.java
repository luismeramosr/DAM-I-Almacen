package com.idat.almacen.ui.operator_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.dto.responses.ResWrapper;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.UserService;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.core.util.SharedData;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperatorDetailViewModel extends ViewModel {

    private UserService service;
    private MediatorLiveData<ResWrapper<User>> userData;

    public void init() {
        service = UserService.getInstance();
        userData = new MediatorLiveData<>();
        userData.setValue(new ResWrapper<>(
            SharedData.getInstance().getUser(),
            System.currentTimeMillis(),
            null
        ));
    }

    public LiveData<ResWrapper<User>> observeUser() { return userData; }

    public LiveData<ResWrapper<User>> updateUser(User user) {
        final LiveData<ResWrapper<User>> source = LiveDataReactiveStreams.fromPublisher(
            service.updateUser(user)
        );

        userData.addSource(source, response -> {
            userData.setValue(response);
            userData.removeSource(source);
        });
        return observeUser();
    }

    public void deleteUser(int id) {
        service.deleteUserById(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
