package com.idat.almacen.ui.operator_new;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.idat.almacen.R;
import com.idat.almacen.activities.MainActivity;
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentNewOperatorBinding;

import static androidx.core.content.ContextCompat.getSystemService;

public class NewOperatorFragment extends Fragment {

    private FragmentNewOperatorBinding binding;
    private NewOperatorViewModel viewModel;
    private User user;
    private Helpers helpers;
    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        binding = FragmentNewOperatorBinding.inflate(getLayoutInflater());
        helpers = Helpers.getInstance();
        viewModel = new ViewModelProvider(this).get(NewOperatorViewModel.class);
        viewModel.init();
        binding.btnSaveNewUser.setOnClickListener(this::saveUser);
        binding.btnCancelNewUser.setOnClickListener(this::cancel);
        viewModel.observeUser()
                .observe(this, (response) -> {
                    user = response.getData();
                });
    }

    private void saveUser(View view) {
        hideSoftKeyboard();
        viewModel.saveUser(getUserFromUi())
            .observe(this, (newUser -> {
                helpers.setTimeout(() -> {
                    helpers.runOnUiThread(activity, () -> {
                        helpers.showToast(activity, "Operario registrado", Toast.LENGTH_SHORT);
                        activity.getNavigationController().navigate(R.id.nav_operators);
                    });
                }, 200);
            }));
    }

    private void cancel(View view) {
        activity.getNavigationController().navigate(R.id.nav_operators);
    }

    private User getUserFromUi() {
        User editedUser = user;
        editedUser.setFirstName(binding.userDetFirstName.getText().toString());
        editedUser.setLastName(binding.userDetLastName.getText().toString());
        editedUser.setDni(binding.userDetDni.getText().toString());
        editedUser.setPhone(binding.userDetPhone.getText().toString());
        editedUser.setEmail(binding.userDetEmail.getText().toString());
        editedUser.setIdRole(2);
        editedUser.setIdSchedule(Integer.parseInt(binding.userDetScheduleId.getText().toString()));
        return editedUser;
    }

    public void hideSoftKeyboard(){
        InputMethodManager imm =(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
