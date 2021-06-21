package com.idat.almacen.core.api.constants;

import com.idat.almacen.R;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Roles {

    private Map<String, Integer> roles;

    public Roles() {
        roles = new HashMap<String, Integer>();
        roles.put("ADMIN", R.menu.menu_admin);
        roles.put("OPERARIO", R.menu.menu_operator);
    }
}
