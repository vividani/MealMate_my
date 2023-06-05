package com.anonymous.mealmate.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.anonymous.mealmate.databinding.ActivitySetmealitemBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.feature.Date;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.view.adapter.MealItemAdapter;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;

import java.util.List;

public class SetMealItemActivity extends AppCompatActivity {

    private MealSetViewModel mealSetViewModel;
    private ActivitySetmealitemBinding binding;

    private RecyclerView mRecyclerView;
    private MealItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setmealitem);
//        binding = ActivitySetmealitemBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        //setMealItems.add(testList);
        //liveData.postValue(setMealItems);

        //Get ViewModel
        mealSetViewModel = new ViewModelProvider(this).get(MealSetViewModel.class);

        //binding setting ****** 매우중요
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_setmealitem);
        binding = ActivitySetmealitemBinding.inflate(LayoutInflater.from(this));
        binding.setMealSetViewModel(mealSetViewModel);
        binding.setDate(Date.getInstance());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        Intent it = getIntent();

        //mAdapter = new MealItemAdapter(new MealItemAdapter.MealItemDiff(), mealSetViewModel, this);
        mAdapter = new MealItemAdapter(this);

        mRecyclerView = binding.rvMealSetting;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.scrollToPosition(((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());

        mealSetViewModel.getMealsLiveData().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                mAdapter.submitList(meals);
            }
        });


        //event
        binding.btnMealBack.setOnClickListener((v) ->{
            finish();
        });

        //intent state observer
        ControlViewState.getInstance().getStateSignalLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer signal) {
                switch(signal){
                    case ControlViewState.INTENT_SETMEAL_TO_FOOD :
                        startActivity(new Intent(SetMealItemActivity.this,FoodActivity.class));
                        break;
                    case ControlViewState.ADAPTER_DATA_CHANGED:
                        mAdapter.notifyDataSetChanged();break;
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mAdapter.notifyDataSetChanged();
    }
}
