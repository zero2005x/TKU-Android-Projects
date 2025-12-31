package com.liangtinglin.n913410014_w12.model;

/**
 * 餐點項目資料模型
 * Model 層 - 負責存儲單個餐點的相關數據
 */
public class MenuItem {

    public enum MenuType {
        COFFEE(0, "咖啡"),
        HAMBURGER(1, "漢堡"),
        FRENCH_FRY(2, "薯條"),
        SOFT_DRINK(3, "汽水");

        private final int index;
        private final String displayName;

        MenuType(int index, String displayName) {
            this.index = index;
            this.displayName = displayName;
        }

        public int getIndex() {
            return index;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Size {
        LARGE(0, "大號"),
        MEDIUM(1, "中號"),
        SMALL(2, "小號");

        private final int index;
        private final String displayName;

        Size(int index, String displayName) {
            this.index = index;
            this.displayName = displayName;
        }

        public int getIndex() {
            return index;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private MenuType menuType;
    private Size size;
    private boolean isSelected;
    private int price;

    // 價格表：[餐點類型][大小] = {咖啡, 漢堡, 薯條, 汽水} x {大, 中, 小}
    private static final int[][] PRICES = {
            {55, 45, 65}, // 咖啡
            {85, 75, 65}, // 漢堡
            {55, 45, 35}, // 薯條
            {60, 50, 40}  // 汽水
    };

    public MenuItem(MenuType menuType) {
        this.menuType = menuType;
        this.size = Size.LARGE;
        this.isSelected = false;
        updatePrice();
    }

    // Getters and Setters
    public MenuType getMenuType() {
        return menuType;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
        updatePrice();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPrice() {
        return price;
    }

    /**
     * 根據餐點類型和大小更新價格
     */
    private void updatePrice() {
        this.price = PRICES[menuType.getIndex()][size.getIndex()];
    }

    /**
     * 取得格式化的餐點描述
     */
    public String getFormattedDescription() {
        return menuType.getDisplayName() + size.getDisplayName();
    }

    /**
     * 靜態方法：根據索引取得價格
     */
    public static int getPriceByIndex(int menuIndex, int sizeIndex) {
        if (menuIndex >= 0 && menuIndex < PRICES.length &&
            sizeIndex >= 0 && sizeIndex < PRICES[menuIndex].length) {
            return PRICES[menuIndex][sizeIndex];
        }
        return 0;
    }
}
