package com.liangtinglin.n913410014_w10.model;

import android.graphics.Typeface;

/**
 * 字體樣式資料模型
 * <p>
 * 定義可用的字體樣式類型。
 */
public class FontStyle {
    /**
     * 字體樣式類型
     */
    public enum Style {
        NORMAL(Typeface.NORMAL),
        BOLD(Typeface.BOLD),
        ITALIC(Typeface.ITALIC),
        BOLD_ITALIC(Typeface.BOLD_ITALIC);

        private final int typefaceValue;

        Style(int typefaceValue) {
            this.typefaceValue = typefaceValue;
        }

        /**
         * 取得 Typeface 對應的值
         */
        public int getTypefaceValue() {
            return typefaceValue;
        }
    }

    private Style currentStyle;

    /**
     * 建構子
     */
    public FontStyle() {
        this.currentStyle = Style.NORMAL;
    }

    /**
     * 取得當前字體樣式
     */
    public Style getCurrentStyle() {
        return currentStyle;
    }

    /**
     * 設定字體樣式
     */
    public void setCurrentStyle(Style style) {
        this.currentStyle = style;
    }

    /**
     * 取得 Typeface 值
     */
    public int getTypefaceValue() {
        return currentStyle.getTypefaceValue();
    }
}
