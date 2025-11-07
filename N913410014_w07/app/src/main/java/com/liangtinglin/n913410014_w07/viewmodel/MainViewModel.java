package com.liangtinglin.n913410014_w07.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w07.model.TextSizeState;

/**
 * MainViewModel - 處理文字大小調整和觸控事件的業務邏輯
 */
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    // LiveData for text size state
    private final MutableLiveData<TextSizeState> textSizeState = new MutableLiveData<>();

    // LiveData for touch event messages
    private final MutableLiveData<String> touchEventMessage = new MutableLiveData<>();

    // LiveData for warning messages
    private final MutableLiveData<String> warningMessage = new MutableLiveData<>();

    public MainViewModel() {
        // 初始化預設大小
        textSizeState.setValue(new TextSizeState(TextSizeState.DEFAULT_SIZE));
        Log.d(TAG, "MainViewModel: 已初始化，預設大小 = " + TextSizeState.DEFAULT_SIZE);
    }

    public LiveData<TextSizeState> getTextSizeState() {
        return textSizeState;
    }

    public LiveData<String> getTouchEventMessage() {
        return touchEventMessage;
    }

    public LiveData<String> getWarningMessage() {
        return warningMessage;
    }

    /**
     * 放大文字（單擊）
     */
    public void enlargeText() {
        TextSizeState current = textSizeState.getValue();
        if (current == null) return;

        if (current.isAtMax()) {
            Log.w(TAG, "enlargeText: 已達最大尺寸 " + current.getMaxSize());
            warningMessage.setValue("已達最大尺寸");
            return;
        }

        TextSizeState newState = current.increase(1);
        textSizeState.setValue(newState);
        Log.v(TAG, "enlargeText: 文字大小更新為 " + newState.getSize() + "sp");
    }

    /**
     * 縮小文字（單擊）
     */
    public void shrinkText() {
        TextSizeState current = textSizeState.getValue();
        if (current == null) return;

        if (current.isAtMin()) {
            Log.w(TAG, "shrinkText: 已達最小尺寸 " + current.getMinSize());
            warningMessage.setValue("已達最小尺寸");
            return;
        }

        TextSizeState newState = current.decrease(1);
        textSizeState.setValue(newState);
        Log.v(TAG, "shrinkText: 文字大小更新為 " + newState.getSize() + "sp");
    }

    /**
     * 快速放大文字（長按）
     */
    public void enlargeTextFast() {
        TextSizeState current = textSizeState.getValue();
        if (current == null) return;

        if (current.getSize() > 50) {
            Log.w(TAG, "enlargeTextFast: 已達長按放大限制 (50)");
            warningMessage.setValue("長按放大已達限制");
            return;
        }

        TextSizeState newState = current.increase(5);
        textSizeState.setValue(newState);
        Log.d(TAG, "enlargeTextFast: 快速放大至 " + newState.getSize() + "sp");
    }

    /**
     * 快速縮小文字（長按）
     */
    public void shrinkTextFast() {
        TextSizeState current = textSizeState.getValue();
        if (current == null) return;

        if (current.getSize() < 12) {
            Log.w(TAG, "shrinkTextFast: 已達長按縮小限制 (12)");
            warningMessage.setValue("長按縮小已達限制");
            return;
        }

        TextSizeState newState = current.decrease(5);
        textSizeState.setValue(newState);
        Log.d(TAG, "shrinkTextFast: 快速縮小至 " + newState.getSize() + "sp");
    }

    /**
     * 重置文字大小（長按 TextView）
     */
    public void resetTextSize() {
        TextSizeState newState = new TextSizeState(TextSizeState.RESET_SIZE);
        textSizeState.setValue(newState);
        Log.i(TAG, "resetTextSize: 重置大小為 " + TextSizeState.RESET_SIZE);
    }

    /**
     * 處理觸控事件
     * @param action 觸控動作（DOWN, UP, MOVE）
     * @param x X 座標
     * @param y Y 座標
     */
    public void handleTouchEvent(String action, int x, int y) {
        String message = String.format("觸控事件: %s - 座標 (%d, %d)", action, x, y);
        touchEventMessage.setValue(message);
        Log.d(TAG, "handleTouchEvent: " + message);
    }
}
