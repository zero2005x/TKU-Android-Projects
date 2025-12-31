package com.liangtinglin.n913410014_w14.model;

import android.graphics.Color;

/**
 * RGB 顏色資料模型
 * Model 層 - 負責存儲 RGB 顏色相關數據
 */
public class RgbColor {
    private int red;
    private int green;
    private int blue;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    public RgbColor() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public RgbColor(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    // Getters and Setters
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = clampValue(red);
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = clampValue(green);
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = clampValue(blue);
    }

    /**
     * 限制數值在有效範圍內
     */
    private int clampValue(int value) {
        if (value < MIN_VALUE) return MIN_VALUE;
        if (value > MAX_VALUE) return MAX_VALUE;
        return value;
    }

    /**
     * 取得 Android Color 物件
     */
    public int getColor() {
        return Color.rgb(red, green, blue);
    }

    /**
     * 取得格式化的顏色結果
     */
    public String getFormattedResult() {
        return "紅色:" + red + "\n綠色:" + green + "\n藍色:" + blue;
    }

    /**
     * 生成隨機顏色
     */
    public static RgbColor random() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new RgbColor(r, g, b);
    }

    /**
     * 檢查輸入值是否有效
     */
    public static boolean isValidValue(int value) {
        return value >= MIN_VALUE && value <= MAX_VALUE;
    }

    /**
     * 檢查所有值是否在有效範圍
     */
    public static boolean isValidRange(int r, int g, int b) {
        return isValidValue(r) && isValidValue(g) && isValidValue(b);
    }
}
