package com.liangtinglin.n913410014_w05.model;

/**
 * BMI 計算結果的資料模型
 * 包含 BMI 數值和對應的健康狀態描述
 */
public class BmiResult {
    private final double bmiValue;
    private final String status;
    private final boolean isError;
    private final String errorMessage;

    // 正常結果的建構子
    public BmiResult(double bmiValue, String status) {
        this.bmiValue = bmiValue;
        this.status = status;
        this.isError = false;
        this.errorMessage = null;
    }

    // 錯誤結果的建構子
    public BmiResult(String errorMessage) {
        this.bmiValue = 0;
        this.status = "";
        this.isError = true;
        this.errorMessage = errorMessage;
    }

    public double getBmiValue() {
        return bmiValue;
    }

    public String getStatus() {
        return status;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 格式化顯示 BMI 結果
     */
    public String getFormattedResult() {
        if (isError) {
            return errorMessage;
        }
        return String.format("BMI: %.2f - %s", bmiValue, status);
    }
}
