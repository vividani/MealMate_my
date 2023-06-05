package com.anonymous.mealmate.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.mealmate.R;
import com.anonymous.mealmate.databinding.AdapterMealBinding;
import com.anonymous.mealmate.databinding.AdapterMealitemBinding;
import com.anonymous.mealmate.model.entity.Food;

import com.anonymous.mealmate.model.entity.Meal;
import com.anonymous.mealmate.viewmodel.MealSetViewModel;

import java.util.List;


public class MealItemAdapter extends ListAdapter<Meal, MealItemAdapter.MealItemViewHolder> {
    //Todo 생성자를 context만  매개변수로 받기 -> complete

    private Context context;
    private ViewModelProvider viewModelProvider;
    private MealSetViewModel mealSetViewModel;

    //    private LifecycleOwner lifecycleOwner;

//    public MealItemAdapter(@NonNull DiffUtil.ItemCallback<Meal> diffCallback, MealSetViewModel mealSetViewModel, LifecycleOwner lifecycleOwner) {
//        super(diffCallback);
//        this.mealSetViewModel = mealSetViewModel;
//        this.lifecycleOwner = lifecycleOwner;
//    }

    public MealItemAdapter(@NonNull Context context) {
        super(new DiffUtil.ItemCallback<Meal>() {
            @Override // Todo 정확한 비교로직 작성 필요
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

    public static class MealItemViewHolder extends RecyclerView.ViewHolder {


        // 이후 변수 변경이 없으므로 final 설정
        private final RecyclerView rvMealSubItem;
        private final MealSubItemAdapter mealSubItemAdapter;
        //        private final Button btnMealSubItemAdd;
//        private final Button btnMealItemDelete;
        private AdapterMealitemBinding binding;

        //        public MealItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            // inner adapter base setting : View hold
//            mealSubItemAdapter = new MealSubItemAdapter();
//            rvMealSubItem = itemView.findViewById(R.id.rv_meal_subItem);
//            rvMealSubItem.setAdapter(mealSubItemAdapter);
//            rvMealSubItem.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//
//            btnMealSubItemAdd = itemView.findViewById(R.id.btn_meal_subItem_add);
//            btnMealItemDelete = itemView.findViewById(R.id.btn_meal_item_delete);
//        }
        public MealItemViewHolder(@NonNull AdapterMealitemBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;

            // inner adapter base setting : View hold
            //mealSubItemAdapter = new MealSubItemAdapter(binding.getMealSetViewModel(), binding.getLifecycleOwner());
            mealSubItemAdapter = new MealSubItemAdapter(context);

            //2차원 bind set
//            rvMealSubItem = itemView.findViewById(R.id.rv_meal_subItem);
            rvMealSubItem = binding.rvMealSubItem;
            rvMealSubItem.setAdapter(mealSubItemAdapter);


//            btnMealSubItemAdd = itemView.findViewById(R.id.btn_meal_subItem_add);
//            btnMealItemDelete = itemView.findViewById(R.id.btn_meal_item_delete);
        }

        public void bind(Meal meal) {
            //list 내부 자료형이 Food 가맞는지 확인해주는 검사 메소드 작성필요
            try {
                //mealSubItemAdapter.submitList(meal.getFoods());
                binding.setMeal(meal);
                mealSubItemAdapter.submitList(meal.getFoodList().toList());
                //subitem에 meal 정보 넘겨줌
                mealSubItemAdapter.setMeal(meal);
            } catch (Exception e) {
                Log.d("bind Error", "take care of the value of lists type");
            }
        }

        public AdapterMealitemBinding getBinding() {
            return binding;
        }

        public MealSubItemAdapter getMealSubItemAdapter() {
            return mealSubItemAdapter;
        }
        //        public static MealItemViewHolder onCreate(ViewGroup parent) {
//            //view inflate : just one time , recycle at View holder
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mealitem, parent, false);
//
//
//            return new MealItemViewHolder(view);
//        }
    }


    @NonNull
    @Override
    public MealItemAdapter.MealItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //초기 1회만 실행되서 ViewHolder 세팅시작하는 메서드
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterMealitemBinding binding = AdapterMealitemBinding.inflate(layoutInflater, parent, false);
        binding.setLifecycleOwner((LifecycleOwner) context);
        binding.setMealSetViewModel(mealSetViewModel);

//        binding.setLifecycleOwner(lifecycleOwner);
        //return MealItemViewHolder.onCreate(parent);
        return new MealItemViewHolder(binding, context);
    }


    @Override
    public void onBindViewHolder(@NonNull MealItemAdapter.MealItemViewHolder holder, int position) {
        //item 별 구분되는 기능 list item 개수만큼 호출됨
        Meal currentMeal = getItem(position);
        holder.bind(currentMeal);
        //item의 variable에 postion값을 저장시킴
        holder.binding.setPosition(position);
        holder.getBinding().getMeal().getFoodList().toLivaData().observe((LifecycleOwner) context, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                holder.getMealSubItemAdapter().submitList(foods);
            }
        });
//        holder.btnMealSubItemAdd.setOnClickListener(v -> {
//            //currentList.add(new Food("사과",100,100,100,100));
//            //변경 필요 좀더 효율적인 notify
//            holder.mealSubItemAdapter.submitList(currentMeal.getFoods());
//            //notifyItemRangeInserted(, 1);
//        });
//        holder.btnMealItemDelete.setOnClickListener(v -> {
//            //이후 리스트 변경 안좋은코드 수정필요  임시 삭제이벤트
//            //setMealItems.remove(position);
//
//            //notifyItemRemoved(position);
//        });
    }


}

