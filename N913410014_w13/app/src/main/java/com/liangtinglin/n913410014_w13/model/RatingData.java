package com.liangtinglin.n913410014_w13.model;

/**
 * 評分資料模型
 * Model 層 - 負責存儲評分相關數據
 */
public class RatingData {
    private float rating;
    private static final float MIN_RATING = 0f;
    private static final float MAX_RATING = 6f;

    public RatingData() {
        this.rating = 1f;
    }

    public RatingData(float rating) {
        setRating(rating);
    }

    // Getters and Setters
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        // 限制評分範圍
        if (rating < MIN_RATING) {
            this.rating = MIN_RATING;
        } else if (rating > MAX_RATING) {
            this.rating = MAX_RATING;
        } else {
            this.rating = rating;
        }
    }

    public int getRatingAsInt() {
        return (int) rating;
    }

    /**
     * 取得格式化的評分結果
     */
    public String getFormattedResult() {
        return "選" + getRatingAsInt() + "顆星星";
    }

    /**
     * 檢查評分是否在有效範圍內
     */
    public boolean isValid(float value) {
        return value >= MIN_RATING && value <= MAX_RATING;
    }
}
