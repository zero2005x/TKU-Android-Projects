package com.liangtinglin.n913410014_w06.model;

/**
 * RGB 顏色狀態的資料模型
 */
public class ColorState {
    private final int red;
    private final int green;
    private final int blue;

    public ColorState(int red, int green, int blue) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
    }

    /**
     * 確保顏色值在 0-255 範圍內
     */
    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    /**
     * 獲取 Android Color 整數值
     */
    public int getColorInt() {
        return android.graphics.Color.rgb(red, green, blue);
    }

    /**
     * 獲取十六進制顏色字串
     */
    public String getHexString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    /**
     * 判斷是否為有效的顏色值
     */
    public static boolean isValid(int r, int g, int b) {
        return r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255;
    }
}
