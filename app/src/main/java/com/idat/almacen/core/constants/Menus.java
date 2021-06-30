package com.idat.almacen.core.constants;

import com.idat.almacen.R;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Menus {

    private Map<String, Integer> menuByRole;
    private static Menus instance;

    private Menus() {
        menuByRole = new HashMap<String, Integer>();
        menuByRole.put("Administrador", R.menu.menu_admin);
        menuByRole.put("Operario", R.menu.menu_operator);
    }

    public static Menus getInstance() {
        if (instance == null)
            instance = new Menus();
        return instance;
    }

    public Integer get(String role) {
        return menuByRole.get(role);
    }
}
