package com.liangtinglin.n913410014_04.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_04.model.BmiCalculator;
import com.liangtinglin.n913410014_04.model.BmiResult;

/**
 * BMI 計算 ViewModel
 */
public class BmiViewModel extends ViewModel {

    private final MutableLiveData<BmiResult> bmiResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<BmiResult> getBmiResult() {
        return bmiResult;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 計算 BMI
     */
    public void calculateBmi(String heightStr, String weightStr) {
        // 驗證輸入
        if (heightStr == null || heightStr.trim().isEmpty() ||
            weightStr == null || weightStr.trim().isEmpty()) {
            errorMessage.setValue("請輸入身高和體重");
            return;
        }

        try {
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            // 計算 BMI
            double bmiValue = BmiCalculator.calculate(height, weight);
            String category = BmiCalculator.classify(bmiValue);

            // 更新結果
            bmiResult.setValue(new BmiResult(bmiValue, category));

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入有效的數字");
        } catch (IllegalArgumentException e) {
            errorMessage.setValue(e.getMessage());
        }
    }
}
