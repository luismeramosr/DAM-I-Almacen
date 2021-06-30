package com.idat.almacen.ui.operators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.api.services.UserService;
import com.idat.almacen.core.app.Almacen;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.core.util.SharedData;
import com.idat.almacen.databinding.OperatorCardBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class OperatorsAdapter extends RecyclerView.Adapter<OperatorsAdapter.ViewHolder> {


    private final MainActivity activity;
    private final Fragment fragment;
    @Setter
    private List<User> operators;
    private UserService service;

    public OperatorsAdapter(MainActivity _activity, Fragment _fragment) {
        activity = _activity;
        fragment = _fragment;
        operators = new ArrayList<>();
        service = UserService.getInstance();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.operator_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User operator = operators.get(position);
        Glide.with(activity)
                .load(R.drawable.avatarplaceholder)
                .centerCrop()
                .into(holder.binding.userImage);

        holder.binding.userFullName.setText(String.format(
                "%s %s",
                operator.getFirstName(),
                operator.getLastName()
        ));
        holder.binding.userPhone.setText(String.valueOf(operator.getPhone()));
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        SwitchMaterial userState = holder.binding.userIsActive;
        if (operator.isActive()) {
            userState.setChecked(true);
            userState.setText(R.string.user_Enabled);
        } else {
            userState.setChecked(false);
            userState.setText(R.string.user_Disabled);
        }
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OperatorCardBinding binding;

        public ViewHolder(@NotNull View item) {
            super(item);
            binding = OperatorCardBinding.bind(item);
            binding.userPhone.setOnClickListener(this::onCall);
            binding.btnShowDetails.setOnClickListener(this::onClick);
            binding.userIsActive.setOnClickListener(this::onSwitch);
        }

        private void onCall(View view) {
            Intent intent = new Intent(
                Intent.ACTION_DIAL,
                Uri.parse(String.format(
                    "tel: %s", binding.userPhone.getText()
                ))
            );
            activity.startActivity(intent);
        }

        private int getStringResource(boolean isActive) {
            if (isActive)
                return R.string.user_Enabled;
            else
                return R.string.user_Disabled;
        }

        private void updateUser() {

        }

        private void onSwitch(View view) {
            User operator = operators.get(getAdapterPosition());
            boolean isActive = binding.userIsActive.isChecked();
            operator.setActive(isActive);
            Helpers.getInstance().showAlertDialog(activity, "Ingrese su contraseña para continuar", () -> {

                Helpers.getInstance().runOnBackgroundThread(() -> {
                    UserService.getInstance()
                        .updateUser(operator)
                        .subscribe(res -> {
                            binding.userIsActive.setText(getStringResource(isActive));
                            binding.userIsActive.setChecked(isActive);
                        }, err -> {
                            err.printStackTrace();
                        }).dispose();
                    });
                }, () -> {
                    binding.userIsActive.setChecked(!isActive);
            });
        }

        @SuppressLint("ResourceType")
        public void onClick(View v) {
            User operator = operators.get(getAdapterPosition());
            SharedData.getInstance().setUser(operator);
            activity.getNavigationController().navigate(R.id.nav_operator_detail);
        }

    }
}
