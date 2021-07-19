package com.idat.almacen.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.UserService;
import com.idat.almacen.core.cache.services.UserCacheService;

public class ProfileViewModel extends ViewModel {

    private UserService userService;
    private UserCacheService cacheService;

    public void init() {
        userService = UserService.getInstance();
        cacheService = UserCacheService.getInstance();
    }

    public void updatePassword(String password) {
        cacheService.getCachedData()
            .subscribe(userCache -> {
                User user = new User();
                user.setId(userCache.id);
                user.setFirstName(userCache.firstName);
                user.setLastName(userCache.lastName);
                user.setDni(userCache.dni);
                user.setPhone(userCache.phone);
                user.setEmail(userCache.email);
                user.setUsername(userCache.username);
                user.setPassword(password);
                user.setActive(userCache.isActive());
                user.setIdRole(userCache.idRole);
                user.setIdSchedule(userCache.idSchedule);
                userService.updateUser(user).subscribe();
            }, err -> {
                err.printStackTrace();
            });
    }
}
