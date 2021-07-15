package com.idat.almacen.ui.item_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.util.Console;
import com.idat.almacen.databinding.FragmentItemDetailBinding;

import org.jetbrains.annotations.NotNull;

public class ItemDetailFragment extends Fragment {

    private FragmentItemDetailBinding binding;
    private ItemDetailViewModel viewModel;
    private Item item;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentItemDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(ItemDetailViewModel.class);
        viewModel.init();
        viewModel.observeItem()
                .observe(getViewLifecycleOwner(), (response) -> {
                    item = response.getData();
                    binding.itemDetName.setText(item.getName());
                    binding.itemDetBarcode.setText(item.getBarcode());
                    binding.itemDetBrand.setText(item.getBrand());
                    binding.itemDetProvider.setText(item.getProvider().getName());
                    binding.itemDetDescription.setText(item.getDescription());
                    binding.itemDetPrice.setText(String.format("%.2f", item.getPrice()));
                    binding.itemDetStock.setText(String.format("%s", item.getStock()));
                    binding.itemDetStockMin.setText(String.format("%s", item.getStock_min()));
                });
        viewModel.subscribeToRTItem()
                .observe(getViewLifecycleOwner(), (rtData) -> {
                        Console.log("Observador: "+rtData.toString());
                        binding.itemDetName.setText(rtData.getName());
                        binding.itemDetPrice.setText(String.format("%.2f", item.getPrice()));
                });
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        Console.log("Returning to RecyclerView...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
