package com.liangtinglin.n913410014_w14.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w14.model.ProgressData;
import com.liangtinglin.n913410014_w14.model.RgbColor;

/**
 * 主畫面 ViewModel
 * ViewModel 層 - 負責管理 UI 相關數據和業務邏輯
 */
public class MainViewModel extends ViewModel {

    // Model 物件
    private final RgbColor rgbColor = new RgbColor();
    private final ProgressData progressData = new ProgressData();

    // 顏色相關 LiveData
    private final MutableLiveData<Integer> redValue = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> greenValue = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> blueValue = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> backgroundColor = new MutableLiveData<>();
    private final MutableLiveData<String> colorResultText = new MutableLiveData<>("");

    // 進度條相關 LiveData
    private final MutableLiveData<Integer> progress = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> progressBarVisible = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> progressButtonEnabled = new MutableLiveData<>(true);

    // 訊息相關 LiveData
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    // 寶石更新觸發器
    private final MutableLiveData<Boolean> gemInvalidate = new MutableLiveData<>();

    // Handler 用於主執行緒操作
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MainViewModel() {
        // 初始化
    }

    // ========== Getters for LiveData ==========

    public LiveData<Integer> getRedValue() {
        return redValue;
    }

    public LiveData<Integer> getGreenValue() {
        return greenValue;
    }

    public LiveData<Integer> getBlueValue() {
        return blueValue;
    }

    public LiveData<Integer> getBackgroundColor() {
        return backgroundColor;
    }

    public LiveData<String> getColorResultText() {
        return colorResultText;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }

    public LiveData<Boolean> getProgressBarVisible() {
        return progressBarVisible;
    }

    public LiveData<Boolean> getProgressButtonEnabled() {
        return progressButtonEnabled;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<Boolean> getGemInvalidate() {
        return gemInvalidate;
    }

    // ========== 顏色相關方法 ==========

    /**
     * 從輸入框設定顏色
     */
    public void setColorFromInputs(String redInput, String greenInput, String blueInput) {
        try {
            int r = Integer.parseInt(redInput.trim());
            int g = Integer.parseInt(greenInput.trim());
            int b = Integer.parseInt(blueInput.trim());

            if (!RgbColor.isValidRange(r, g, b)) {
                errorMessage.setValue("請輸入0~255的數字");
                return;
            }

            rgbColor.setRed(r);
            rgbColor.setGreen(g);
            rgbColor.setBlue(b);

            updateColorUI(true, false);

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入數字");
        }
    }

    /**
     * 生成隨機顏色
     */
    public void generateRandomColor() {
        RgbColor random = RgbColor.random();
        rgbColor.setRed(random.getRed());
        rgbColor.setGreen(random.getGreen());
        rgbColor.setBlue(random.getBlue());

        updateColorUI(true, true);
    }

    /**
     * 從 SeekBar 設定紅色
     */
    public void setRedFromSeekBar(int value) {
        rgbColor.setRed(value);
        redValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 從 SeekBar 設定綠色
     */
    public void setGreenFromSeekBar(int value) {
        rgbColor.setGreen(value);
        greenValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 從 SeekBar 設定藍色
     */
    public void setBlueFromSeekBar(int value) {
        rgbColor.setBlue(value);
        blueValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 更新顏色 UI
     */
    private void updateColorUI(boolean updateSeekBars, boolean updateEditTexts) {
        if (updateSeekBars) {
            redValue.setValue(rgbColor.getRed());
            greenValue.setValue(rgbColor.getGreen());
            blueValue.setValue(rgbColor.getBlue());
        }

        updateColorDisplay();
    }

    /**
     * 更新顏色顯示
     */
    private void updateColorDisplay() {
        backgroundColor.setValue(rgbColor.getColor());
        colorResultText.setValue(rgbColor.getFormattedResult());
    }

    // ========== 進度條相關方法 ==========

    /**
     * 開始進度任務
     */
    public void startProgressTask(String currentText) {
        progressData.setOriginalText(currentText);
        progressData.setProgress(0);
        progressData.setRunning(true);

        progressBarVisible.setValue(true);
        progress.setValue(0);
        progressButtonEnabled.setValue(false);

        // 在背景執行緒執行任務
        new Thread(() -> {
            try {
                while (!progressData.isComplete()) {
                    Thread.sleep(500);

                    mainHandler.post(() -> {
                        progressData.incrementProgress();
                        progress.setValue(progressData.getProgress());
                        colorResultText.setValue(progressData.getProgressText());
                    });
                }

                // 完成後恢復原狀
                mainHandler.post(() -> {
                    progressBarVisible.setValue(false);
                    progress.setValue(0);
                    colorResultText.setValue(progressData.getOriginalText());
                    progressButtonEnabled.setValue(true);
                    progressData.reset();
                    toastMessage.setValue("任務完成！");
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ========== WebView 相關方法 ==========

    /**
     * 驗證 URL 是否有效
     */
    public boolean isValidUrl(String url) {
        return url != null && !url.trim().isEmpty();
    }

    // ========== 寶石相關方法 ==========

    /**
     * 觸發寶石重繪
     */
    public void invalidateGem() {
        gemInvalidate.setValue(true);
    }

    /**
     * 取得當前顏色資料
     */
    public RgbColor getRgbColor() {
        return rgbColor;
    }
}
