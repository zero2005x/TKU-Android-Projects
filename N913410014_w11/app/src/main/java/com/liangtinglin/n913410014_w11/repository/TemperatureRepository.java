package com.liangtinglin.n913410014_w11.repository;

import com.liangtinglin.n913410014_w11.model.TemperatureConversion;

/**
 * 溫度轉換資料倉庫
 * Repository 層 - 負責溫度轉換的業務邏輯
 */
public class TemperatureRepository {

    /**
     * 執行溫度轉換
     * @param inputValue 輸入的溫度值
     * @param isCelsiusToFahrenheit 是否為攝氏轉華氏
     * @return 溫度轉換結果
     */
    public TemperatureConversion convertTemperature(double inputValue, boolean isCelsiusToFahrenheit) {
        double celsius, fahrenheit;

        if (isCelsiusToFahrenheit) {
            celsius = inputValue;
            fahrenheit = (celsius * 9.0 / 5.0) + 32;
        } else {
            fahrenheit = inputValue;
            celsius = (fahrenheit - 32) * 5.0 / 9.0;
        }

        return new TemperatureConversion(celsius, fahrenheit, isCelsiusToFahrenheit);
    }

    /**
     * 驗證輸入值是否為有效數字
     * @param input 輸入字串
     * @return 是否為有效數字
     */
    public boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
