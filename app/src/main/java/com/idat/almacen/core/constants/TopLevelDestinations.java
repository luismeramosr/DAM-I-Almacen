package com.idat.almacen.core.constants;

import com.idat.almacen.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;

public class TopLevelDestinations {
    private static TopLevelDestinations instance;
    private List<Integer> destinations;

    private TopLevelDestinations() {
        destinations = new ArrayList<>();
        destinations.add(R.id.nav_home_admin);
        destinations.add(R.id.nav_home_operator);
        destinations.add(R.id.nav_operators);
        destinations.add(R.id.nav_profile);
        destinations.add(R.id.nav_logout);
    }

    public static TopLevelDestinations getInstance() {
        if (instance == null)
            instance = new TopLevelDestinations();
        return instance;
    }

    public int[] getDestinations() {
        int[] dests = new int[destinations.size()];
        for (int i=0;i<destinations.size();i++) {
            dests[i] = destinations.get(i);
        }
        return dests;
    }
}
