package com.idat.almacen.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.almacen.R;
import com.idat.almacen.core.api.constants.Roles;
import com.idat.almacen.core.api.services.AuthService;
import com.idat.almacen.core.cache.models.AuthCache;
import com.idat.almacen.core.cache.services.AuthCacheService;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.databinding.ActivityMainBinding;
import com.idat.almacen.databinding.NavHeaderMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private AuthCacheService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        service = AuthCacheService.getInstance(this);
        setContentView(binding.getRoot());
        setUpMenu();
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NewApi")
    private void setUpMenu() {
        service.getAuthCache().observe(this, (data) -> {
            binding.navView.inflateMenu(new Roles().getRoles().get(data.getRole()));
            loadUserData(data);
        });
    }

    private void loadUserData(AuthCache cachedData) {
        View headerView = binding.navView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.userName);
        TextView email = headerView.findViewById(R.id.userEmail);
        username.setText(cachedData.getFirstName()+ " "+cachedData.getLastName());
        email.setText(cachedData.getEmail());
    }
}