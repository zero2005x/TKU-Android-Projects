package com.liangtinglin.n913410014_w11.model;

/**
 * 餐點項目資料模型
 * Model 層 - 負責存儲單個餐點的相關數據
 */
public class FoodItem {
    
    public enum FoodType {
        SOFT_DRINK("汽水", "杯"),
        HAMBURGER("漢堡", "份"),
        FRENCH_FRY("薯條", "份"),
        SOUP("濃湯", "碗"),
        COFFEE("咖啡", "杯");

        private final String displayName;
        private final String unit;

        FoodType(String displayName, String unit) {
            this.displayName = displayName;
            this.unit = unit;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getUnit() {
            return unit;
        }
    }

    public enum Size {
        SMALL("小"),
        MEDIUM("中"),
        LARGE("大");

        private final String displayName;

        Size(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private FoodType foodType;
    private Size size;
    private boolean isSelected;

    public FoodItem(FoodType foodType) {
        this.foodType = foodType;
        this.size = Size.SMALL;
        this.isSelected = false;
    }

    // Getters and Setters
    public FoodType getFoodType() {
        return foodType;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 取得格式化的餐點描述
     */
    public String getFormattedDescription() {
        return size.getDisplayName() + foodType.getUnit() + foodType.getDisplayName();
    }
}
