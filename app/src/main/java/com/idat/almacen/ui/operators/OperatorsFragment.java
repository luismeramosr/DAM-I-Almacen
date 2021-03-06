package com.idat.almacen.ui.operators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.core.util.SharedData;
import com.idat.almacen.databinding.FragmentOperatorsBinding;
import com.idat.almacen.ui.loading_dialog.LoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OperatorsFragment extends Fragment {

    private FragmentOperatorsBinding binding;
    private OperatorsViewModel viewModel;
    private OperatorsAdapter adapter;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoading();
        binding = FragmentOperatorsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(OperatorsViewModel.class);
        viewModel.init();
        binding.searchBar.setOnQueryTextListener(filterOperators());
        binding.searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.btnAddOperator.setOnClickListener(this::onAddOperator);
        recyclerView = binding.operators;
        adapter = new OperatorsAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.observeOperators().observe(getViewLifecycleOwner(), data -> {
            if (data!=null) {
                adapter.setOperators(data.getData());
                adapter.notifyDataSetChanged();
            }
        });
        Helpers.getInstance().setTimeout(() -> {
            loadingDialog.stopLoading();
        }, 400);
        return binding.getRoot();
    }

    public SearchView.OnQueryTextListener filterOperators() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        };
    }

    private void onAddOperator(View view) {
        User user = new User();
        MainActivity activity = (MainActivity) getActivity();
        SharedData.getInstance().setUser(user);
        activity.getNavigationController().navigate(R.id.nav_new_operator);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
