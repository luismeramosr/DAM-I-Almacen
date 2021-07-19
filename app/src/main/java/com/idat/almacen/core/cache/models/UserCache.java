package com.idat.almacen.core.cache.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.ResWrapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "UserCache")
public class UserCache {
    @NonNull
    @PrimaryKey
    public int id = 1;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "dni")
    public String dni;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "active")
    private boolean active;

    @ColumnInfo(name = "idRole")
    public int idRole;

    @ColumnInfo(name = "idSchedule")
    public int idSchedule;

    @ColumnInfo(name = "role")
    public String role;

    @ColumnInfo(name = "jwt")
    public String jwt;

    @ColumnInfo(name = "timestamp")
    public Long timestamp;

    public static UserCache ofLoginResponse(ResWrapper<LoginResponse> responseWrapper) {
        return new UserCache(
                responseWrapper.getData().getUser().getId(),
                responseWrapper.getData().getUser().getFirstName(),
                responseWrapper.getData().getUser().getLastName(),
                responseWrapper.getData().getUser().getDni(),
                responseWrapper.getData().getUser().getPhone(),
                responseWrapper.getData().getUser().getEmail(),
                responseWrapper.getData().getUser().getUsername(),
                responseWrapper.getData().getUser().getPassword(),
                responseWrapper.getData().getUser().isActive(),
                responseWrapper.getData().getUser().getIdRole(),
                responseWrapper.getData().getUser().getIdSchedule(),
                responseWrapper.getData().getUser().getRole().getName(),
                responseWrapper.getData().getJwt(),
                responseWrapper.getTimestamp()
        );
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
