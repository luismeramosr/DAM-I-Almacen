package com.idat.almacen.core.constants;

import com.idat.almacen.R;

import java.util.HashMap;
import java.util.Map;

public class NavigationConfig {

    private Map<String, Integer> menuByRole;
    private Map<String, Integer> navigationGraphs;
    private static NavigationConfig instance;

    private NavigationConfig() {
        menuByRole = new HashMap<>();
        navigationGraphs = new HashMap<>();
        menuByRole.put("Administrador", R.menu.menu_admin);
        menuByRole.put("Operario", R.menu.menu_operator);
        navigationGraphs.put("Administrador", R.navigation.admin_navigation);
        navigationGraphs.put("Operario", R.navigation.operator_navigation);
    }

    public static NavigationConfig getInstance() {
        if (instance == null)
            instance = new NavigationConfig();
        return instance;
    }

    public Integer getMenu(String role) {
        return menuByRole.get(role);
    }

    public Integer getNavGraph(String role) { return navigationGraphs.get(role); }
}
