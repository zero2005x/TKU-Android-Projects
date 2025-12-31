package com.liangtinglin.n913410014_w13.model;

/**
 * 使用者資料模型
 * Model 層 - 負責存儲使用者相關數據
 */
public class UserProfile {
    
    public enum Gender {
        MALE("先生"),
        FEMALE("小姐");

        private final String displaySuffix;

        Gender(String displaySuffix) {
            this.displaySuffix = displaySuffix;
        }

        public String getDisplaySuffix() {
            return displaySuffix;
        }
    }

    private String name;
    private Gender gender;

    public UserProfile() {
        this.name = "";
        this.gender = Gender.MALE;
    }

    public UserProfile(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 取得格式化的問候語
     */
    public String getGreeting() {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name + gender.getDisplaySuffix() + "好";
    }

    /**
     * 檢查名字是否有效
     */
    public boolean isNameValid() {
        return name != null && !name.isEmpty();
    }
}
