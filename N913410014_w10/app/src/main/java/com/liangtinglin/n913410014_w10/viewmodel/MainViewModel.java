package com.liangtinglin.n913410014_w10.viewmodel;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w10.R;
import com.liangtinglin.n913410014_w10.model.FoodItem;
import com.liangtinglin.n913410014_w10.model.FontStyle;
import com.liangtinglin.n913410014_w10.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 主 ViewModel (MVVM - ViewModel 層)
 * <p>
 * 職責：
 * - 管理食物項目資料
 * - 處理使用者資訊（姓名、性別）
 * - 管理字體樣式
 * - 處理圖片點擊邏輯
 * - 提供 LiveData 供 View 觀察
 */
public class MainViewModel extends ViewModel {

    // 食物項目列表
    private final List<FoodItem> foodItems = new ArrayList<>();

    // LiveData - 當前顯示的文字
    private final MutableLiveData<String> displayText = new MutableLiveData<>("Hello World!");

    // LiveData - 當前選擇的圖片資源 ID
    private final MutableLiveData<Integer> selectedImageResId = new MutableLiveData<>();

    // LiveData - 字體樣式
    private final MutableLiveData<FontStyle.Style> fontStyle = new MutableLiveData<>(FontStyle.Style.NORMAL);

    // LiveData - 錯誤訊息
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // 使用者資訊
    private final UserInfo userInfo = new UserInfo();

    // 字體樣式管理
    private final FontStyle fontStyleManager = new FontStyle();

    /**
     * 建構子 - 初始化食物項目
     */
    public MainViewModel() {
        initializeFoodItems();
    }




    /**
     * 初始化食物項目列表
     */
    private void initializeFoodItems() {
        foodItems.add(new FoodItem(R.drawable.coffee, "Coffee", R.id.coffeeImageView));
        foodItems.add(new FoodItem(R.drawable.frenchfry, "French_Fry", R.id.frenchFryImageView));
        foodItems.add(new FoodItem(R.drawable.hamburger, "Hamburger", R.id.hamburgerImageView));
        foodItems.add(new FoodItem(R.drawable.softdrink, "Soft_Drink", R.id.softDrinkImageView));
        foodItems.add(new FoodItem(R.drawable.soup, "Soup", R.id.soupImageView));
    }

    /**
     * 取得食物項目列表
     */
    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    /**
     * 取得顯示文字的 LiveData
     */
    public LiveData<String> getDisplayText() {
        return displayText;
    }

    /**
     * 取得選擇的圖片資源 ID 的 LiveData
     */
    public LiveData<Integer> getSelectedImageResId() {
        return selectedImageResId;
    }

    /**
     * 取得字體樣式的 LiveData
     */
    public LiveData<FontStyle.Style> getFontStyle() {
        return fontStyle;
    }

    /**
     * 取得錯誤訊息的 LiveData
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 處理圖片點擊事件
     *
     * @param imageViewId 被點擊的 ImageView 的 ID
     */
    public void onImageClicked(int imageViewId) {
        for (FoodItem item : foodItems) {
            if (item.getImageViewId() == imageViewId) {
                selectedImageResId.setValue(item.getImageResId());
                displayText.setValue(item.getName());
                break;
            }
        }
    }

    /**
     * 設定使用者姓名
     *
     * @param name 使用者姓名
     */
    public void setUserName(String name) {
        userInfo.setName(name);
    }

    /**
     * 設定使用者性別並產生問候語
     *
     * @param gender 性別
     */
    public void setUserGender(UserInfo.Gender gender) {
        userInfo.setGender(gender);
        updateGreeting();
    }

    /**
     * 更新問候語
     */
    private void updateGreeting() {
        if (!userInfo.isNameValid()) {
            errorMessage.setValue("Your Name is not Entered");
            return;
        }

        String greeting = userInfo.generateGreeting();
        displayText.setValue(greeting);
    }

    /**
     * 設定字體樣式
     *
     * @param style 字體樣式
     */
    public void setFontStyle(FontStyle.Style style) {
        fontStyleManager.setCurrentStyle(style);
        fontStyle.setValue(style);
    }

    /**
     * 取得當前字體樣式的 Typeface 值
     */
    public int getCurrentTypefaceValue() {
        return fontStyleManager.getTypefaceValue();
    }
}
