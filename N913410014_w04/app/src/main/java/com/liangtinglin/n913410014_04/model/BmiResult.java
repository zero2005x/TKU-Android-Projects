package com.liangtinglin.n913410014_04.model;

/**
 * BMI 計算結果模型
 */
public class BmiResult {
    private final double bmiValue;
    private final String category;

    public BmiResult(double bmiValue, String category) {
        this.bmiValue = bmiValue;
        this.category = category;
    }

    public double getBmiValue() {
        return bmiValue;
    }

    public String getCategory() {
        return category;
    }

    /**
     * 格式化顯示結果
     */
    public String format() {
        return String.format("您的 BMI 是 %.2f\n結果為：%s", bmiValue, category);
    }
}
