package com.idat.almacen.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
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
        new Thread(() -> {
            function.run();
        }).start();
    }



    public void showAlertDialog(Context context, String title, Runnable onOk, Runnable onCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the input
        final EditText input = new EditText(builder.getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onOk.run();
                String password = input.getText().toString();
                UserCacheService.getInstance()
                    .getCachedData()
                    .subscribeOn(Schedulers.io())
                    .subscribe(data -> {
                        if (BCrypt.verifyer().verify(password.toCharArray(), data.getPassword()).verified) {
                            onOk.run();
                            dialog.dismiss();
                        }
                    }, err -> {
                        err.printStackTrace();
                    });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCancel.run();
                dialog.cancel();
            }
        });

        builder.show();
    }
}
