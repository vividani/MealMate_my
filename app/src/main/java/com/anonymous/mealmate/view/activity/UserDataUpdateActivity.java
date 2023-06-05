package com.anonymous.mealmate.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anonymous.mealmate.databinding.ActivityUserdataupdateBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.model.entity.User;
import com.anonymous.mealmate.viewmodel.UserViewModel;

public class UserDataUpdateActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private ViewFlipper viewFlipper;

    private ActivityUserdataupdateBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_userdataupdate);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding = ActivityUserdataupdateBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        binding.setUserViewModel(userViewModel);
        //test
        binding.setUser(new User("서현승",User.GENDER_MALE,178,74,User.ACTIVITY_RATIO_HIGH,User.PURPOSE_DIET));





        viewFlipper = binding.vfSurvey;

        //viewFlipper.setDisplayedChild(1);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewFlipper.showPrevious();
                finish();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();

            }
        });
        ControlViewState.getInstance().getStateSignalLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer signal) {
                switch (signal){
                    case ControlViewState.ACTIVITY_FINISH:finish();return;
                }
            }
        });


    }
}
