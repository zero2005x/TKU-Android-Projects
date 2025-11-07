package com.liangtinglin.n913410014_w03.model;

/**
 * 聯絡人資料模型
 * <p>
 * 使用不可變類別 (Immutable Class) 設計，所有欄位為 final。
 * 提供 Builder 模式方便建立物件。
 */
public class Contact {

    private final String name;
    private final String telephone;
    private final String email;
    private final String website;

    /**
     * 建構子（私有，強制使用 Builder）
     */
    private Contact(Builder builder) {
        this.name = builder.name;
        this.telephone = builder.telephone;
        this.email = builder.email;
        this.website = builder.website;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    /**
     * Builder 模式
     */
    public static class Builder {
        private String name = "";
        private String telephone = "";
        private String email = "";
        private String website = "";

        public Builder setName(String name) {
            this.name = name != null ? name : "";
            return this;
        }

        public Builder setTelephone(String telephone) {
            this.telephone = telephone != null ? telephone : "";
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email != null ? email : "";
            return this;
        }

        public Builder setWebsite(String website) {
            this.website = website != null ? website : "";
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    /**
     * 格式化聯絡人資訊為顯示用字串
     */
    public String formatField(String label, String value) {
        return label + ":" + value;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
