package com.idat.almacen.ui.home_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.idat.almacen.core.util.Console;
import com.idat.almacen.databinding.FragmentHomeAdminBinding;
import com.idat.almacen.databinding.FragmentHomeOperatorBinding;

import lombok.Setter;

public class HomeAdminFragment extends Fragment {

    private HomeAdminViewModel viewModel;
    private FragmentHomeAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(HomeAdminViewModel.class);
        viewModel.init(getContext());
        binding = FragmentHomeAdminBinding.inflate(getLayoutInflater());
        viewModel.getUserCache().observe((LifecycleOwner) getActivity(), data -> {
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