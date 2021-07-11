package com.idat.almacen.ui.operator_new;

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
import com.idat.almacen.core.api.models.User;
import com.idat.almacen.core.util.Helpers;
import com.idat.almacen.databinding.FragmentNewOperatorBinding;

public class NewOperatorFragment extends Fragment {

    private FragmentNewOperatorBinding binding;
    private NewOperatorViewModel viewModel;
    private User user;
    private Helpers helpers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentNewOperatorBinding.inflate(getLayoutInflater());
        helpers = Helpers.getInstance();
        viewModel = new ViewModelProvider(this).get(NewOperatorViewModel.class);
        viewModel.init();
        binding.btnSaveNewUser.setOnClickListener(this::saveUser);
        binding.btnCancelNewUser.setOnClickListener(this::cancel);
        viewModel.observeUser()
                .observe(this, (response) -> {
                    user = response.getData();
                    binding.userDetFirstName.setText(user.getFirstName());
                    binding.userDetLastName.setText(user.getLastName());
                    binding.userDetDni.setText(String.valueOf(user.getDni()));
                    binding.userDetPhone.setText(String.valueOf(user.getPhone()));
                    binding.userDetEmail.setText(user.getEmail());
                    binding.userDetScheduleId.setText(String.valueOf(user.getIdSchedule()));
                });
    }

    private void saveUser(View view) {
        viewModel.saveUser(getUserFromUi())
            .observe(this, (newUser -> {
                MainActivity activity = (MainActivity) getActivity();
                helpers.setTimeout(() -> {
                    helpers.runOnUiThread(activity, () -> {
                        helpers.showToast(activity, "Operario registrado", Toast.LENGTH_SHORT);
                        activity.getNavigationController().navigate(R.id.nav_operators);
                    });
                }, 200);
            }));
    }

    private void cancel(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getNavigationController().navigate(R.id.nav_operators);
    }

    private User getUserFromUi() {
        User editedUser = user;
        editedUser.setFirstName(binding.userDetFirstName.getText().toString());
        editedUser.setLastName(binding.userDetLastName.getText().toString());
        editedUser.setDni(Integer.parseInt(binding.userDetDni.getText().toString()));
        editedUser.setPhone(Integer.parseInt(binding.userDetPhone.getText().toString()));
        editedUser.setEmail(binding.userDetEmail.getText().toString());
        editedUser.setIdRole(2);
        editedUser.setIdSchedule(Integer.parseInt(binding.userDetScheduleId.getText().toString()));
        return editedUser;
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
