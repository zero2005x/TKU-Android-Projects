package com.liangtinglin.n913410014_w10.model;

/**
 * 使用者資訊資料模型
 * <p>
 * 包含使用者的姓名和性別資訊。
 */
public class UserInfo {
    private String name;
    private Gender gender;

    /**
     * 性別列舉
     */
    public enum Gender {
        MALE,
        FEMALE,
        UNSPECIFIED
    }

    /**
     * 建構子
     */
    public UserInfo() {
        this.name = "";
        this.gender = Gender.UNSPECIFIED;
    }

    /**
     * 建構子
     *
     * @param name   姓名
     * @param gender 性別
     */
    public UserInfo(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    /**
     * 取得姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 設定姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 取得性別
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 設定性別
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 產生問候語
     *
     * @return 完整的問候語文字
     */
    public String generateGreeting() {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        String prefix = "";
        switch (gender) {
            case MALE:
                prefix = "Mr ";
                break;
            case FEMALE:
                prefix = "Mrs ";
                break;
            default:
                prefix = "";
                break;
        }

        return prefix + name + ", Hello";
    }

    /**
     * 檢查姓名是否有效
     */
    public boolean isNameValid() {
        return name != null && !name.trim().isEmpty();
    }
}
