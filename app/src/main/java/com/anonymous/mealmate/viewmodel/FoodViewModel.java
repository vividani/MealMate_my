package com.anonymous.mealmate.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anonymous.mealmate.api.FoodApiHelper;
import com.anonymous.mealmate.feature.ControlViewState;
import com.anonymous.mealmate.model.entity.Food;
import com.anonymous.mealmate.model.repository.FoodApiRepository;
import com.anonymous.mealmate.model.repository.FoodRepository;

import java.util.List;

import retrofit2.Call;

public class FoodViewModel extends AndroidViewModel {
    private final FoodApiRepository foodApiRepository;

    private final LiveData<List<Food>> searchResults;   // 검색 결과를 나타내는 LiveData 추가

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final FoodRepository foodRepository;
    private final LiveData<List<Food>> allFoods;
    //private final MutableLiveData<Food> clickedFood = new MutableLiveData<>();
    private final MutableLiveData<Food> selectedFood = new MutableLiveData<>(); // 선택된 음식을 나타내는 LiveData 추가
    private final MutableLiveData<Food> foodLiveData = new MutableLiveData<>(); // 특정 음식의 좋아요 여부를 나타내는 LiveData 추가

    public FoodViewModel(Application application) {
        super(application);
        foodRepository = FoodRepository.getInstance(application);
        foodApiRepository = FoodApiRepository.getInstance(application);

        searchResults = foodApiRepository.getSearchResults();

        //repository = new FoodRepository(application);
        // 모든 음식 정보를 가져옴.
        allFoods = foodRepository.getAllFoods();
    }

    //TODO : FoodApi
    public Call<FoodApiHelper.ResponseClass> getFoodNtrItdntList(String foodName) { // 사용자가 특정 음식 이름을 입력하면 그 음식의 영양 정보를 반환함.
        return foodApiRepository.getFoodNtrItdntList(foodName);
    }

    /*// 사용자가 음식 이름을 입력하면 그와 관련된 모든 음식을 검색 결과로 반환함.
    public LiveData<List<Food>> onSearchFood(String foodName) {
        return foodApiRepository.searchFood(foodName);
    }*/


    //xml binding method, do not erase
    //binding Text complete, enable to user
    public void onSearchFood(View view, String inputFoodName) {
        //Food Name 전달 받아 repository에 데이터 요청하는 메소드
        //db search logic

        //foodRepository.searchFood(foodName);

        //api repository 검색,


        //TODO 레파지토리에 food 검색 기능 연결 -> complete
        //TODO 이후 조건 받아 foodapi에서 받게하기
        LiveData<List<Food>> searchResults = foodApiRepository.searchFood(inputFoodName);


        //test

    }


    // 검색결과와 에러메시지를 가져옴.
    public LiveData<List<Food>> getSearchResults() {
        return searchResults;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    //TODO : Food
    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    // DB에 넣을 때 같은 음식이 있는지 확인하고, 없으면 삽입.
    public void insert(Food food) {
        new Thread(() -> {
            try {
                Food existingFood = foodRepository.getFoodByNameSync(food.getFoodName());
                if (existingFood == null) {
                    foodRepository.insertFood(food);
                }
            } catch (Exception e) {
                // 예외를 로그에 기록하거나 사용자에게 오류 메시지를 표시합니다.
            }
        }).start();
    }


    public void update(Food food) {
        foodRepository.updateFood(food);
    }

    public void delete(Food food) {
        foodRepository.deleteFood(food);
    }

    // 특정 음식의 좋아요 여부를 가져옵니다.
    public LiveData<List<Food>> getLikedFoods() {
        return foodRepository.getUserLikedFoods();
    }


    // 특정 음식의 좋아요 여부를 업데이트합니다.
    public void setClickedFood(Food food) {
        foodLiveData.setValue(food);
    }


    public void selectFood(Food food) {
        selectedFood.setValue(food);
    } // 선택된 음식을 설정하는 메소드 추가


    public LiveData<Food> getSelectedFood() {
        return selectedFood;
    } // 선택된 음식을 가져오는 메소드 추가

    //xml binding method, do not erase
    //binding Text complete, enable to use

    //xml binding method, do not erase
    public void onLikeStateChange(Food food) {
        //Food Adapter에 하트 버튼을 눌렀을때 Food db를 업데이트 해주는 메서드
        if (food.getFoodLike() == Food.FOOD_NOT_LIKE)
            food.setFoodLike(Food.FOOD_LIKE);
        else if (food.getFoodLike() == Food.FOOD_LIKE) {
            food.setFoodLike(Food.FOOD_NOT_LIKE);
        }
        //db upload
        //foodRepository.updateFood(food);

        //test
    }

    public void onDeleteFood(Food food) {
        foodRepository.deleteFood(food);
    }

    public void onAddFood() {
        ControlViewState.getInstance().activeIntentSignal(ControlViewState.ACTIVITY_FINISH);
        Toast.makeText(getApplication().getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
    }
}
