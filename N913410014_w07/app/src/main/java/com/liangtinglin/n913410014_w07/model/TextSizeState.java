package com.liangtinglin.n913410014_w07.model;

/**
 * 文字大小狀態的資料模型
 */
public class TextSizeState {
    private final int size;
    private final int maxSize;
    private final int minSize;

    public static final int DEFAULT_SIZE = 12;
    public static final int MAX_SIZE = 100;
    public static final int MIN_SIZE = 12;
    public static final int RESET_SIZE = 25;

    public TextSizeState(int size, int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.size = clamp(size);
    }

    public TextSizeState(int size) {
        this(size, MIN_SIZE, MAX_SIZE);
    }

    /**
     * 確保文字大小在合理範圍內
     */
    private int clamp(int value) {
        return Math.max(minSize, Math.min(maxSize, value));
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    /**
     * 增加文字大小
     */
    public TextSizeState increase(int amount) {
        return new TextSizeState(size + amount, minSize, maxSize);
    }

    /**
     * 減少文字大小
     */
    public TextSizeState decrease(int amount) {
        return new TextSizeState(size - amount, minSize, maxSize);
    }

    /**
     * 重置為預設大小
     */
    public TextSizeState reset() {
        return new TextSizeState(RESET_SIZE, minSize, maxSize);
    }

    /**
     * 檢查是否已達最大值
     */
    public boolean isAtMax() {
        return size >= maxSize;
    }

    /**
     * 檢查是否已達最小值
     */
    public boolean isAtMin() {
        return size <= minSize;
    }
}
