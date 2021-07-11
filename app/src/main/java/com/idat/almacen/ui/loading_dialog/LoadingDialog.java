package com.idat.almacen.ui.loading_dialog;

import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.almacen.R;

public class LoadingDialog {

    private AppCompatActivity activity;
    private AlertDialog dialog;

    public LoadingDialog(AppCompatActivity _activity) {
        activity = _activity;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null, false));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public void stopLoading() {
        dialog.dismiss();
    }

}
