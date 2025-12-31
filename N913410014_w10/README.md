# Week 10 - 圖片與文字互動應用

## 📱 專案簡介

這是淡江大學 Android 課程第十週的練習專案，實作一個圖片與文字互動的應用程式。本專案已重構為 **MVVM (Model-View-ViewModel)** 架構，展示如何處理複雜的 UI 互動和多種使用者輸入。

## ✨ 功能特色

### 1. 圖片點擊功能

- 點擊不同的食物圖片（Coffee、French Fry、Hamburger、Soft Drink、Soup）
- 自動顯示對應的食物名稱
- 使用 ImageView 的點擊事件處理

### 2. 使用者資訊輸入

- EditText 輸入使用者姓名
- RadioGroup 選擇性別（男性/女性）
- 自動產生問候語（Mr/Mrs + 姓名 + Hello）
- 輸入驗證與錯誤提示

### 3. 字體樣式切換

- Normal（正常）
- Bold（粗體）
- Italic（斜體）
- Bold Italic（粗斜體）
- 使用 RadioGroup 動態切換

## 🏗️ MVVM 架構

### 📦 Model 層

#### `FoodItem.java`

食物項目的資料模型，封裝：

- 圖片資源 ID
- 食物名稱
- ImageView 的 ID

#### `UserInfo.java`

使用者資訊的資料模型，包含：

- 姓名
- 性別（Male/Female/Unspecified）
- 問候語生成邏輯
- 姓名驗證方法

#### `FontStyle.java`

字體樣式的資料模型，定義：

- 四種字體樣式（Normal、Bold、Italic、Bold Italic）
- Typeface 值的對應關係

### 🧠 ViewModel 層

#### `MainViewModel.java`

處理所有業務邏輯：

- 管理食物項目列表
- 處理圖片點擊事件
- 管理使用者資訊狀態
- 處理字體樣式切換
- 提供 LiveData 供 View 觀察

**LiveData 資料流：**

```
displayText (String)         → 顯示的文字內容
selectedImageResId (Integer) → 選中的圖片資源 ID
fontStyle (FontStyle.Style)  → 當前字體樣式
errorMessage (String)        → 錯誤訊息
```

### 📱 View 層

#### `MainActivity.java`

僅負責 UI 互動：

- 綁定 UI 元件
- 觀察 ViewModel 的 LiveData
- 設定事件監聽器
- 更新 UI 顯示

## 📂 專案結構

```
N913410014_w10/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/liangtinglin/n913410014_w10/
│   │   │   │   ├── model/
│   │   │   │   │   ├── FoodItem.java        # 食物項目資料模型
│   │   │   │   │   ├── UserInfo.java        # 使用者資訊資料模型
│   │   │   │   │   └── FontStyle.java       # 字體樣式資料模型
│   │   │   │   ├── viewmodel/
│   │   │   │   │   └── MainViewModel.java   # 主要業務邏輯
│   │   │   │   └── MainActivity.java        # View 層 - UI 互動
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml    # 主畫面佈局
│   │   │   │   └── drawable/                # 食物圖片資源
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                     # UI 測試
│   │   └── test/                            # 單元測試
│   └── build.gradle.kts                     # 專案建置設定
├── gradle/                                  # Gradle 設定
└── build.gradle.kts                         # 專案層級建置設定
```

## 🔑 關鍵程式碼說明

### 1. 初始化食物項目（ViewModel）

```java
private void initializeFoodItems() {
    foodItems.add(new FoodItem(R.drawable.coffee, "Coffee", R.id.coffeeImageView));
    foodItems.add(new FoodItem(R.drawable.frenchfry, "French_Fry", R.id.frenchFryImageView));
    // ... 其他食物項目
}
```

### 2. 處理圖片點擊（ViewModel）

```java
public void onImageClicked(int imageViewId) {
    for (FoodItem item : foodItems) {
        if (item.getImageViewId() == imageViewId) {
            selectedImageResId.setValue(item.getImageResId());
            displayText.setValue(item.getName());
            break;
        }
    }
}
```

### 3. 產生問候語（Model）

```java
public String generateGreeting() {
    String prefix = gender == Gender.MALE ? "Mr " : "Mrs ";
    return prefix + name + ", Hello";
}
```

### 4. 觀察資料變化（View）

