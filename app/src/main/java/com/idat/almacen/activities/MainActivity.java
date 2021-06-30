package com.idat.almacen.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.almacen.R;
import com.idat.almacen.core.constants.Menus;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.services.UserCacheService;
import com.idat.almacen.core.constants.TopLevelDestinations;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.ActivityMainBinding;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private UserCacheService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        service = UserCacheService.getInstance();
        DrawerLayout drawer = binding.drawerLayout;

        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        setUpMenu();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                TopLevelDestinations.getInstance().getDestinations())
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NewApi")
    private void setUpMenu() {
        service.getCachedData()
                .subscribe(data -> {
                    int idMenu = Menus.getInstance().get(data.getRole());
                    binding.navView.inflateMenu(idMenu);
                    loadUserData(data);
                }, err -> {
                    err.printStackTrace();
                });

    }

    private void loadUserData(UserCache cachedData) {
        View headerView = binding.navView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.userName);
        TextView email = headerView.findViewById(R.id.userEmail);
        TextView role = headerView.findViewById(R.id.userRole);
        String user = String.format("%s %s",
                        cachedData.getFirstName(),
                        cachedData.getLastName());
        username.setText(user);
        email.setText(cachedData.getEmail());
        role.setText(cachedData.getRole());
        Helpers.showToast(this,
                String.format("Bienvenido %s", user), Toast.LENGTH_SHORT);
    }

}