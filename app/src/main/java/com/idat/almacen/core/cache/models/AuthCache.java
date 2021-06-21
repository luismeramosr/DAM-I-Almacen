package com.idat.almacen.core.cache.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.Response;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Console;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "AuthCache")
public class AuthCache {
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

    public static AuthCache ofLoginResponse(Response<LoginResponse> response) {
        return new AuthCache(
                response.getData().getUser().getId(),
                response.getData().getUser().getFirstName(),
                response.getData().getUser().getLastName(),
                response.getData().getUser().getUsername(),
                response.getData().getUser().getEmail(),
                response.getData().getUser().getRole().getName(),
                response.getData().getJwt(),
                response.getTimestamp()
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
