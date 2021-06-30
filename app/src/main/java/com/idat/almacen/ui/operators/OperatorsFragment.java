package com.idat.almacen.ui.operators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.databinding.FragmentOperatorsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OperatorsFragment extends Fragment {

    private FragmentOperatorsBinding binding;
    private OperatorsViewModel viewModel;
    private OperatorsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOperatorsBinding.inflate(getLayoutInflater());
        recyclerView = binding.operators;
        viewModel = new ViewModelProvider(this).get(OperatorsViewModel.class);
        viewModel.init();
        adapter = new OperatorsAdapter((AppCompatActivity) getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.observeOperators().observe(this, data -> {
            if (data!=null) {
                Console.log(data.toString());
                adapter.setOperators(data.getData());
                adapter.notifyDataSetChanged();
            }
        });
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
