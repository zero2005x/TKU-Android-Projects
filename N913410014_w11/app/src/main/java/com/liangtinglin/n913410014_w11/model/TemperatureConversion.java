package com.liangtinglin.n913410014_w11.model;

/**
 * 溫度轉換結果資料模型
 * Model 層 - 負責存儲溫度轉換的相關數據
 */
public class TemperatureConversion {
    private double celsius;
    private double fahrenheit;
    private boolean isCelsiusToFahrenheit;

    public TemperatureConversion() {
        this.celsius = 0;
        this.fahrenheit = 32;
        this.isCelsiusToFahrenheit = true;
    }

    public TemperatureConversion(double celsius, double fahrenheit, boolean isCelsiusToFahrenheit) {
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.isCelsiusToFahrenheit = isCelsiusToFahrenheit;
    }

    // Getters and Setters
    public double getCelsius() {
        return celsius;
    }

    public void setCelsius(double celsius) {
        this.celsius = celsius;
    }

    public double getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    public boolean isCelsiusToFahrenheit() {
        return isCelsiusToFahrenheit;
    }

    public void setCelsiusToFahrenheit(boolean celsiusToFahrenheit) {
        isCelsiusToFahrenheit = celsiusToFahrenheit;
    }

    /**
     * 格式化溫度轉換結果
     */
    public String getFormattedResult() {
        return String.format("轉換結果: %.2f°C，相當於 %.2f°F", celsius, fahrenheit);
    }
}
