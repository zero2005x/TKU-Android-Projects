package com.liangtinglin.n913410014_w14.model;

/**
 * 進度條資料模型
 * Model 層 - 負責存儲進度相關數據
 */
public class ProgressData {
    private int progress;
    private boolean isRunning;
    private String originalText;

    private static final int MIN_PROGRESS = 0;
    private static final int MAX_PROGRESS = 100;
    private static final int PROGRESS_INCREMENT = 20;

    public ProgressData() {
        this.progress = 0;
        this.isRunning = false;
        this.originalText = "";
    }

    // Getters and Setters
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < MIN_PROGRESS) {
            this.progress = MIN_PROGRESS;
        } else if (progress > MAX_PROGRESS) {
            this.progress = MAX_PROGRESS;
        } else {
            this.progress = progress;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    /**
     * 增加進度
     */
    public void incrementProgress() {
        setProgress(progress + PROGRESS_INCREMENT);
    }

    /**
     * 重設進度
     */
    public void reset() {
        this.progress = 0;
        this.isRunning = false;
    }

    /**
     * 檢查進度是否完成
     */
    public boolean isComplete() {
        return progress >= MAX_PROGRESS;
    }

    /**
     * 取得進度百分比文字
     */
    public String getProgressText() {
        return "載入中... " + progress + "%";
    }
}
