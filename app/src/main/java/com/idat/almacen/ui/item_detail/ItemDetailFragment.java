package com.idat.almacen.ui.item_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.ws.NewDataPublishedListener;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentItemDetailBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.Helper;

public class ItemDetailFragment extends Fragment implements NewDataPublishedListener {

    private FragmentItemDetailBinding binding;
    private ItemDetailViewModel viewModel;
    private Gson gson;
    private List<EditText> editTexts;
    private Item item;
    private Helpers helpers;
    private MainActivity activity;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentItemDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(ItemDetailViewModel.class);
        gson = new Gson();
        helpers = Helpers.getInstance();
        activity = (MainActivity) getActivity();
        editTexts = new ArrayList<>();
        editTexts.add(binding.itemDetName);
        editTexts.add(binding.itemDetBarcode);
        editTexts.add(binding.itemDetBrand);
        editTexts.add(binding.itemDetProvider);
        editTexts.add(binding.itemDetDescription);
        editTexts.add(binding.itemDetPrice);
        editTexts.add(binding.itemDetStock);
        editTexts.add(binding.itemDetStock);
        disableEdition();
        binding.btnUpdateItem.setOnClickListener(this::updateItem);
        binding.btnCommitItemChanges.setOnClickListener(this::commitUpdate);
        binding.btnDeleteItem.setOnClickListener(this::deleteItem);
        viewModel.init();
        viewModel.observeItem()
                .observe(getViewLifecycleOwner(), (response) -> {
                    item = response.getData();
                    updateUiFromData(item);
                });
        viewModel.subscribeToRTItem(this);
        return binding.getRoot();
    }

    @Override
    public void onNewData(JsonObject newData) {
        item = gson.fromJson(newData, Item.class);
        updateUiFromData(item);
    }

    public void updateItem(View view) {
        enableEdition();
    }

    public void commitUpdate(View view) {
        disableEdition();
        updateItemFromUi();
        viewModel.updateItem(item)
            .observe(this, response -> {
                helpers.showToast(activity, "Item actualizado", Toast.LENGTH_SHORT);
                updateUiFromData(response.getData());
            });
    }

    public void deleteItem(View view) {
        viewModel.deleteItem(item.getBarcode());
        helpers.showToast(activity, "Usuario eliminado", Toast.LENGTH_SHORT);
        activity.getNavigationController().navigate(R.id.nav_inventory);
    }

    private void enableEdition() {
        binding.btnUpdateItem.setVisibility(View.INVISIBLE);
        binding.btnCommitItemChanges.setVisibility(View.VISIBLE);
        for (EditText et : editTexts) {
            et.setFocusable(true);
            et.setClickable(true);
            et.setFocusableInTouchMode(true);
        }
    }

    private void disableEdition() {
        binding.btnUpdateItem.setVisibility(View.VISIBLE);
        binding.btnCommitItemChanges.setVisibility(View.INVISIBLE);
        for (EditText et : editTexts) {
            et.setFocusable(false);
            et.setClickable(false);
            et.setFocusableInTouchMode(false);
        }
    }

    private void updateUiFromData(Item item) {
        binding.itemDetName.setText(item.getName());
        binding.itemDetBarcode.setText(item.getBarcode());
        binding.itemDetBrand.setText(item.getBrand());
        binding.itemDetProvider.setText(item.getProvider().getName());
        binding.itemDetDescription.setText(item.getDescription());
        binding.itemDetPrice.setText(String.format("%.2f", item.getPrice()));
        binding.itemDetStock.setText(String.format("%s", item.getStock()));
        binding.itemDetStockMin.setText(String.format("%s", item.getStock_min()));
    }

    private void updateItemFromUi() {
        item.setName(binding.itemDetName.getText().toString());
        item.setBarcode(binding.itemDetBarcode.getText().toString());
        item.setBrand(binding.itemDetBrand.getText().toString());
        //item.getProvider(binding.itemDetProvider.getText().toString());
        item.setDescription(binding.itemDetDescription.getText().toString());
        item.setPrice(Float.parseFloat(binding.itemDetPrice.getText().toString()));
        item.setStock(Integer.parseInt(binding.itemDetStock.getText().toString()));
        item.setStock_min(Integer.parseInt(binding.itemDetStockMin.getText().toString()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Console.log("Returning to RecyclerView...");
        viewModel.unsubscribeToRTItem();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
