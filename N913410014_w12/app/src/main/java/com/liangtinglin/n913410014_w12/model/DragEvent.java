package com.liangtinglin.n913410014_w12.model;

/**
 * 拖放事件資料模型
 * Model 層 - 負責存儲拖放操作的相關數據
 */
public class DragEvent {

    public enum DragState {
        STARTED("開始"),
        ENTERED("進入"),
        EXITED("離開"),
        ENDED("結束"),
        DROPPED("放下");

        private final String displayName;

        DragState(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private DragState state;
    private int x;
    private int y;

    public DragEvent(DragState state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    // Getters
    public DragState getState() {
        return state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 取得格式化的拖放資訊
     */
    public String getFormattedInfo() {
        if (state == DragState.ENDED) {
            return state.getDisplayName();
        }
        return state.getDisplayName() + "位置 : (" + x + "," + y + ")";
    }
}
