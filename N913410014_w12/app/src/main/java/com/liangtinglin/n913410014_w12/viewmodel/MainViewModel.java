package com.liangtinglin.n913410014_w12.viewmodel;

import android.widget.CompoundButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w12.model.DragEvent;
import com.liangtinglin.n913410014_w12.model.MenuItem;
import com.liangtinglin.n913410014_w12.model.Order;
import com.liangtinglin.n913410014_w12.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主畫面 ViewModel
 * ViewModel 層 - 負責管理 UI 相關數據和業務邏輯
 */
public class MainViewModel extends ViewModel {

    private final OrderRepository orderRepository;

    // LiveData
    private final MutableLiveData<String> resultText = new MutableLiveData<>("結果 : ");
    private final MutableLiveData<Map<Integer, Boolean>> imageVisibility = new MutableLiveData<>();
    private final MutableLiveData<DragEvent> dragEventInfo = new MutableLiveData<>();

    // 選中的餐點
    private final List<CompoundButton> selectedButtons = new ArrayList<>();

    // 餐點大小索引 (0=大, 1=中, 2=小)
    private final int[] sizeIndices = {0, 0, 0, 0}; // 預設都是大

    // 價格表
    private static final int[][] PRICES = {
            {55, 45, 65}, // 咖啡
            {85, 75, 65}, // 漢堡
            {55, 45, 35}, // 薯條
            {60, 50, 40}  // 汽水
    };

    public MainViewModel() {
        orderRepository = new OrderRepository();
        initImageVisibility();
    }

    private void initImageVisibility() {
        Map<Integer, Boolean> visibility = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            visibility.put(i, false);
        }
        imageVisibility.setValue(visibility);
    }

    // ========== Getters for LiveData ==========

    public LiveData<String> getResultText() {
        return resultText;
    }

    public LiveData<Map<Integer, Boolean>> getImageVisibility() {
        return imageVisibility;
    }

    public LiveData<DragEvent> getDragEventInfo() {
        return dragEventInfo;
    }

    // ========== 餐點選擇相關方法 ==========

    /**
     * 處理 CheckBox 選擇變更
     */
    public void onItemCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!selectedButtons.contains(buttonView)) {
                selectedButtons.add(buttonView);
            }
        } else {
            selectedButtons.remove(buttonView);
        }
    }

    /**
     * 設定餐點大小
     * @param menuIndex 餐點索引 (0-3)
     * @param sizeIndex 大小索引 (0=大, 1=中, 2=小)
     */
    public void setItemSize(int menuIndex, int sizeIndex) {
        if (menuIndex >= 0 && menuIndex < sizeIndices.length) {
            sizeIndices[menuIndex] = sizeIndex;
        }
    }

    /**
     * 取得選中的按鈕列表
     */
    public List<CompoundButton> getSelectedButtons() {
        return new ArrayList<>(selectedButtons);
    }

    // ========== 確認訂單相關方法 ==========

    /**
     * 確認訂單
     * @param checkBoxIds 所有 CheckBox 的 ID 陣列
     * @param getButtonId 用於取得按鈕 ID 的函數介面
     */
    public void confirmOrder(int[] checkBoxIds, ButtonIdGetter getButtonId) {
        // 先隱藏所有圖片
        Map<Integer, Boolean> visibility = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            visibility.put(i, false);
        }

        if (selectedButtons.isEmpty()) {
            resultText.setValue("未點餐");
            imageVisibility.setValue(visibility);
            return;
        }

        StringBuilder sb = new StringBuilder("結果 : ");
        int totalPrice = 0;

        for (int i = 0; i < selectedButtons.size(); i++) {
            CompoundButton button = selectedButtons.get(i);
            String foodName = button.getText().toString();
            String size = "";
            int currentId = button.getId();

            // 找出這個 CheckBox 在陣列中的索引位置
            for (int j = 0; j < checkBoxIds.length; j++) {
                if (checkBoxIds[j] == currentId) {
                    int sizeIndex = sizeIndices[j];
                    
                    switch (sizeIndex) {
                        case 0:
                            size = "大號";
                            break;
                        case 1:
                            size = "中號";
                            break;
                        case 2:
                            size = "小號";
                            break;
                    }
                    
                    totalPrice += PRICES[j][sizeIndex];
                    visibility.put(j, true);
                    break;
                }
            }

            String itemString = foodName + size;
            if (i == 0) {
                sb.append(itemString);
            } else {
                sb.append("、").append(itemString);
            }
        }

        sb.append("\n總金額 : ").append(totalPrice).append(" 元");
        resultText.setValue(sb.toString());
        imageVisibility.setValue(visibility);
    }

    // ========== 清除相關方法 ==========

    /**
     * 清除所有資料
     */
    public void clearAll() {
        selectedButtons.clear();
        
        // 重設大小為大
        for (int i = 0; i < sizeIndices.length; i++) {
            sizeIndices[i] = 0;
        }

        resultText.setValue("結果 : ");

        // 隱藏所有圖片
        Map<Integer, Boolean> visibility = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            visibility.put(i, false);
        }
        imageVisibility.setValue(visibility);
    }

    // ========== 拖放相關方法 ==========

    /**
     * 處理拖放事件
     */
    public void handleDragEvent(DragEvent.DragState state, int x, int y) {
        DragEvent event = new DragEvent(state, x, y);
        dragEventInfo.setValue(event);
        resultText.setValue(event.getFormattedInfo());
    }

    /**
     * 介面：用於取得按鈕 ID
     */
    public interface ButtonIdGetter {
        int getId(CompoundButton button);
    }
}
