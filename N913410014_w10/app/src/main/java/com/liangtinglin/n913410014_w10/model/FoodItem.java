package com.liangtinglin.n913410014_w10.model;

/**
 * 食物項目資料模型
 * <p>
 * 代表一個食物項目，包含圖片資源 ID 和名稱。
 */
public class FoodItem {
    private final int imageResId;
    private final String name;
    private final int imageViewId;

    /**
     * 建構子
     *
     * @param imageResId  圖片資源 ID
     * @param name        食物名稱
     * @param imageViewId ImageView 的 ID
     */
    public FoodItem(int imageResId, String name, int imageViewId) {
        this.imageResId = imageResId;
        this.name = name;
        this.imageViewId = imageViewId;
    }

    /**
     * 取得圖片資源 ID
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * 取得食物名稱
     */
    public String getName() {
        return name;
    }

    /**
     * 取得 ImageView 的 ID
     */
    public int getImageViewId() {
        return imageViewId;
    }
}
