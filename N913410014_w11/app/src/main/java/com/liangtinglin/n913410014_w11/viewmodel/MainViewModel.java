package com.liangtinglin.n913410014_w11.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w11.model.FoodItem;
import com.liangtinglin.n913410014_w11.model.OrderResult;
import com.liangtinglin.n913410014_w11.model.TemperatureConversion;
import com.liangtinglin.n913410014_w11.repository.TemperatureRepository;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 主畫面 ViewModel
 * ViewModel 層 - 負責管理 UI 相關數據和業務邏輯
 */
public class MainViewModel extends ViewModel {

    private final TemperatureRepository temperatureRepository;

    // LiveData 用於響應式資料綁定
    private final MutableLiveData<String> resultText = new MutableLiveData<>("轉換結果: ");
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<FoodItem.FoodType, Boolean>> foodVisibility = new MutableLiveData<>();

    // 餐點選擇狀態
    private final Map<FoodItem.FoodType, FoodItem> foodItems = new EnumMap<>(FoodItem.FoodType.class);

    // 溫度轉換模式
    private boolean isCelsiusToFahrenheit = true;

    public MainViewModel() {
        temperatureRepository = new TemperatureRepository();
        initializeFoodItems();
    }

    /**
     * 初始化餐點項目
     */
    private void initializeFoodItems() {
        for (FoodItem.FoodType type : FoodItem.FoodType.values()) {
            foodItems.put(type, new FoodItem(type));
        }

        // 初始化可見性 Map
        Map<FoodItem.FoodType, Boolean> visibility = new EnumMap<>(FoodItem.FoodType.class);
        for (FoodItem.FoodType type : FoodItem.FoodType.values()) {
            visibility.put(type, false);
        }
        foodVisibility.setValue(visibility);
    }

    // ========== Getters for LiveData ==========

    public LiveData<String> getResultText() {
        return resultText;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Map<FoodItem.FoodType, Boolean>> getFoodVisibility() {
        return foodVisibility;
    }

    // ========== 溫度轉換相關方法 ==========

    /**
     * 設定轉換模式
     */
    public void setConversionMode(boolean isCelsiusToFahrenheit) {
        this.isCelsiusToFahrenheit = isCelsiusToFahrenheit;
    }

    /**
     * 執行即時溫度轉換
     */
    public void performConversion(String inputStr) {
        if (inputStr == null || inputStr.trim().isEmpty()) {
            return;
        }

        if (!temperatureRepository.isValidInput(inputStr)) {
            resultText.setValue("輸入格式錯誤");
            return;
        }

        try {
            double inputValue = Double.parseDouble(inputStr);
            TemperatureConversion conversion = temperatureRepository.convertTemperature(inputValue, isCelsiusToFahrenheit);
            resultText.setValue(conversion.getFormattedResult());
        } catch (NumberFormatException e) {
            resultText.setValue("輸入格式錯誤");
        }
    }

    // ========== 餐點訂單相關方法 ==========

    /**
     * 設定餐點選擇狀態
     */
    public void setFoodSelected(FoodItem.FoodType foodType, boolean isSelected) {
        FoodItem item = foodItems.get(foodType);
        if (item != null) {
            item.setSelected(isSelected);
        }
    }

    /**
     * 設定餐點大小
     */
    public void setFoodSize(FoodItem.FoodType foodType, FoodItem.Size size) {
        FoodItem item = foodItems.get(foodType);
        if (item != null) {
            item.setSize(size);
        }
    }

    /**
     * 取得選中的餐點列表
     */
    private List<FoodItem> getSelectedFoodItems() {
        List<FoodItem> selected = new ArrayList<>();
        for (FoodItem item : foodItems.values()) {
            if (item.isSelected()) {
                selected.add(item);
            }
        }
        return selected;
    }

    /**
     * 處理確認按鈕點擊
     */
    public void handleConfirm(String temperatureInput) {
        boolean hasTemperature = temperatureInput != null && !temperatureInput.trim().isEmpty();
        List<FoodItem> selectedItems = getSelectedFoodItems();
        boolean hasFoodOrder = !selectedItems.isEmpty();

        // 驗證輸入
        if (!hasTemperature && !hasFoodOrder) {
            errorMessage.setValue("請輸入溫度或選擇餐點");
            return;
        }

        OrderResult result = new OrderResult();

        // 處理溫度轉換
        if (hasTemperature) {
            if (!temperatureRepository.isValidInput(temperatureInput)) {
                errorMessage.setValue("溫度輸入格式錯誤");
                return;
            }
            double inputValue = Double.parseDouble(temperatureInput);
            TemperatureConversion conversion = temperatureRepository.convertTemperature(inputValue, isCelsiusToFahrenheit);
            result.setTemperatureConversion(conversion);
        }

        // 處理餐點訂單
        if (hasFoodOrder) {
            result.setSelectedFoodItems(selectedItems);
        }

        // 更新結果和可見性
        resultText.setValue(result.getFormattedResult());
        updateFoodVisibility(selectedItems);
    }

    /**
     * 更新餐點圖片可見性
     */
    private void updateFoodVisibility(List<FoodItem> selectedItems) {
        Map<FoodItem.FoodType, Boolean> visibility = new EnumMap<>(FoodItem.FoodType.class);

        for (FoodItem.FoodType type : FoodItem.FoodType.values()) {
            visibility.put(type, false);
        }

        for (FoodItem item : selectedItems) {
            visibility.put(item.getFoodType(), true);
        }

        foodVisibility.setValue(visibility);
    }

    /**
     * 清除所有資料
     */
    public void clearAll() {
        // 重設所有餐點
        for (FoodItem item : foodItems.values()) {
            item.setSelected(false);
            item.setSize(FoodItem.Size.SMALL);
        }

        // 清除結果
        resultText.setValue("轉換結果: ");

        // 隱藏所有圖片
        Map<FoodItem.FoodType, Boolean> visibility = new EnumMap<>(FoodItem.FoodType.class);
        for (FoodItem.FoodType type : FoodItem.FoodType.values()) {
            visibility.put(type, false);
        }
        foodVisibility.setValue(visibility);
    }
}
