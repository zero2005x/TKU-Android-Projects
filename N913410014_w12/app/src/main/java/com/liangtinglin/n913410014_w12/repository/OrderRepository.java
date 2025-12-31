package com.liangtinglin.n913410014_w12.repository;

import com.liangtinglin.n913410014_w12.model.MenuItem;
import com.liangtinglin.n913410014_w12.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * 訂單資料倉庫
 * Repository 層 - 負責訂單相關的業務邏輯
 */
public class OrderRepository {

    /**
     * 計算選中餐點的總金額
     * @param selectedItems 選中的餐點列表
     * @return 總金額
     */
    public int calculateTotalPrice(List<MenuItem> selectedItems) {
        int total = 0;
        for (MenuItem item : selectedItems) {
            total += item.getPrice();
        }
        return total;
    }

    /**
     * 建立訂單
     * @param selectedItems 選中的餐點列表
     * @return 訂單物件
     */
    public Order createOrder(List<MenuItem> selectedItems) {
        Order order = new Order();
        order.setItems(selectedItems);
        return order;
    }

    /**
     * 根據索引計算單項價格
     * @param menuIndex 餐點索引
     * @param sizeIndex 大小索引
     * @return 價格
     */
    public int getItemPrice(int menuIndex, int sizeIndex) {
        return MenuItem.getPriceByIndex(menuIndex, sizeIndex);
    }
}
