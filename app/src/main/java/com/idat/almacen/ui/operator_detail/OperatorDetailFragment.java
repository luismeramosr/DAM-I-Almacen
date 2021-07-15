package com.idat.almacen.ui.operator_detail;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.core.util.SharedData;
import com.idat.almacen.databinding.FragmentOperatorDetailBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OperatorDetailFragment extends Fragment {

    private FragmentOperatorDetailBinding binding;
    private OperatorDetailViewModel viewModel;
    private User user;
    private List<EditText> editTexts;
    private Helpers helpers;
    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOperatorDetailBinding.inflate(getLayoutInflater());
        activity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(OperatorDetailViewModel.class);
        viewModel.init();
        helpers = Helpers.getInstance();
        editTexts = new ArrayList<>();
        editTexts.add(binding.userDetFirstName);
        editTexts.add(binding.userDetLastName);
        editTexts.add(binding.userDetDni);
        editTexts.add(binding.userDetPhone);
        editTexts.add(binding.userDetEmail);
        editTexts.add(binding.userDetScheduleId);
        binding.btnUpdateUser.setOnClickListener(this::updateUser);
        binding.btnCommitChanges.setOnClickListener(this::commitUpdate);
        binding.btnDeleteUser.setOnClickListener(this::deleteUser);
        viewModel.observeUser().observe(this, (response -> {
            user = response.getData();
            binding.userDetFirstName.setText(user.getFirstName());
            binding.userDetLastName.setText(user.getLastName());
            binding.userDetDni.setText(String.valueOf(user.getDni()));
            binding.userDetPhone.setText(String.valueOf(user.getPhone()));
            binding.userDetEmail.setText(user.getEmail());
            binding.userDetScheduleId.setText(String.valueOf(user.getIdSchedule()));
        }));
        disableEdition();
    }

    private void updateUser(View view) {
        enableEdition();
    }

    private void commitUpdate(View view) {
        disableEdition();
        viewModel.updateUser(getUserFromUi())
              .observe(this, (response) -> {
                  helpers.setTimeout(() -> {
                      helpers.runOnUiThread(activity, () -> {
                          helpers.showToast(activity, "Operario actualizado", Toast.LENGTH_SHORT);
                          activity.getNavigationController().navigate(R.id.nav_operators);
                      });
                  }, 200);
              });
    }

    private void deleteUser(View view) {
        viewModel.deleteUser(getUserFromUi().getId());
        helpers.showToast(activity, "Usuario eliminado", Toast.LENGTH_SHORT);
        activity.getNavigationController().navigate(R.id.nav_operators);
    }

    private void enableEdition() {
        binding.btnUpdateUser.setVisibility(View.INVISIBLE);
        binding.btnCommitChanges.setVisibility(View.VISIBLE);
        for (EditText et : editTexts) {
            et.setFocusable(true);
            et.setClickable(true);
            et.setFocusableInTouchMode(true);
        }
    }

    private void disableEdition() {
        binding.btnUpdateUser.setVisibility(View.VISIBLE);
        binding.btnCommitChanges.setVisibility(View.INVISIBLE);
        for (EditText et : editTexts) {
            et.setFocusable(false);
            et.setClickable(false);
            et.setFocusableInTouchMode(false);
        }
    }

    private User getUserFromUi() {
        User editedUser = user;
        editedUser.setFirstName(binding.userDetFirstName.getText().toString());
        editedUser.setLastName(binding.userDetLastName.getText().toString());
        editedUser.setDni(binding.userDetDni.getText().toString());
        editedUser.setPhone(binding.userDetPhone.getText().toString());
        editedUser.setEmail(binding.userDetEmail.getText().toString());
        editedUser.setIdSchedule(Integer.parseInt(binding.userDetScheduleId.getText().toString()));
        return editedUser;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
