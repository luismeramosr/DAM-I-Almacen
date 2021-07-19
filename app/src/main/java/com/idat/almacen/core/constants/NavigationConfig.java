package com.idat.almacen.core.constants;

import com.idat.almacen.R;

import java.util.HashMap;
import java.util.Map;

public class NavigationConfig {

    private Map<String, Integer> menuByRole;
    private static NavigationConfig instance;

    private NavigationConfig() {
        menuByRole = new HashMap<>();
        menuByRole.put("Administrador", R.menu.menu_admin);
        menuByRole.put("Operario", R.menu.menu_operator);
    }

    public static NavigationConfig getInstance() {
        if (instance == null)
            instance = new NavigationConfig();
        return instance;
    }

    public Integer getMenu(String role) {
        return menuByRole.get(role);
    }

}
