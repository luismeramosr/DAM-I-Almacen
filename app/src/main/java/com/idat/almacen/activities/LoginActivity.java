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
import com.idat.almacen.core.api.services.AuthService;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.services.UserCacheService;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.ActivityLoginBinding;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthService authService;
    private UserCacheService cacheService;

    private TextView username;
    private TextView password;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authService = AuthService.getInstance();
        cacheService = UserCacheService.getInstance();
        username = binding.username;
        password = binding.password;

        binding.btnLogin.setOnClickListener((View v) -> {
            if (validateRequestData(username.getText().toString(), password.getText().toString())) {
                cacheService.deleteCache();
                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                Console.log("Logging in...");
                authService.login(request)
                      .subscribe((response) -> {
                          Console.log(response.toString());
                          if (response.getErrorMessage() == null) {

                              cacheService.cacheData(UserCache.ofLoginResponse(response));
                              goToMainActivity();
                          } else {
                              Helpers.getInstance().showToast(this, response.getErrorMessage(), Toast.LENGTH_SHORT);
                          }
                      }, (err) -> {
                          if (err.getCause() != null)  {
                              Helpers.getInstance().showToast(this,"El servidor no responde o no tiene conexión a internet!",
                                      Toast.LENGTH_SHORT);
                          }
                          err.printStackTrace();
                      });
            }
        });
    }

    private boolean validateRequestData(String username, String password) {
        if (username.length() > 20 || username.equals("")) {
            Helpers.getInstance().showToast(this, "Debe ingresar un usuario válido!", Toast.LENGTH_SHORT);
            return false;
        }

        if (password.length() > 64 || password.equals("")) {
            Helpers.getInstance().showToast(this, "Debe ingresar una contraseña válida!", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    private void goToMainActivity() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }
}
