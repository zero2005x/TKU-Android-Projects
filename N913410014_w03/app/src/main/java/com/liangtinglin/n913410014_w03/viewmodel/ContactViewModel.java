package com.liangtinglin.n913410014_w03.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w03.model.Contact;
import com.liangtinglin.n913410014_w03.model.repository.ContactRepository;

/**
 * 聯絡人 ViewModel
 * <p>
 * 負責管理聯絡人資料和業務邏輯。
 * 使用 LiveData 讓多個 View 區塊可以同時觀察並顯示相同資料。
 */
public class ContactViewModel extends ViewModel {

    private final ContactRepository repository;

    // 使用 LiveData 保存當前聯絡人
    private final MutableLiveData<Contact> currentContact = new MutableLiveData<>();

    // 訊息提示（Toast）
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    /**
     * 建構子
     */
    public ContactViewModel() {
        repository = new ContactRepository();
        // 初始化為預設聯絡人
        currentContact.setValue(repository.getDefaultContact());
    }

    /**
     * 取得當前聯絡人 LiveData（供 View 觀察）
     */
    public LiveData<Contact> getCurrentContact() {
        return currentContact;
    }

    /**
     * 取得 Toast 訊息 LiveData
     */
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    /**
     * 更新聯絡人（當使用者點擊「確認」按鈕）
     *
     * @param inputName 使用者輸入的名字
     */
    public void updateContact(String inputName) {
        if (inputName == null || inputName.trim().isEmpty()) {
            // 輸入為空，顯示提示並使用預設資料
            toastMessage.setValue("請輸入姓名");
            currentContact.setValue(repository.getDefaultContact());
        } else {
            // 建立新聯絡人
            Contact newContact = repository.createContact(inputName);
            currentContact.setValue(newContact);
        }
    }

    /**
     * 重置為預設聯絡人
     */
    public void resetToDefault() {
        currentContact.setValue(repository.getDefaultContact());
    }

    /**
     * 格式化顯示文字（提供給 View 使用）
     */
    public String formatContactField(String label, String value) {
        return label + ":" + value;
    }
}
