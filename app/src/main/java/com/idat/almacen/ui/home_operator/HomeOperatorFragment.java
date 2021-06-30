package com.idat.almacen.ui.home_operator;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.idat.almacen.databinding.FragmentHomeAdminBinding;
import com.idat.almacen.databinding.FragmentHomeOperatorBinding;
import com.idat.almacen.ui.home_admin.HomeAdminViewModel;

public class HomeOperatorFragment extends Fragment {

    private HomeOperatorViewModel viewModel;
    private FragmentHomeOperatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(HomeOperatorViewModel.class);
        viewModel.init(getContext());
        binding = FragmentHomeOperatorBinding.inflate(getLayoutInflater());
        viewModel.getUserData().observe((LifecycleOwner) getContext(), (data) -> {
            binding.tv.setText(data.getRole());
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
