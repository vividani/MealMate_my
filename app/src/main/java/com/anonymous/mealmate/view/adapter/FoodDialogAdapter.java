package com.anonymous.mealmate.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.databinding.AdapterMealBinding;

import com.anonymous.mealmate.databinding.DialogFoodBinding;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.viewmodel.MealCheckViewModel;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;


public class FoodDialogAdapter extends ListAdapter<Meal,FoodDialogAdapter.FoodDialogViewHolder>{
//    private LifecycleOwner lifecycleOwner;
    private MealSetViewModel mealSetViewModel;

    private ViewModelProvider viewModelProvider;
    private Context context;

    public static class FoodDialogViewHolder extends RecyclerView.ViewHolder{
//        private TextView tvMealInfo;
//        private LinearLayout llMealMenu;
//        private Button btnMealCheck;

        private DialogFoodBinding binding;

        public FoodDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            // not use
//            tvMealInfo = itemView.findViewById(R.id.tv_meal_info);
//            llMealMenu = itemView.findViewById(R.id.ll_meal_menu);
//            btnMealCheck = itemView.findViewById(R.id.btn_meal_check);
        }
        public FoodDialogViewHolder(@NonNull DialogFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Meal meal){
            // 모두 수정 필요
            // data setting method
//            tvMealInfo.setText("1번째 끼니");
            //meal 에서 음식정보를 찾아오는 로직 필요

            //테이블이 약간 이상하다
//            TextView testText = new TextView(llMealMenu.getContext());
//            testText.setText("1. Test Data");

            // list의 옵션 삭제시 뷰가 안사라지고 계속 add되고 있음 수정필요
//            llMealMenu.addView(testText);
            binding.setMeal(meal);
            //binding.setFoods();
        }
        private DialogFoodBinding getBinding(){
            return binding;
        }

//        public static MealViewHolder onCreate(ViewGroup parent){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_meal, parent,false);
//            return new MealViewHolder(view);
//        }
    }
//    public FoodDialogAdapter(@NonNull DiffUtil.ItemCallback<Meal> diffCallback, MealSetViewModel mealSetViewModel, LifecycleOwner lifecycleOwner) {
//        super(diffCallback);
//        this.mealSetViewModel= mealSetViewModel;
//        this.lifecycleOwner =lifecycleOwner;
//    }

    public FoodDialogAdapter(@NonNull Context context){
        super(new DiffUtil.ItemCallback<Meal>() {
            @Override // Todo 아이템 비교로직 작성 필요
            public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return false;
            }
        });
        this.context = context;
        viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) context);
        mealSetViewModel = viewModelProvider.get(MealSetViewModel.class);
    }

    @NonNull
    @Override
    public FoodDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DialogFoodBinding binding = DialogFoodBinding.inflate(inflater);
        binding.setLifecycleOwner((LifecycleOwner) context);
        binding.setMealSetViewModel(mealSetViewModel);

        //        binding.setLifecycleOwner(lifecycleOwner);
//        binding.setFoods(new Foods());
//        return MealViewHolder.onCreate(parent);
        return new FoodDialogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDialogViewHolder holder, int position) {
        Meal currentMeal = getItem(position);
        holder.bind(currentMeal);
    }

//    public static class MealDiff extends DiffUtil.ItemCallback<Meal>{
//        @Override
//        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//            return oldItem == newItem;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//            return oldItem.getMealIndex()==newItem.getMealIndex();
//        }
//    }
}

