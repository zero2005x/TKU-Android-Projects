package com.liangtinglin.n913410014_w02.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w02.model.TextSizeConfig;

/**
 * 字型大小 ViewModel
 * <p>
 * 負責管理字型大小的業務邏輯和狀態。
 * 使用 LiveData 讓 View 可以觀察資料變化。
 * 繼承自 ViewModel，可在配置變更（如螢幕旋轉）時保存狀態。
 */
public class TextSizeViewModel extends ViewModel {

    // 使用 MutableLiveData 來保存字型大小狀態
    private final MutableLiveData<Integer> fontSize = new MutableLiveData<>();

    // 使用 MutableLiveData 來保存第一個 TextView 的文字
    private final MutableLiveData<String> firstText = new MutableLiveData<>();

    // 使用 MutableLiveData 來保存第二個 TextView 的文字
    private final MutableLiveData<String> secondText = new MutableLiveData<>();

    /**
     * 建構子：初始化預設值
     */
    public TextSizeViewModel() {
        // 設定初始字型大小
        fontSize.setValue(TextSizeConfig.DEFAULT_SIZE);
        // 設定初始文字（從 strings.xml 載入，稍後在 Activity 中設定）
        firstText.setValue("今天天氣");
        secondText.setValue("測試");
    }

    /**
     * 取得字型大小 LiveData（供 View 觀察）
     */
    public LiveData<Integer> getFontSize() {
        return fontSize;
    }

    /**
     * 取得第一個文字 LiveData
     */
    public LiveData<String> getFirstText() {
        return firstText;
    }

    /**
     * 取得第二個文字 LiveData
     */
    public LiveData<String> getSecondText() {
        return secondText;
    }

    /**
     * 增加字型大小
     */
    public void increaseFontSize() {
        Integer currentSize = fontSize.getValue();
        if (currentSize != null && currentSize < TextSizeConfig.MAX_SIZE) {
            fontSize.setValue(currentSize + TextSizeConfig.SIZE_INCREMENT);
        }
    }

    /**
     * 減少字型大小
     */
    public void decreaseFontSize() {
        Integer currentSize = fontSize.getValue();
        if (currentSize != null && currentSize > TextSizeConfig.MIN_SIZE) {
            fontSize.setValue(currentSize - TextSizeConfig.SIZE_INCREMENT);
        }
    }

    /**
     * 取得當前字型大小（用於顯示文字）
     */
    public int getCurrentFontSize() {
        Integer size = fontSize.getValue();
        return size != null ? size : TextSizeConfig.DEFAULT_SIZE;
    }

    /**
     * 設定第一個文字（當使用者點擊時）
     */
    public void updateFirstText(String baseText, String sizeLabel, int size) {
        firstText.setValue(baseText + "," + sizeLabel + size);
    }

    /**
     * 設定第二個文字（當使用者點擊時）
     */
    public void updateSecondText(String baseText, String sizeLabel, int size) {
        secondText.setValue(baseText + "," + sizeLabel + size);
    }
}
