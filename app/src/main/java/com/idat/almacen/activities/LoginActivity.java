package com.idat.almacen.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.almacen.core.api.dto.requests.LoginRequest;
import com.idat.almacen.core.api.dto.responses.LoginResponse;
import com.idat.almacen.core.api.dto.responses.Response;
import com.idat.almacen.core.api.services.AuthService;
import com.idat.almacen.core.cache.models.AuthCache;
import com.idat.almacen.core.cache.services.AuthCacheService;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.ActivityLoginBinding;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthService authService;
    private AuthCacheService cacheService;

    private TextView username;
    private TextView password;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authService = new AuthService();
        cacheService = AuthCacheService.getInstance(this);
        username = binding.username;
        password = binding.password;

        binding.btnLogin.setOnClickListener((View v) -> {
            if (validateRequestData(username.getText().toString(), password.getText().toString())) {

                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                authService.login(request)
                        .subscribeOn(Schedulers.io())
                        .subscribe((response) -> {
                            showToast("Bienvenido");
                            // TODO: Solve error where the auth response is not being cached
                            Console.log(response.toString());
                            cacheService.cacheAuthData(AuthCache.ofLoginResponse(response));
                            Helpers.getInstance().setTimeout(this::goToMainActivity, 200);
                        }, (err) -> {
                            if (err.getCause() != null) {
                                showToast("El servidor no responde o no tiene conexión a internet!");
                            } else
                                showToast("Credenciales incorrectas!");
                            err.printStackTrace();
                        });
            }
        });
    }

    private boolean validateRequestData(String username, String password) {
        if (username.length() > 20 || username.equals("")) {
            showToast("Debe ingresar un usuario válido!");
            return false;
        }

        if (password.length() > 64 || password.equals("")) {
            showToast("Debe ingresar una contraseña válida!");
            return false;
        }

        return true;
    }

    // TODO: Refactor this method to a Helpers method
    private void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }

    private void goToMainActivity() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
