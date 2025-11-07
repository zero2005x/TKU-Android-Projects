package com.liangtinglin.n913410014_04.model;

/**
 * BMI 計算器
 * <p>
 * 負責 BMI 計算邏輯和分類判定。
 * 使用靜態方法，無需實例化。
 */
public class BmiCalculator {

    // BMI 分類常數
    public static final double BMI_UNDERWEIGHT = 18.5;
    public static final double BMI_NORMAL = 24.0;
    public static final double BMI_OVERWEIGHT = 27.0;
    public static final double BMI_MILD_OBESITY = 30.0;
    public static final double BMI_MODERATE_OBESITY = 35.0;

    /**
     * 計算 BMI
     *
     * @param heightCm 身高（公分）
     * @param weightKg 體重（公斤）
     * @return BMI 值
     * @throws IllegalArgumentException 如果輸入值無效
     */
    public static double calculate(double heightCm, double weightKg) {
        if (heightCm <= 0 || weightKg <= 0) {
            throw new IllegalArgumentException("身高和體重必須大於零");
        }

        // 將身高從公分轉換為公尺
        double heightM = heightCm / 100.0;

        // 計算 BMI
        return weightKg / (heightM * heightM);
    }

    /**
     * 根據 BMI 值判定分類
     *
     * @param bmi BMI 值
     * @return 分類字串
     */
    public static String classify(double bmi) {
        if (bmi < BMI_UNDERWEIGHT) {
            return "體重過輕";
        } else if (bmi < BMI_NORMAL) {
            return "正常範圍";
        } else if (bmi < BMI_OVERWEIGHT) {
            return "過重";
        } else if (bmi < BMI_MILD_OBESITY) {
            return "輕度肥胖";
        } else if (bmi < BMI_MODERATE_OBESITY) {
            return "中度肥胖";
        } else {
            return "重度肥胖";
        }
    }

    // 私有建構子，防止實例化
    private BmiCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
