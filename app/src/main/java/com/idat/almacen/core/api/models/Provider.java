package com.idat.almacen.core.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    private int id;
    private String name;
    private String phone;

}
