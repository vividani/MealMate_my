package com.anonymous.mealmate.feature;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ControlViewState {

    //
    /**
     * 트리거 클래스
     * 뷰의 이동을 제어한다. activity, fragment에서 데이터 핸들링을 하지 않기 때문에 단순 인텐트로 어디서 든지 화면 전환 가능 하도록 설계 할 수있다. ->  MVVM 의 최대장점 -> 복잡한 이벤트 구현이 쉽다.
     *
     * liveData 인 stateSignalLive 데이터를 액티비티에서 observe 한다음 상수값으로 선언된 signal에따라 switch 문으로 기능을 작동하도록 설계
     */

    private static ControlViewState instance = null;
    private MutableLiveData<Integer> stateSignalLiveData = new MutableLiveData<>();

    public LiveData<Integer> getStateSignalLiveData() {
        return stateSignalLiveData;
    }

    public ControlViewState() {
        stateSignalLiveData.postValue(NONE);
    }

    public static ControlViewState getInstance() {
        if (instance == null) {
            synchronized (ControlViewState.class) {
                if (instance == null)
                    instance = new ControlViewState();
            }
        }
        return instance;
    }


    // 메소드 이름으로 동작 구분을 위해 같은 기능을 하는 메소드 여러개 생성
    public void activeIntentSignal(Integer signal){
        //Todo if문 범위값 지정해서 Intent signal 인지 확인
        //active observer signal
        stateSignalLiveData.setValue(signal);
    }



    public void activeDialogSignal(Integer signal){
        //Todo if문 범위 값 지정해서 다이아로그 만의 신호 범위 인지 확인
        stateSignalLiveData.setValue(signal);
    }

    public void activeActivityLifeCycleSignal(Integer signal){
        stateSignalLiveData.setValue(signal);
    }



    //activity lifeCycle signal
    public static final int ACTIVITY_FINISH =  1;

    public static final int ACTIVITY_RESTART = 2;


    //intent signal
    public static final int NONE = -1;

    public static final int INTENT_MAIN_TO_SETMEAL = 11;
    public static final int INTENT_MAIN_TO_USERUPDATEDATA = 12;

    public static final int INTENT_SETMEAL_TO_FOOD = 13;


    // Dialog create signal

    public static final int DIALOG_FOOD_DATASET = 21;

    public static final int ADAPTER_DATA_CHANGED =10;


}
