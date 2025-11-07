package com.liangtinglin.n913410014_w02.model;

/**
 * 字型大小配置類別
 * <p>
 * 定義字型大小的常數和規則，遵循單一職責原則 (SRP)。
 * 將配置從業務邏輯中分離，提高可維護性。
 */
public class TextSizeConfig {

    // 預設字型大小
    public static final int DEFAULT_SIZE = 25;

    // 最小字型大小
    public static final int MIN_SIZE = 25;

    // 最大字型大小
    public static final int MAX_SIZE = 38;

    // 每次調整的增量
    public static final int SIZE_INCREMENT = 1;

    /**
     * 驗證字型大小是否在有效範圍內
     *
     * @param size 要驗證的字型大小
     * @return 如果在範圍內返回 true，否則返回 false
     */
    public static boolean isValidSize(int size) {
        return size >= MIN_SIZE && size <= MAX_SIZE;
    }

    /**
     * 限制字型大小在有效範圍內
     *
     * @param size 原始字型大小
     * @return 被限制在有效範圍內的字型大小
     */
    public static int clampSize(int size) {
        if (size < MIN_SIZE) return MIN_SIZE;
        if (size > MAX_SIZE) return MAX_SIZE;
        return size;
    }

    // 私有建構子，防止實例化
    private TextSizeConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
