package com.liangtinglin.n913410014_w05.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w05.model.BmiResult;

/**
 * MainViewModel - 處理 BMI 計算和按鈕點擊的業務邏輯
 * 使用 LiveData 來實現 View 和 ViewModel 之間的數據綁定
 */
public class MainViewModel extends ViewModel {
    
    // LiveData for BMI calculation result
    private final MutableLiveData<BmiResult> bmiResult = new MutableLiveData<>();
    
    // LiveData for button click messages
    private final MutableLiveData<String> buttonClickMessage = new MutableLiveData<>();
    
    // LiveData for error messages (Toast)
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<BmiResult> getBmiResult() {
        return bmiResult;
    }

    public LiveData<String> getButtonClickMessage() {
        return buttonClickMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 計算 BMI
     * @param heightCm 身高（公分）
     * @param weightKg 體重（公斤）
     */
    public void calculateBmi(String heightCm, String weightKg) {
        // 驗證輸入
        if (heightCm == null || heightCm.isEmpty() || weightKg == null || weightKg.isEmpty()) {
            errorMessage.setValue("請輸入身高和體重");
            return;
        }

        try {
            double height = Double.parseDouble(heightCm) / 100.0; // 轉換為公尺
            double weight = Double.parseDouble(weightKg);

            // 驗證身高不為零
            if (height == 0) {
                errorMessage.setValue("身高不能為零");
                return;
            }

            // 計算 BMI
            double bmi = weight / (height * height);
            
            // 判斷健康狀態
            String status = getBmiStatus(bmi);
            
            // 更新結果
            bmiResult.setValue(new BmiResult(bmi, status));

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入有效的數字");
        }
    }

    /**
     * 根據 BMI 值判斷健康狀態
     */
    private String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "體重過輕";
        } else if (bmi < 24) {
            return "正常範圍";
        } else if (bmi < 27) {
            return "過重";
        } else if (bmi < 30) {
            return "輕度肥胖";
        } else if (bmi < 35) {
            return "中度肥胖";
        } else {
            return "重度肥胖";
        }
    }

    /**
     * 處理靜態按鈕點擊
     * @param buttonText 按鈕文字
     */
    public void onStaticButtonClicked(String buttonText) {
        buttonClickMessage.setValue("按下 " + buttonText);
    }

    /**
     * 處理動態按鈕點擊
     * @param buttonText 按鈕文字
     */
    public void onDynamicButtonClicked(String buttonText) {
        buttonClickMessage.setValue("按下 " + buttonText);
    }
}
