package com.liangtinglin.n913410014_w06.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w06.model.ColorState;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MainViewModel - 處理顏色選擇和按鈕點擊的業務邏輯
 */
public class MainViewModel extends ViewModel {

    // LiveData for color state
    private final MutableLiveData<ColorState> colorState = new MutableLiveData<>();

    // LiveData for button click messages
    private final MutableLiveData<String> buttonClickMessage = new MutableLiveData<>();

    // LiveData for error messages
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<ColorState> getColorState() {
        return colorState;
    }

    public LiveData<String> getButtonClickMessage() {
        return buttonClickMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 生成隨機顏色
     */
    public void generateRandomColor() {
        int r = ThreadLocalRandom.current().nextInt(256);
        int g = ThreadLocalRandom.current().nextInt(256);
        int b = ThreadLocalRandom.current().nextInt(256);
        
        colorState.setValue(new ColorState(r, g, b));
    }

    /**
     * 手動設定顏色
     * @param redStr 紅色值字串
     * @param greenStr 綠色值字串
     * @param blueStr 藍色值字串
     */
    public void setManualColor(String redStr, String greenStr, String blueStr) {
        // 驗證輸入
        if (redStr == null || redStr.isEmpty() || 
            greenStr == null || greenStr.isEmpty() || 
            blueStr == null || blueStr.isEmpty()) {
            errorMessage.setValue("請輸入完整的 RGB 值");
            return;
        }

        try {
            int r = Integer.parseInt(redStr);
            int g = Integer.parseInt(greenStr);
            int b = Integer.parseInt(blueStr);

            // 驗證範圍
            if (!ColorState.isValid(r, g, b)) {
                errorMessage.setValue("RGB 值必須在 0-255 之間");
                return;
            }

            colorState.setValue(new ColorState(r, g, b));

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入有效的數字");
        }
    }

    /**
     * 處理動態按鈕點擊
     * @param buttonText 按鈕文字
     */
    public void onDynamicButtonClicked(String buttonText) {
        buttonClickMessage.setValue(buttonText);
    }
}
