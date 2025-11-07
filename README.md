# 淡江大學 Android 課程專案集 (MVVM 重構版)

## 📚 專案簡介

這是淡江大學 Android 應用程式開發課程的系列專案，原始程式碼已使用 **Claude 4.5** 完整重構為 **MVVM (Model-View-ViewModel)** 架構，提升程式碼的可維護性、可測試性和可讀性。

### 🎯 重構目標

- ✅ 將原本雜亂的程式碼重構為清晰的 MVVM 架構
- ✅ 分離關注點：UI 層、業務邏輯層、資料層
- ✅ 使用 LiveData 實現響應式資料綁定
- ✅ 提升程式碼可讀性和可維護性
- ✅ 遵循 Android 開發最佳實踐

---

## 📁 專案結構

### Week 02 - 文字大小調整器

**專案名稱：** `N913410014_w02` (Java) / `N913410014_w02_kotlin` (Kotlin)

**功能：**

- 點擊 TextView 動態調整文字大小
- 增大/縮小文字功能

**MVVM 架構：**

```
├── model/
│   └── TextSizeConfig.java        # 文字大小配置常數
├── viewmodel/
│   └── TextSizeViewModel.java     # 文字大小業務邏輯
└── MainActivity.java               # View 層 - UI 互動
```

**學習重點：**

- ViewModel 基本使用
- LiveData 資料觀察
- UI 事件處理

---

### Week 03 - 聯絡人資訊展示

**專案名稱：** `N913410014_w03`

**功能：**

- 顯示聯絡人資訊（姓名、電話、Email、網站）
- 三種不同佈局方式展示
- 動態生成和靜態宣告 UI 元件

**MVVM 架構：**

```
├── model/
│   └── Contact.java               # 聯絡人資料模型
├── viewmodel/
│   └── ContactViewModel.java      # 聯絡人業務邏輯
└── MainActivity.java              # View 層 - 多佈局展示
```

**學習重點：**

- Model 資料封裝
- 動態生成 UI 元件
- Linkify 自動超連結

---

### Week 04 - 匯率與 BMI 計算器

**專案名稱：** `N913410014_w04`

**功能：**

- 雙組匯率計算器（USD ↔ NTD）
- BMI 計算器與健康狀態判斷

**MVVM 架構：**

```
├── model/
│   └── BmiResult.java             # BMI 結果資料模型
├── viewmodel/
│   ├── ExchangeViewModel.java     # 匯率計算邏輯
│   └── BmiViewModel.java          # BMI 計算邏輯
└── MainActivity.java              # View 層 - 多功能整合
```

**學習重點：**

- 多 ViewModel 協作
- 輸入驗證
- 業務邏輯封裝

---

### Week 05 - BMI 計算器進階版

**專案名稱：** `N913410014_w05`

**功能：**

- BMI 計算與健康評估
- 靜態與動態按鈕生成
- 按鈕點擊事件處理

**MVVM 架構：**

```
├── model/
│   └── BmiResult.java             # BMI 結果資料模型
├── viewmodel/
│   └── MainViewModel.java         # 主要業務邏輯
└── ui/
    └── MainActivity.java          # View 層 - UI 互動
```

**學習重點：**

- 動態生成 UI 元件
- 錯誤處理與使用者反饋
- LiveData 多類型資料流

---

### Week 06 - RGB 顏色選擇器

**專案名稱：** `N913410014_w06`

**功能：**

- RGB 顏色選擇與預覽
- 隨機顏色生成
- 動態按鈕生成

**MVVM 架構：**

```
├── model/
│   └── ColorState.java            # 顏色狀態資料模型
├── viewmodel/
│   └── MainViewModel.java         # 顏色處理邏輯
└── ui/
    └── MainActivity.java          # View 層 - 顏色互動
```

**學習重點：**

- 狀態管理
- 顏色處理
- 隨機數生成

---

### Week 07 - 觸控事件處理

**專案名稱：** `N913410014_w07`

**功能：**

- 文字大小調整
- 觸控事件處理與座標顯示
- 多點觸控支援

**MVVM 架構：**

```
├── model/
│   └── TouchEvent.java            # 觸控事件資料模型
├── viewmodel/
│   └── MainViewModel.java         # 觸控事件邏輯
└── ui/
    └── MainActivity.java          # View 層 - 觸控互動
```

**學習重點：**

- 觸控事件處理
- 座標計算
- 事件傳遞機制

---

### 其他專案

- **N931410014_w01_java** - Week 01 基礎練習 (Java)
- **N913410104_w01** - 額外練習專案 (Kotlin)

---

## 🏗️ MVVM 架構說明

### 什麼是 MVVM？

MVVM (Model-View-ViewModel) 是一種軟體架構模式，將應用程式分為三個主要部分：

```
┌─────────────┐
│    View     │  UI 層 - Activity/Fragment
│  (Activity) │  職責：顯示資料、處理使用者互動
└──────┬──────┘
       │ 觀察 LiveData
       ↓
┌─────────────┐
│  ViewModel  │  業務邏輯層
│             │  職責：處理業務邏輯、狀態管理
└──────┬──────┘
       │ 使用
       ↓
┌─────────────┐
│    Model    │  資料層
│             │  職責：資料模型、資料來源
└─────────────┘
```

### 各層職責

