package com.anonymous.mealmate.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anonymous.mealmate.R;

import com.anonymous.mealmate.databinding.ActivityMainBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.feature.Date;
import com.anonymous.mealmate.view.fragment.CalendarFragment;
import com.anonymous.mealmate.view.fragment.FoodFragment;
import com.anonymous.mealmate.view.fragment.HomeFragment;
import com.anonymous.mealmate.view.fragment.UserFragment;
import com.anonymous.mealmate.viewmodel.MealCheckViewModel;
import com.anonymous.mealmate.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private FoodFragment foodFragment;

    private UserFragment userFragment;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private ActivityMainBinding binding;

    // test 용 뷰모델 mainactivity에서 뷰모델 모두 생성한다음 fragment에 넘기기 vs 프래그먼트에서 자체 생성 2안이 나아보임
    // dateChange되면 meal load를 갱신하기위한 observer mainactivity에서 구현
    private MealCheckViewModel mealCheckViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mealCheckViewModel = new ViewModelProvider(this).get(MealCheckViewModel.class);
        //setContentView(R.layout.activity_main);


        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        binding.setLifecycleOwner(this);
        binding.setSelectedDate(Date.getInstance());
        setContentView(binding.getRoot());



        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        foodFragment = new FoodFragment();
        userFragment = new UserFragment();
        fragmentManager.beginTransaction().replace(R.id.fl_navigation, homeFragment);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_navigation_home:
                        fragmentManager.beginTransaction().replace(R.id.fl_navigation, homeFragment).commit();
                        Date.getInstance().setToDayDateTime();
                    return true;
                    case R.id.item_navigation_calendar:fragmentManager.beginTransaction().replace(R.id.fl_navigation, calendarFragment).commit();
                    return true;
                    case R.id.item_navigation_food:fragmentManager.beginTransaction().replace(R.id.fl_navigation, foodFragment).commit();
                    return true;
                    case R.id.item_navigation_user:fragmentManager.beginTransaction().replace(R.id.fl_navigation,userFragment).commit();
                    return true;

                    default: return false;
                }
            }
        });

        binding.bottomNavigationView.setItemIconTintList(null);

        binding.fabMealModify.setOnClickListener((v) ->{
            ControlViewState.getInstance().activeIntentSignal(ControlViewState.INTENT_MAIN_TO_SETMEAL);
        });

        //observe active signal
        ControlViewState.getInstance().getStateSignalLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer intentSignal) {
                Intent intent;
                switch (intentSignal){
                    case ControlViewState.INTENT_MAIN_TO_SETMEAL:
                        intent = new Intent(MainActivity.this,SetMealItemActivity.class);
                        startActivity(intent); break;
                    case ControlViewState.INTENT_MAIN_TO_USERUPDATEDATA:
                        intent = new Intent(MainActivity.this,UserDataUpdateActivity.class);
                        startActivity(intent); break;
                    default:return;
                }

            }
        });

        ControlViewState.getInstance().getStateSignalLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer dialogSignal) {
                switch (dialogSignal){
                    //Todo dialog 작성하여 연결
                    case ControlViewState.DIALOG_FOOD_DATASET:
                }

            }
        });

    }
}
