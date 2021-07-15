package com.idat.almacen.core.api.models;

import java.lang.reflect.Field;
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
    private Set<Request> requests;

    public boolean isEmpty() {
        boolean result = false;
        try {
            for (Field f : getClass().getDeclaredFields())
                if (f.get(this) != null) result = true;
        } catch (IllegalAccessException err) {
            err.printStackTrace();
        }
        return result;
    }

}