#### 📱 View (Activity/Fragment)

- 綁定 UI 元件
- 觀察 ViewModel 的 LiveData
- 將使用者操作轉發給 ViewModel
- **不包含業務邏輯**

#### 🧠 ViewModel

- 處理所有業務邏輯
- 管理 UI 狀態
- 使用 LiveData 發布資料變化
- 在配置變更時保持狀態

#### 📦 Model

- 定義資料結構
- 資料驗證
- 封裝業務規則

### 重構前後對比

#### ❌ 重構前（雜亂的程式碼）

```java
// 所有邏輯都在 Activity 中
public class MainActivity extends AppCompatActivity {
    private int fontSize = 20;

    public void onClick(View v) {
        fontSize += 5;  // 業務邏輯混在 View 層
        textView.setTextSize(fontSize);
    }
}
```

#### ✅ 重構後（MVVM 架構）

```java
// View 層 - 只負責 UI
public class MainActivity extends AppCompatActivity {
    private TextSizeViewModel viewModel;

    private void setupViews() {
        button.setOnClickListener(v -> viewModel.increaseFontSize());
    }

    private void observeViewModel() {
        viewModel.getFontSize().observe(this, size -> {
            textView.setTextSize(size);
        });
    }
}

// ViewModel 層 - 負責業務邏輯
public class TextSizeViewModel extends ViewModel {
    private MutableLiveData<Integer> fontSize = new MutableLiveData<>(20);

    public void increaseFontSize() {
        Integer current = fontSize.getValue();
        if (current != null && current < 100) {
            fontSize.setValue(current + 5);
        }
    }
}
```

---

## 🔧 開發環境

### 必要條件

- **Android Studio**: Ladybug | 2024.2.1 或更新版本
- **Gradle**: 8.7
- **Java**: JDK 17 或更新版本
- **Android SDK**:
  - Compile SDK: 34
  - Min SDK: 24
  - Target SDK: 34

### 相依套件

```kotlin
// Core Android
implementation("androidx.appcompat:appcompat:1.7.0")
implementation("androidx.core:core-ktx:1.13.1")
implementation("com.google.android.material:material:1.12.0")

// ViewModel & LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")

// Activity
implementation("androidx.activity:activity:1.9.3")
implementation("androidx.constraintlayout:constraintlayout:2.2.0")
```

---

## 🚀 快速開始

### 1. Clone 專案

```bash
git clone https://github.com/<你的使用者名稱>/TKU-Android-Projects.git
cd TKU-Android-Projects
```

### 2. 開啟專案

1. 開啟 Android Studio
2. 選擇 `File` > `Open`
3. 選擇任一週的專案資料夾（例如：`N913410014_w02`）
4. 等待 Gradle 同步完成

### 3. 執行專案

1. 連接 Android 裝置或啟動模擬器
2. 點擊 `Run` 按鈕或按 `Shift + F10`
3. 選擇目標裝置並執行

---

## 📖 學習路徑建議

### 初學者

1. **Week 02** - 從最簡單的 MVVM 開始
2. **Week 03** - 學習 Model 的使用
3. **Week 04** - 練習多 ViewModel 協作

### 進階

4. **Week 05** - 動態 UI 生成
5. **Week 06** - 狀態管理與顏色處理
6. **Week 07** - 觸控事件處理

---

## 🧪 測試

每個專案都包含基本的單元測試和 UI 測試：

```bash
# 執行單元測試
./gradlew test

# 執行 UI 測試（需要連接裝置）
./gradlew connectedAndroidTest
```

---

## 📝 程式碼規範

本專案遵循以下規範：

- **命名規範**:
  - Activity: `MainActivity`
  - ViewModel: `XxxViewModel`
  - Model: `XxxModel` 或 `Xxx`
- **註解規範**:
  - 每個類別都有 JavaDoc 說明
  - 公開方法都有註解說明用途
- **架構規範**:
  - 嚴格遵循 MVVM 分層
  - View 層不包含業務邏輯
  - 使用 LiveData 進行資料綁定

---

## 🤝 貢獻指南

歡迎提交 Issue 和 Pull Request！

### 提交 PR 前請確認：

- [ ] 程式碼符合 MVVM 架構
- [ ] 已添加適當的註解
- [ ] 測試通過
- [ ] UI 運作正常

---

## 📄 授權

本專案僅用於學術學習目的。

---

## 👨‍💻 作者

- **學號**: N913410014
- **課程**: 淡江大學 Android 應用程式開發
- **重構工具**: Claude 4.5 AI Assistant

---

## 🙏 致謝

- 感謝淡江大學提供優質的 Android 開發課程
- 感謝 Claude 4.5 協助進行程式碼重構
- 感謝 Android 官方文檔提供的 MVVM 最佳實踐

---

## 📞 聯絡方式

如有問題或建議，歡迎透過以下方式聯絡：

- GitHub Issues: [建立 Issue](https://github.com/<你的使用者名稱>/TKU-Android-Projects/issues)
- Email: [你的 Email]

---

## 🔗 相關資源

- [Android 官方文檔](https://developer.android.com/)
- [MVVM 架構指南](https://developer.android.com/topic/architecture)
- [LiveData 使用指南](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel 概述](https://developer.android.com/topic/libraries/architecture/viewmodel)

---

**最後更新**: 2025-11-08
