package com.idat.almacen.ui.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.Item;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.SharedData;
import com.idat.almacen.databinding.ItemCardBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements Filterable {

    private List<Item> items;
    private List<Item> allItems;
    private MainActivity activity;

    public InventoryAdapter(MainActivity _activity) {
        activity = _activity;
        items = new ArrayList<>();
        allItems = new ArrayList<>();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Item item = items.get(position);

        Glide.with(activity)
                .load(R.drawable.packageplaceholder)
                .centerCrop()
                .into(holder.binding.itemImage);

        holder.binding.itemName.setText(item.getName());
        holder.binding.itemBrand.setText(item.getBrand());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> _items) {
        items = _items;
        allItems = new ArrayList<>(items);
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allItems);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : allItems) {
                    if (
                        item.getName().toLowerCase().contains(filterPattern) ||
                        item.getBrand().toLowerCase().contains(filterPattern)
                    )
                        filteredList.add(item);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCardBinding binding;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            binding = ItemCardBinding.bind(itemView);
            binding.btnShowItemDetail.setOnClickListener(this::showItemDetail);
        }

        private void showItemDetail(View view) {
            Item item = items.get(getAdapterPosition());
            SharedData.getInstance().setItem(item);
            activity.getNavigationController().navigate(R.id.nav_item_detail);
        }
    }
}
