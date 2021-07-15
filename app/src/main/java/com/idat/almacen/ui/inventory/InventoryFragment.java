package com.idat.almacen.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.ws.WebSocketClient;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentInventoryBinding;
import com.idat.almacen.ui.loading_dialog.LoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;
    private InventoryViewModel viewModel;
    private InventoryAdapter adapter;
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
        binding = FragmentInventoryBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        viewModel.init();
        binding.btnAddItem.setOnClickListener(this::addItem);
        binding.itemSearchBar.setOnQueryTextListener(filterItems());
        binding.itemSearchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView = binding.items;
        adapter = new InventoryAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.observeItems()
                .observe(getViewLifecycleOwner(), (response) -> {
                    adapter.setItems(response.getData());
                    adapter.notifyDataSetChanged();
                });
        Helpers.getInstance().setTimeout(() -> {
            loadingDialog.stopLoading();
        }, 400);

        return binding.getRoot();
    }

    private void addItem(View view) {
    }

    private SearchView.OnQueryTextListener filterItems() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
