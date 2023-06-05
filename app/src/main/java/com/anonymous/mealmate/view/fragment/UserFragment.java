package com.anonymous.mealmate.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anonymous.mealmate.databinding.FragmentUserBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.model.entity.User;
import com.anonymous.mealmate.viewmodel.UserViewModel;
import com.google.android.material.navigation.NavigationView;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;

    UserViewModel userViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding = FragmentUserBinding.inflate(inflater);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setUserViewModel(userViewModel);
        binding.setUser(new User("서현승", User.GENDER_MALE, 178, 74, User.ACTIVITY_RATIO_HIGH, User.PURPOSE_DIET));

        binding.nvUserMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ControlViewState.getInstance().activeIntentSignal(ControlViewState.INTENT_MAIN_TO_USERUPDATEDATA);
                return false;
            }
        });

        return binding.getRoot();
    }
}
