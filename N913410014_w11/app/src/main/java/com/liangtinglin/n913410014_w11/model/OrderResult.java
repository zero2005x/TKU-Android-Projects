package com.liangtinglin.n913410014_w11.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 訂單結果資料模型
 * Model 層 - 負責組合溫度轉換和餐點訂單結果
 */
public class OrderResult {
    private TemperatureConversion temperatureConversion;
    private List<FoodItem> selectedFoodItems;
    private String errorMessage;
    private boolean hasError;

    public OrderResult() {
        this.selectedFoodItems = new ArrayList<>();
        this.hasError = false;
    }

    // Getters and Setters
    public TemperatureConversion getTemperatureConversion() {
        return temperatureConversion;
    }

    public void setTemperatureConversion(TemperatureConversion temperatureConversion) {
        this.temperatureConversion = temperatureConversion;
    }

    public List<FoodItem> getSelectedFoodItems() {
        return selectedFoodItems;
    }

    public void setSelectedFoodItems(List<FoodItem> selectedFoodItems) {
        this.selectedFoodItems = selectedFoodItems;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.hasError = (errorMessage != null && !errorMessage.isEmpty());
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean hasTemperature() {
        return temperatureConversion != null;
    }

    public boolean hasFoodOrder() {
        return selectedFoodItems != null && !selectedFoodItems.isEmpty();
    }

    /**
     * 取得完整的結果字串
     */
    public String getFormattedResult() {
        StringBuilder result = new StringBuilder();

        if (hasTemperature()) {
            result.append(temperatureConversion.getFormattedResult());
        }

        if (hasFoodOrder()) {
            if (hasTemperature()) {
                result.append("\n\n");
            }
            result.append("已點餐點: ");
            for (int i = 0; i < selectedFoodItems.size(); i++) {
                result.append(selectedFoodItems.get(i).getFormattedDescription());
                if (i < selectedFoodItems.size() - 1) {
                    result.append("、");
                }
            }
        }

        return result.toString();
    }
}
