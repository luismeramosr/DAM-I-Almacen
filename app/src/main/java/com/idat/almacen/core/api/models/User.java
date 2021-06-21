package com.idat.almacen.core.api.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private int dni;
    private int phone;
    private String email;
    private String username;
    private String password;
    private int idRole;
    private Role role;
}
