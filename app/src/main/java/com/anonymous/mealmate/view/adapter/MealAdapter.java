package com.anonymous.mealmate.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.R;
import com.anonymous.mealmate.databinding.AdapterMealBinding;

import com.anonymous.mealmate.model.entity.Food;
import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.viewmodel.MealCheckViewModel;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;


public class MealAdapter extends ListAdapter<Meal, MealAdapter.MealViewHolder> {

    //Todo 생성자를 context만  매개변수로 받기 - > complete
    private LifecycleOwner lifecycleOwner;
    private MealCheckViewModel mealCheckViewModel;

    private Context context;
    private ViewModelProvider viewModelProvider;

    public static class MealViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvMealInfo;
//        private LinearLayout llMealMenu;
//        private Button btnMealCheck;

        private AdapterMealBinding binding;

        //not user
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            // not use
//            tvMealInfo = itemView.findViewById(R.id.tv_meal_info);
//            llMealMenu = itemView.findViewById(R.id.ll_meal_menu);
//            btnMealCheck = itemView.findViewById(R.id.btn_meal_check);
        }

        public MealViewHolder(@NonNull AdapterMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
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

        private AdapterMealBinding getBinding() {
            return binding;
        }

//        public static MealViewHolder onCreate(ViewGroup parent){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_meal, parent,false);
//            return new MealViewHolder(view);
//        }
    }
//
//    public MealAdapter(@NonNull DiffUtil.ItemCallback<Meal> diffCallback, MealCheckViewModel mealCheckViewModel, LifecycleOwner lifecycleOwner) {
//        super(diffCallback);
//        this.mealCheckViewModel = mealCheckViewModel;
//        this.lifecycleOwner = lifecycleOwner;
//    }

    //context 사용한 adapter
    public MealAdapter(@NonNull Context context) {
        super(new DiffUtil.ItemCallback<Meal>() { // callback method
            @Override // 이후 정확한 아이템 비교로직 작성 필요
            public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return false;
            }
        });
        //context setting
        this.context = context;

        // viewModel contact
        viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) context);
        mealCheckViewModel = viewModelProvider.get(MealCheckViewModel.class);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterMealBinding binding = AdapterMealBinding.inflate(inflater);
        binding.setLifecycleOwner((LifecycleOwner) context);
        binding.setMealCheckViewModel(mealCheckViewModel);

//        binding.setLifecycleOwner(lifecycleOwner);
//        binding.setFoods(new Foods());
//        return MealViewHolder.onCreate(parent);
        return new MealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal currentMeal = getItem(position);
        holder.bind(currentMeal);
//        holder.btnMealCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //notifyItemRemoved(position);
//            }
//        });
    }
}


//public static class MealDiff extends DiffUtil.ItemCallback<Meal> {
//    @Override
//    public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//        return oldItem == newItem;
//    }
//
//    @Override
//    public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//        return oldItem.getMealIndex() == newItem.getMealIndex();
//    }
//}
//    public Meal getItemTest(int position){
//        return getItem(position);
//    }
//public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
//    // ViewModel의 liveData를 출력하기 위한 어댑터
//    private final int layoutName = R.layout.adapter_meal;
//
//
//    private OnItemClickListener listener;
//
//    private List<Meal> list = new ArrayList<>();
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        Button btnCheck;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            btnCheck = itemView.findViewById(R.id.btn_meal_check);
//
//            //check event
//            btnCheck.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
////                    if (listener != null && position = RecyclerView.NO_POSITION) {
////                        listener.onItemClick(list.get(position));
////                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(Meal meal);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//
//    @NonNull
//    @Override
//    public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(layoutName, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
//        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setList(List<Meal> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
//}
