package com.anonymous.mealmate.view.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.R;
import com.anonymous.mealmate.databinding.AdapterFoodBinding;
import com.anonymous.mealmate.databinding.AdapterMealitemBinding;
import com.anonymous.mealmate.databinding.FragmentFoodBinding;
import com.anonymous.mealmate.model.entity.Food;
import com.anonymous.mealmate.viewmodel.FoodViewModel;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;

import java.util.List;


public class FoodAdapter extends ListAdapter<Food,FoodAdapter.FoodViewHolder>{
    //lifecycle 순서 - > 생성자 실행후 -> onCreateViewHolder  -> FoodViewHolder 생성자 실행후 hold 메모리에 유지
    // -> item list 갱신 받고  onBindViewHolder 하여 item별로 기능 차이 설정 position 값을 이용해 item구분
    private FoodViewModel foodViewModel;
    private MealSetViewModel mealSetViewModel;

    //매우 중요 *** context는 각종 Owner클래스로 convert 가능함 -> context의 제대로 된 사용법 여러 매개변수를 사용하지 않고  context or application 만전달하여 convert해서 사용
    // ex) (LifeCycleOwner)context , (ViewModelStoreOwner)context 진짜 중요함
    private ViewModelProvider viewModelProvider;
    private Context context;
//    public FoodAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback, FoodViewModel foodViewModel, LifecycleOwner lifecycleOwner) {
//        super(diffCallback);
//        this.foodViewModel = foodViewModel;
//        this.lifecycleOwner = lifecycleOwner;
//
//    }
    public FoodAdapter( @NonNull Context context){
        super(new DiffUtil.ItemCallback<Food>() {
            @Override
            public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                //정확한 비교 수정필요
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                // 정확한 비교 수정필요
                return oldItem.equals(newItem);
            }
        });

        this.context = context;

        // get ViewModel
        viewModelProvider = new ViewModelProvider((ViewModelStoreOwner)context);
        foodViewModel = viewModelProvider.get(FoodViewModel.class);
        mealSetViewModel = viewModelProvider.get(MealSetViewModel.class);

    }

    public void setFoods(List<Food> foods) {
        submitList(foods);
    }



    public static class FoodViewHolder extends RecyclerView.ViewHolder{

//        private TextView tvFoodName;
//        private Button btnFoodLike;
//        private TextView tvFoodNutritionCarbohydrate;
//        private TextView tvFoodNutritionProtein;
//        private TextView tvFoodNutritionFat;

        private AdapterFoodBinding binding;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            //view 할당, 초기 1회 실행
//            tvFoodName=itemView.findViewById(R.id.tv_food_name);
//            btnFoodLike=itemView.findViewById(R.id.btn_food_like);
//            tvFoodNutritionCarbohydrate=itemView.findViewById(R.id.tv_food_nutrition_carbohydrate);
//            tvFoodNutritionProtein=itemView.findViewById(R.id.tv_food_nutrition_protein);
//            tvFoodNutritionFat =itemView.findViewById(R.id.tv_food_nutrition_fat);
        }
        public FoodViewHolder(@NonNull AdapterFoodBinding binding){
            super(binding.getRoot());
            this.binding=binding;

        }
        public void bind(Food food){
            // view data 갱신
//            tvFoodName.setText(food.getFoodName());
//            tvFoodNutritionCarbohydrate.setText(""+food.getFoodCarbohydrates());
//            tvFoodNutritionProtein.setText(""+food.getFoodProtein());
//            tvFoodNutritionFat.setText(""+food.getFoodFat());

            binding.setFood(food);
        }

//        public static FoodViewHolder onCreate(ViewGroup parent){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_food, parent,false);
//            return new FoodViewHolder(view);
//        }

    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //binding에 viewModel 연결하고, lifecycleOwner 설정해주고 리턴, ViewHolder 생성
        AdapterFoodBinding binding = AdapterFoodBinding.inflate(LayoutInflater.from(parent.getContext()));
        //context convert ****
        binding.setLifecycleOwner((LifecycleOwner)context);
        binding.setFoodViewModel(foodViewModel);
        binding.setMealSetViewModel(mealSetViewModel);

//        return FoodViewHolder.onCreate(parent);
        return new FoodViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food currentFood = getItem(position);
        //holder.bind(currentFood);

        holder.binding.setFood(currentFood);
        holder.binding.executePendingBindings();

        //테스트 시에 여기다 item view 의 eventListener 생성 이후 databinding 후에 view model 로 mothod 옮겨 작성
    }
}
//public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
//    // ViewModel의 liveData를 출력하기 위한 어댑터
//    private final int LayoutName = R.layout.adapter_food;
//    private List<Food> list = new ArrayList<>();
//
//    public FoodAdapter() {
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    @NonNull
//    @Override
//    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(LayoutName, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setList(List list) {
//        // liveData observe listener 호출될 때 마다 호출되어 adapter의 list를 업데이트 해주는 method
//        this.list = list;
//        // notifyDataSetChanged 메서드는 어댑터를 초기화하고 다시생성하는 것이라 효율이 안좋으므로 추후에 수정 필요
//        notifyDataSetChanged();
//    }
//}

//public static class FoodDiff extends DiffUtil.ItemCallback<Food> {
//
//    @Override
//    public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
//        return oldItem == newItem;
//    }
//
//    @Override
//    public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
//        return oldItem.equals(newItem);
//    }
//}
