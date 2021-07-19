package com.idat.almacen.ui.item_new;

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
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.models.Provider;
import com.idat.almacen.core.api.models.Request;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentNewItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewItemFragment extends Fragment {

    private FragmentNewItemBinding binding;
    private NewItemViewModel viewModel;
    private Item item;
    private Helpers helpers;
    private MainActivity activity;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewItemBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(NewItemViewModel.class);
        viewModel.init();
        item = new Item();
        helpers = Helpers.getInstance();
        activity = (MainActivity) getActivity();
        binding.btnSaveNewItem.setOnClickListener(this::saveNewItem);
        return binding.getRoot();
    }

    private void saveNewItem(View view) {
        Item newItem = updateItemFromUi();
        if (!newItem.getName().equals("") &&
            !newItem.getBarcode().equals("") &&
            !newItem.getBrand().equals("") &&
            !binding.newItemProvider.getText().toString().equals("") &&
            !newItem.getDescription().equals("") &&
            !binding.newItemPrice.getText().toString().equals("") &&
            !binding.newItemStock.getText().toString().equals("") &&
            !binding.newItemStockMin.getText().toString().equals("")) {
            viewModel.saveItem(newItem);
            helpers.setTimeout(() -> {
                helpers.runOnUiThread(activity, () -> {
                    helpers.showToast(activity, "Item registrado", Toast.LENGTH_SHORT);
                    activity.getNavigationController().navigate(R.id.nav_inventory);
                });
            }, 200);
        } else {
            helpers.showToast(activity, "Debe llenar todos los campos.", Toast.LENGTH_SHORT);
        }

    }

    private Item updateItemFromUi() {
        item.setName(binding.newItemName.getText().toString());
        item.setBarcode(binding.newItemBarcode.getText().toString());
        item.setBrand(binding.newItemBrand.getText().toString());
        item.setDescription(binding.newItemDescription.getText().toString());
        try {
            item.setProviderId(Integer.parseInt(binding.newItemProvider.getText().toString()));
            item.setPrice(Float.parseFloat(binding.newItemPrice.getText().toString()));
            item.setStock(Integer.parseInt(binding.newItemStock.getText().toString()));
            item.setStock_min(Integer.parseInt(binding.newItemStockMin.getText().toString()));
        } catch (NumberFormatException err) {
            helpers.showToast(activity, "Debe llenar todos los campos.", Toast.LENGTH_SHORT);
        }
        return item;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
