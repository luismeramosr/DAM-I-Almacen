package com.idat.almacen.ui.operator_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.idat.almacen.R;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.SharedData;
import com.idat.almacen.databinding.FragmentOperatorDetailBinding;

import org.jetbrains.annotations.NotNull;

public class OperatorDetailFragment extends Fragment {

    private FragmentOperatorDetailBinding binding;
    private User operator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOperatorDetailBinding.inflate(getLayoutInflater());
        operator = SharedData.getInstance().getUser();
        binding.textView.setText(operator.getFirstName());
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
