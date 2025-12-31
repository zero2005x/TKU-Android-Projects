package com.liangtinglin.n913410014_w12.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 訂單資料模型
 * Model 層 - 負責管理訂單的完整資訊
 */
public class Order {
    private List<MenuItem> items;
    private int totalPrice;

    public Order() {
        this.items = new ArrayList<>();
        this.totalPrice = 0;
    }

    /**
     * 添加餐點到訂單
     */
    public void addItem(MenuItem item) {
        items.add(item);
        calculateTotal();
    }

    /**
     * 清除訂單
     */
    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    /**
     * 計算總金額
     */
    private void calculateTotal() {
        totalPrice = 0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
    }

    /**
     * 設定訂單項目
     */
    public void setItems(List<MenuItem> items) {
        this.items = new ArrayList<>(items);
        calculateTotal();
    }

    // Getters
    public List<MenuItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * 取得格式化的訂單結果
     */
    public String getFormattedResult() {
        if (isEmpty()) {
            return "未點餐";
        }

        StringBuilder sb = new StringBuilder("結果 : ");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getFormattedDescription());
            if (i < items.size() - 1) {
                sb.append("、");
            }
        }
        sb.append("\n總金額 : ").append(totalPrice).append(" 元");

        return sb.toString();
    }
}