//    public static class MealItemDiff extends DiffUtil.ItemCallback<Meal> {
//
//        @Override
//        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//            return false;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
//            return false;
//        }
//    }

//public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.ViewHolder> {
//    // 호출 순서 1. 생성자로 데이터 입력받기 2. getItemCount()로 생성할 뷰의 개수 파악 3. onCreateViewHolder()로 뷰를 count 수 만큼 생성
//    public List<List<Food>> list = new ArrayList<>(); // 테스트 list 추후에 변경
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // 이벤트 리스너 할당등 코드를 모아놓음
//        private RecyclerView rv;
//        private Button btnCreate;
//        private Button btnDelete;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            rv = itemView.findViewById(R.id.rv_meal_subItem);
//            rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//            rv.scrollToPosition(((LinearLayoutManager) rv.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
//
//            btnCreate = itemView.findViewById(R.id.btn_meal_subItem_add);
//            btnDelete = itemView.findViewById(R.id.btn_meal_item_delete);
//
//        }
//
//        public RecyclerView getRv() {
//            return rv;
//        }
//
//        public Button getBtnCreate() {
//            return btnCreate;
//        }
//
//        public Button getBtnDelete() {
//            return btnDelete;
//        }
//    }
//
//    public MealItemAdapter() {//constructor 생성자, 데이터 리스트 받음
//
//    }
//
//
//    @NonNull
//    @Override
//    public MealItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mealitem, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MealItemAdapter.ViewHolder holder, int position) {
//        MealSubItemAdapter sfa = new MealSubItemAdapter();
//        holder.getRv().setAdapter(sfa);
//
//        holder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list.remove(holder.getAdapterPosition());
//                notifyDataSetChanged();
//            }
//        });
//
//        holder.getBtnCreate().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //test
//                //sfa.list.add(new DataModel_Food(1,1,"food",100,100,100,300,0));
//                sfa.notifyDataSetChanged();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        // 리사이클러 뷰의 인덱스 개수 만큼 생성받기 위한 리턴
//        return list.size();
//    }
//
//    public void setList(List<List<Food>> list) {
//        this.list = list;
//    }
//}
