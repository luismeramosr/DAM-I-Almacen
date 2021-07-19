package com.idat.almacen.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private Helpers helpers;
    private MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.init();
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        binding.btnUpdatePassword.setOnClickListener(this::updatePassword);
        activity = (MainActivity) getActivity();
        helpers = Helpers.getInstance();
        return binding.getRoot();
    }

    private void updatePassword(View view) {
        String newPassword = binding.userProfileNewPassword.getText().toString();
        if (!newPassword.equals("")) {
            viewModel.updatePassword(newPassword);
            helpers.setTimeout(() -> {
                helpers.hideKeyboard(activity);
                activity.getNavigationController().navigate(R.id.nav_logout);
            }, 500);
        } else {
            helpers.showToast(activity, "Debe ingresar una contraseña.", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
