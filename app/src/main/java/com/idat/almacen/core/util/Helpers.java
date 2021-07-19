package com.idat.almacen.core.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.idat.almacen.core.app.Almacen;
import com.idat.almacen.core.cache.services.UserCacheService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Helpers {

    private static Helpers instance;

    private Helpers() { }

    public static Helpers getInstance() {
        if (instance == null) {
            instance = new Helpers();
        }
        return instance;
    }

    public void setTimeout(Runnable function, int delay) {
        new Thread(() -> {
           try {
               Thread.sleep(delay);
               function.run();
           } catch (Exception err) {
               System.err.println(err);
           }
        }).start();
    }

    @SuppressLint("NewApi")
    public LocalDateTime timestampToDateTime(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(-5));
    }

    @SuppressLint("ShowToast")
    public void showToast(AppCompatActivity activity, String msg, int duration) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, duration).show());
    }

    public void runOnBackgroundThread(Runnable function) {
        new Thread(function).start();
    }

    public void runOnUiThread(AppCompatActivity activity, Runnable function) {
        activity.runOnUiThread(function);
    }

    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
