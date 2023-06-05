package com.anonymous.mealmate.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.databinding.FragmentFoodBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.model.entity.Food;
import com.anonymous.mealmate.view.adapter.FoodAdapter;
import com.anonymous.mealmate.viewmodel.FoodViewModel;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;

import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private FragmentFoodBinding binding;
    private FoodViewModel foodViewModel;

    private MealSetViewModel mealSetViewModel;
    private RecyclerView mRecyclerView;
    private FoodAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealSetViewModel = new ViewModelProvider(this).get(MealSetViewModel.class);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        binding = FragmentFoodBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setFoodViewModel(foodViewModel);
        setContentView(binding.getRoot());


        //recycler view
        mRecyclerView = binding.rvFoodSearch;
        //mAdapter = new FoodAdapter(new FoodAdapter.FoodDiff(), foodViewModel, this);
        mAdapter = new FoodAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        //recycler view end

        foodViewModel.getSearchResults().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                mAdapter.setFoods(foods);

                Toast.makeText(FoodActivity.this, "APIAcessComplete!!", Toast.LENGTH_SHORT).show();
            }
        });
        //observer setup
        foodViewModel.getAllFoods().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                //observe 함수 작성
                mAdapter.submitList(foods);
                //testing toast message
                Toast.makeText(FoodActivity.this, "foodListAcessComplete!!", Toast.LENGTH_SHORT).show();
            }
        });

        ControlViewState.getInstance().getStateSignalLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer signal) {
                switch (signal){
                    case ControlViewState.ACTIVITY_FINISH:
                        finish();break;
                }
            }
        });


        Intent intent = getIntent();
    }
}
