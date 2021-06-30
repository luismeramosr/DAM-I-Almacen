package com.idat.almacen.ui.operators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.idat.almacen.R;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.OperatorCardBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class OperatorsAdapter extends RecyclerView.Adapter<OperatorsAdapter.ViewHolder> {

    @Setter
    private List<User> operators;
    private final AppCompatActivity activity;

    public OperatorsAdapter(AppCompatActivity _activity) {
        activity = _activity;
        operators = new ArrayList<>();
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
            binding.userIsActive.setOnCheckedChangeListener(this::onSwitch);
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

        private void onSwitch(CompoundButton compoundButton, boolean b) {
            if (b) {
                compoundButton.setText(R.string.user_Enabled);
            } else {
                compoundButton.setText(R.string.user_Disabled);
            }
        }

        public void onClick(View v) {
            User operator = operators.get(getAdapterPosition());
            Helpers.showToast(activity, operator.getUsername(), Toast.LENGTH_SHORT);
        }

    }
}
