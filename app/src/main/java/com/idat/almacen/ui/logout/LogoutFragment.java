package com.idat.almacen.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.idat.almacen.activities.LoginActivity;
import com.idat.almacen.databinding.FragmentLogoutBinding;

import org.jetbrains.annotations.NotNull;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(getLayoutInflater());
        AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
        mainActivity.getSupportActionBar().hide();
        binding.animationLogout.postOnAnimationDelayed(() -> {
            Intent intent = new Intent(mainActivity, LoginActivity.class);
            startActivity(intent);
            mainActivity.finish();
        }, 2000);
        return binding.getRoot();
    }
}
