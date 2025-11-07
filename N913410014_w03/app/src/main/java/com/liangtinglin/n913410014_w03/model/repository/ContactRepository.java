package com.liangtinglin.n913410014_w03.model.repository;

import com.liangtinglin.n913410014_w03.model.Contact;

/**
 * 聯絡人資料倉庫
 * <p>
 * 負責提供聯絡人資料。在實際應用中，這裡可以連接資料庫或網路 API。
 * 目前使用預設資料（模擬教學範例）。
 */
public class ContactRepository {

    // 預設聯絡人資料
    private static final Contact DEFAULT_CONTACT = new Contact.Builder()
            .setName("Wayne Lin")
            .setTelephone("0936123456")
            .setEmail("wayne@takming.edu.tw")
            .setWebsite("http://wayne.cif.takming.edu.tw")
            .build();

    /**
     * 取得預設聯絡人
     */
    public Contact getDefaultContact() {
        return DEFAULT_CONTACT;
    }

    /**
     * 根據使用者輸入建立新聯絡人
     * 如果輸入為空，使用預設值
     */
    public Contact createContact(String inputName) {
        if (inputName == null || inputName.trim().isEmpty()) {
            return DEFAULT_CONTACT;
        }

        // 保留其他預設資料，只替換名字
        return new Contact.Builder()
                .setName(inputName.trim())
                .setTelephone(DEFAULT_CONTACT.getTelephone())
                .setEmail(DEFAULT_CONTACT.getEmail())
                .setWebsite(DEFAULT_CONTACT.getWebsite())
                .build();
    }
}
