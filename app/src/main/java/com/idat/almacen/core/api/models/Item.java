package com.idat.almacen.core.api.models;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String barcode;
    private String brand;
    private String name;
    private String description;
    private Float price;
    private int stock;
    private int stock_min;
    private int providerId;
    private Provider provider;
    private List<Request> requests;

}
