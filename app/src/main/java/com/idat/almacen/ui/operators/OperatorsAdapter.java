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
import android.widget.Filter;
import android.widget.Filterable;
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

public class OperatorsAdapter extends RecyclerView.Adapter<OperatorsAdapter.ViewHolder> implements Filterable {


    private final MainActivity activity;
    private List<User> operators;
    private List<User> allOperators;
    private UserService service;

    public OperatorsAdapter(MainActivity _activity) {
        activity = _activity;
        operators = new ArrayList<>();
        allOperators = new ArrayList<>();
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
        if (!operator.isActive())
            holder.binding.btnUnlockUser.setVisibility(View.VISIBLE);
        else
            holder.binding.btnUnlockUser.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public void setOperators(List<User> _operators) {
        operators = _operators;
        allOperators = new ArrayList<>(operators);
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allOperators);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User operator : allOperators) {
                    if (
                        String.format("%s %s",
                            operator.getFirstName(),
                            operator.getLastName()).toLowerCase().contains(filterPattern)
                    ) {
                        filteredList.add(operator);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            operators.clear();
            operators.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OperatorCardBinding binding;

        public ViewHolder(@NotNull View item) {
            super(item);
            binding = OperatorCardBinding.bind(item);
            binding.userPhone.setOnClickListener(this::onCall);
            binding.btnShowDetails.setOnClickListener(this::onClick);
            binding.btnUnlockUser.setOnClickListener(this::onUnlock);
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

        private void onUnlock(View view) {
            User operator = operators.get(getAdapterPosition());
            operator.setActive(true);
            service.updateUser(operator)
                    .subscribe((res) -> {
                        Helpers.getInstance().showToast(activity, "Usuario desbloqueado", Toast.LENGTH_SHORT);
                        binding.btnUnlockUser.setVisibility(View.INVISIBLE);
                    }, (err) -> {
                        Helpers.getInstance().showToast(activity, "No se pudo desbloquear al usuario", Toast.LENGTH_SHORT);
                        err.printStackTrace();
                    });
            // Remember for the cart
            //operators.remove(operator);
            //notifyDataSetChanged();
        }

        @SuppressLint("ResourceType")
        public void onClick(View v) {
            User operator = operators.get(getAdapterPosition());
            SharedData.getInstance().setUser(operator);
            activity.getNavigationController().navigate(R.id.nav_operator_detail);
        }

    }
}
