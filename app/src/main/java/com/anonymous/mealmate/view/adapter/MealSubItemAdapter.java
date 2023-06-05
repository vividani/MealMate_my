package com.anonymous.mealmate.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.R;
import com.anonymous.mealmate.databinding.AdapterMealsubitemBinding;
import com.anonymous.mealmate.model.entity.Food;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;

import java.util.ArrayList;
import java.util.List;

public class MealSubItemAdapter extends ListAdapter<Food, MealSubItemAdapter.MealSubItemViewHolder> {
    //Todo 생성자를 context만  매개변수로 받기

    private Context context;
    private ViewModelProvider viewModelProvider;
    private MealSetViewModel mealSetViewModel;
    //private LifecycleOwner lifecycleOwner;

    // 에러방지용 초기값
    private Meal meal = null;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public static class MealSubItemViewHolder extends RecyclerView.ViewHolder {


        private AdapterMealsubitemBinding binding;

        public MealSubItemViewHolder(@NonNull AdapterMealsubitemBinding binding) {
            super(binding.getRoot());


            this.binding = binding;
        }


        public void bind(Food food) {
            binding.setFood(food);
        }
    }

//    public MealSubItemAdapter(MealSetViewModel mealSetViewModel, LifecycleOwner lifecycleOwner) {
//        super(new DiffUtil.ItemCallback<Food>() {
//            @Override
//            public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
//                //정확한 비교 수정필요
//                return oldItem == newItem;
//            }
//
//            @Override
//            public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
//                // 정확한 비교 수정필요
//                return false;
//            }
//        });
//        this.mealSetViewModel = mealSetViewModel;
//        this.lifecycleOwner = lifecycleOwner;
//    }

    public MealSubItemAdapter(@NonNull Context context) {
        super(new DiffUtil.ItemCallback<Food>() {
            @Override
            public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                // todo 정확한 비교 수정필요
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                // 정확한 비교 수정필요
                return false;
            }
        });
        this.context = context;
        viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) context);
        mealSetViewModel = viewModelProvider.get(MealSetViewModel.class);
    }

    @NonNull
    @Override
    public MealSubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterMealsubitemBinding binding = AdapterMealsubitemBinding.inflate(layoutInflater, parent, false);
        binding.setLifecycleOwner((LifecycleOwner) context);
        binding.setMealSetViewModel(mealSetViewModel);

//        binding.setLifecycleOwner(lifecycleOwner);
        // 식단정보를 초기값으로 세팅
        binding.setMeal(meal);
        return new MealSubItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealSubItemViewHolder holder, int position) {
        Food currentFood = getItem(position);
        holder.bind(currentFood);
    }


}
//public class MealSubItemAdapter extends RecyclerView.Adapter<MealSubItemAdapter.ViewHolder> {
//    // DB에 업로드 하기위한 메서드
//
//    private final int LayoutName = R.layout.adapter_mealsubitem;
//    public List<Food> list = new ArrayList<>();
//
//
//    public MealSubItemAdapter() {
//        //empty constructor
//    }
//
//    public MealSubItemAdapter(ArrayList<Food> list) {
//        this.list = list;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private Button btnDelete;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            btnDelete = itemView.findViewById(R.id.btn_meal_subItem_delete);
//        }
//
//        public Button getBtnDelete() {
//            return btnDelete;
//        }
//    }
//
//    @NonNull
//    @Override
//    public MealSubItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(LayoutName, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MealSubItemAdapter.ViewHolder holder, int position) {
//        holder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (list.size() != 0) {
//                    list.remove(holder.getAdapterPosition());
//                    notifyDataSetChanged();
//                } else ; // 예외처리 작성
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setList(List list) {
//        this.list = list;
//    }
//}