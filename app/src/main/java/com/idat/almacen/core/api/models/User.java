package com.idat.almacen.core.api.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String dni;
    private String phone;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private int idRole;
    private int idSchedule;
    private Role role;
    private Schedule schedule;
}
