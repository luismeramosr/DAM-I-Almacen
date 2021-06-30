package com.idat.almacen.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.almacen.databinding.ActivitySplashBinding;
import com.idat.almacen.core.util.Helpers;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.animationSplash.postOnAnimationDelayed(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2100);
    }
}
