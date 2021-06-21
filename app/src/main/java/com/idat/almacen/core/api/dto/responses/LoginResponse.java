package com.idat.almacen.core.api.dto.responses;

import com.idat.almacen.core.api.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private User user;
    private String jwt;
}
