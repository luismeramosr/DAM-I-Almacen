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
    private String id = "";

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "jwt")
    private String jwt;

    @ColumnInfo(name = "timestamp")
    private Long timestamp;

    public static UserCache ofLoginResponse(ResWrapper<LoginResponse> responseWrapper) {
        return new UserCache(
                responseWrapper.getData().getUser().getId(),
                responseWrapper.getData().getUser().getFirstName(),
                responseWrapper.getData().getUser().getLastName(),
                responseWrapper.getData().getUser().getUsername(),
                responseWrapper.getData().getUser().getEmail(),
                responseWrapper.getData().getUser().getRole().getName(),
                responseWrapper.getData().getJwt(),
                responseWrapper.getTimestamp()
        );
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