```java
viewModel.getDisplayText().observe(this, text -> {
    displayTextView.setText(text);
});

viewModel.getFontStyle().observe(this, style -> {
    Typeface currentTypeface = displayTextView.getTypeface();
    displayTextView.setTypeface(currentTypeface, style.getTypefaceValue());
});
```

## 🎯 學習重點

### 1. MVVM 架構實踐

- **分離關注點**：UI、業務邏輯、資料層完全分離
- **LiveData 響應式**：資料變化自動更新 UI
- **ViewModel 生命週期**：配置變更時保持狀態

### 2. 複雜 UI 互動

- **多個 RadioGroup**：處理多組選項
- **EditText 驗證**：使用者輸入的驗證與錯誤處理
- **動態文字更新**：根據不同操作更新顯示內容

### 3. 資料模型設計

- **FoodItem**：封裝圖片與名稱的對應關係
- **UserInfo**：封裝使用者資訊與問候語邏輯
- **FontStyle**：使用 Enum 管理字體樣式

### 4. 事件處理

- **圖片點擊**：使用 Lambda 簡化監聽器
- **RadioGroup 監聽**：區分不同 RadioGroup 的事件
- **使用者輸入**：即時取得 EditText 內容

## 🚀 執行專案

### 1. 開啟專案

```bash
# 使用 Android Studio 開啟
File > Open > 選擇 N913410014_w10 資料夾
```

### 2. 同步 Gradle

```bash
# Android Studio 會自動提示同步
# 或手動點擊 Sync Project with Gradle Files
```

### 3. 執行應用

```bash
# 連接 Android 裝置或啟動模擬器
# 點擊 Run 按鈕或按 Shift + F10
```

## 📋 必要條件

- **Android Studio**: Ladybug | 2024.2.1 或更新版本
- **Gradle**: 8.13
- **Java**: JDK 11 或更新版本
- **Android SDK**:
  - Compile SDK: 36
  - Min SDK: 28
  - Target SDK: 36

## 📦 相依套件

```kotlin
// Core Android
implementation("androidx.appcompat:appcompat:1.7.0")
implementation("androidx.material:material:1.12.0")
implementation("androidx.activity:activity:1.9.3")
implementation("androidx.constraintlayout:constraintlayout:2.2.0")

// ViewModel & LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")
```

## 🧪 測試

本專案包含單元測試和 UI 測試：

```bash
# 執行單元測試
./gradlew test

# 執行 UI 測試（需要連接裝置）
./gradlew connectedAndroidTest
```

## 💡 重構前後對比

### ❌ 重構前（雜亂的程式碼）

```java
public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    int[] imageViewList = {...};
    String[] imageNameList = {...};

    @Override
    public void onClick(View v) {
        // 所有邏輯都混在一起
        for (int i = 0; i < idList.length; i++) {
            if (idList[i] == v.getId()) {
                imageView.setImageResource(imageViewList[i]);
                imageNameTextView.setText(imageNameList[i]);
            }
        }
    }
}
```

### ✅ 重構後（MVVM 架構）

**Model 層：**

```java
public class FoodItem {
    private final int imageResId;
    private final String name;
    // ...
}
```

**ViewModel 層：**

```java
public class MainViewModel extends ViewModel {
    private final MutableLiveData<String> displayText = new MutableLiveData<>();

    public void onImageClicked(int imageViewId) {
        // 業務邏輯處理
        displayText.setValue(item.getName());
    }
}
```

**View 層：**

```java
public class MainActivity extends AppCompatActivity {
    private void observeViewModel() {
        viewModel.getDisplayText().observe(this, text -> {
            displayTextView.setText(text);
        });
    }
}
```

## 🎓 延伸學習

1. **資料持久化**：使用 Room Database 儲存食物資料
2. **圖片載入**：整合 Glide 或 Picasso 載入網路圖片
3. **RecyclerView**：使用 RecyclerView 顯示食物列表
4. **動畫效果**：添加圖片點擊的動畫效果
5. **多語言支援**：實作國際化（i18n）

## 📝 程式碼規範

本專案遵循以下規範：

- ✅ 使用 MVVM 架構模式
- ✅ 每個類別都有完整的 JavaDoc 註解
- ✅ 使用語意化的變數命名
- ✅ View 層不包含業務邏輯
- ✅ 使用 LiveData 進行資料綁定

## 👨‍💻 作者

- **學號**: N913410014
- **課程**: 淡江大學 Android 應用程式開發
- **Week**: 10

## 📄 授權

本專案僅用於學術學習目的。

---

**最後更新**: 2025-12-31
