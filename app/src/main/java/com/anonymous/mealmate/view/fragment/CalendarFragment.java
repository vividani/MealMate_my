package com.anonymous.mealmate.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.databinding.FragmentCalendarBinding;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.feature.Date;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.view.adapter.MealAdapter;
import com.anonymous.mealmate.viewmodel.MealCheckViewModel;

import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    private MealCheckViewModel mealCheckViewModel;
    private RecyclerView mRecyclerView;
    private MealAdapter mAdapter;

    private FragmentCalendarBinding binding;

    public CalendarFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mealCheckViewModel = new ViewModelProvider(this).get(MealCheckViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);
        binding.setMealCheckViewModel(mealCheckViewModel);
        binding.setDate(Date.getInstance()); // 싱글톤으로 static 구현된 date 가져옴



        //mAdapter = new MealAdapter(new MealAdapter.MealDiff(),mealCheckViewModel,this);
        mAdapter = new MealAdapter(getContext());

        //recycler view set up
//        mRecyclerView = view.findViewById(R.id.rv_meal_loading);

        mRecyclerView=binding.includeMealList.rvMealLoading;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.scrollToPosition(((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        mRecyclerView.setAdapter(mAdapter);
        //recycler view set end

        // observer setup


        mealCheckViewModel.getAllMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                mAdapter.submitList(meals);

                //testing toast message
                //Toast.makeText(getContext(), "calendar fragment access complete", Toast.LENGTH_SHORT).show();
            }
        });

        Date.getInstance().getDateChangedSignalLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.includeMealList.includeDatetimeInfo.setDate(Date.getInstance());
            }
        });

        // observer end

        //eventListener


//        return view;
        return binding.getRoot();
    }
}
