package com.anonymous.mealmate.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.anonymous.mealmate.databinding.FragmentHomeBinding;
import com.anonymous.mealmate.feature.Date;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.model.entity.User;
import com.anonymous.mealmate.view.adapter.MealAdapter;
import com.anonymous.mealmate.viewmodel.MealCheckViewModel;

import java.util.List;

public class HomeFragment extends Fragment {


    private MealCheckViewModel mealCheckViewModel;

    private MealAdapter mAdapter;

    private RecyclerView mRecyclerView;

    //test
    private FragmentHomeBinding binding;

    //public HomeFragment(){


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mealCheckViewModel = new ViewModelProvider(this).get(MealCheckViewModel.class);
       // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setMealCheckViewModel(mealCheckViewModel);
        binding.setDate(Date.getInstance()); // 오늘 날짜로 기본 세팅
        binding.setUser(new User("서현승",User.GENDER_MALE,178,74,User.ACTIVITY_RATIO_HIGH,User.PURPOSE_DIET));


        //binding.includeMealList.includeDatetimeInfo.setDate(Date.getInstance());

        //view = inflater.inflate( R.layout.fragment_home,container,false);



        // 직접연결된 뷰가아니라 에러 날수도 있음 xml 안에 include 된 view 의 id를 참조하는중
        //recycler view setup
        mRecyclerView = binding.includeMealList.rvMealLoading;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.scrollToPosition(((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());


        //mAdapter = new MealAdapter(new MealAdapter.MealDiff(),mealCheckViewModel,this);
        mAdapter = new MealAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        // recycler view set end


        // observer part
        //viewModel_checkMeal = ViewModelProviders.of(this).get(ViewModel_CheckMeal.class);

        mealCheckViewModel.getAllMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                //test
                mAdapter.submitList(meals);
                //testing toast message
                //Toast.makeText(getContext(), "test success", Toast.LENGTH_SHORT).show();
            }
        });
        // observer end

        //test Event
//        Button btnTest = view.findViewById(R.id.View_user_info);
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mealCheckViewModel.insert(new Meal("230524",0));
//            }
//        });
//        Button btnTest2 = view.findViewById(R.id.View_meal_chart);
//        btnTest2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mAdapter.getItemCount()!=0)
//                    mealCheckViewModel.delete(mAdapter.getItemTest(0));
//            }
//        });
        // end test code

        return binding.getRoot();
    }
}

