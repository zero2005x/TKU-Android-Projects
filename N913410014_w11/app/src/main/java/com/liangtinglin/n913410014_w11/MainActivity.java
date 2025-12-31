package com.liangtinglin.n913410014_w11;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w11.model.FoodItem;
import com.liangtinglin.n913410014_w11.viewmodel.MainViewModel;

import java.util.EnumMap;
import java.util.Map;

/**
 * 主畫面 Activity
 * View 層 - 只負責 UI 顯示和使用者互動
 * 所有業務邏輯都委託給 ViewModel 處理
 */
public class MainActivity extends AppCompatActivity {

    // ViewModel
    private MainViewModel viewModel;

    // UI 元件
    private Button confirmButton, clearButton;
    private TextView convertResultTextView;
    private EditText userInputEditText;
    private RadioGroup conversionTypeRadioGroup;

    // 餐點 CheckBox
    private CheckBox hamburgerCheckBox, frenchfryCheckBox, soupCheckBox, coffeeCheckBox, softDrinkCheckBox;

    // 餐點大小 RadioGroup
    private RadioGroup softDrinkSizeRadioGroup, hamburgerSizeRadioGroup;
    private RadioGroup frenchfrySizeRadioGroup, soupSizeRadioGroup, coffeeSizeRadioGroup;

    // 結果圖片
    private ImageView resSoftDrinkImg, resHamburgerImg, resFrenchFryImg, resSoupImg, resCoffeeImg;

    // FoodType 對應 ImageView 的映射
    private Map<FoodItem.FoodType, ImageView> foodImageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化 ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 初始化 UI 元件
        initViews();

        // 設定監聽器
        setupListeners();

        // 觀察 LiveData
        observeViewModel();
    }

    /**
     * 初始化所有 UI 元件
     */
    private void initViews() {
        // 按鈕
        confirmButton = findViewById(R.id.confirmButton);
        clearButton = findViewById(R.id.clearButton);

        // 文字元件
        convertResultTextView = findViewById(R.id.convertResultTextView);
        userInputEditText = findViewById(R.id.userInputEditText);

        // 溫度轉換 RadioGroup
        conversionTypeRadioGroup = findViewById(R.id.conversionTypeRadioGroup);

        // 餐點 CheckBox
        hamburgerCheckBox = findViewById(R.id.hamburgerCheckBox);
        frenchfryCheckBox = findViewById(R.id.frenchfryCheckBox);
        soupCheckBox = findViewById(R.id.soupCheckBox);
        coffeeCheckBox = findViewById(R.id.coffeeCheckBox);
        softDrinkCheckBox = findViewById(R.id.softDrinkCheckBox);

        // 餐點大小 RadioGroup
        softDrinkSizeRadioGroup = findViewById(R.id.softDrinkSizeRadioGroup);
        hamburgerSizeRadioGroup = findViewById(R.id.hamburgerSizeRadioGroup);
        frenchfrySizeRadioGroup = findViewById(R.id.frenchfrySizeRadioGroup);
        soupSizeRadioGroup = findViewById(R.id.soupSizeRadioGroup);
        coffeeSizeRadioGroup = findViewById(R.id.coffeeSizeRadioGroup);

        // 結果圖片
        resSoftDrinkImg = findViewById(R.id.resSoftDrinkImg);
        resHamburgerImg = findViewById(R.id.resHamburgerImg);
        resFrenchFryImg = findViewById(R.id.resFrenchFryImg);
        resSoupImg = findViewById(R.id.resSoupImg);
        resCoffeeImg = findViewById(R.id.resCoffeeImg);

        // 建立 FoodType 對應 ImageView 的映射
        foodImageMap = new EnumMap<>(FoodItem.FoodType.class);
        foodImageMap.put(FoodItem.FoodType.SOFT_DRINK, resSoftDrinkImg);
        foodImageMap.put(FoodItem.FoodType.HAMBURGER, resHamburgerImg);
        foodImageMap.put(FoodItem.FoodType.FRENCH_FRY, resFrenchFryImg);
        foodImageMap.put(FoodItem.FoodType.SOUP, resSoupImg);
        foodImageMap.put(FoodItem.FoodType.COFFEE, resCoffeeImg);
    }

    /**
     * 設定所有監聽器
     */
    private void setupListeners() {
        // 按鈕監聽器
        confirmButton.setOnClickListener(v -> onConfirmClick());
        clearButton.setOnClickListener(v -> onClearClick());

        // 溫度輸入監聽器
        userInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.performConversion(s.toString());
            }
        });

        // 溫度轉換模式監聽器
        conversionTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean isCelsiusToFahrenheit = (checkedId == R.id.celsiusToFahrenheitRadioButton);
            viewModel.setConversionMode(isCelsiusToFahrenheit);

            String input = userInputEditText.getText().toString();
            if (!input.isEmpty()) {
                viewModel.performConversion(input);
            } else {
                Toast.makeText(this, "請輸入數字進行溫度轉換", Toast.LENGTH_SHORT).show();
            }
        });

        // 餐點 CheckBox 監聽器
        setupFoodCheckBoxListener(hamburgerCheckBox, FoodItem.FoodType.HAMBURGER);
        setupFoodCheckBoxListener(frenchfryCheckBox, FoodItem.FoodType.FRENCH_FRY);
        setupFoodCheckBoxListener(soupCheckBox, FoodItem.FoodType.SOUP);
        setupFoodCheckBoxListener(coffeeCheckBox, FoodItem.FoodType.COFFEE);
        setupFoodCheckBoxListener(softDrinkCheckBox, FoodItem.FoodType.SOFT_DRINK);

        // 餐點大小 RadioGroup 監聽器
        setupSizeRadioGroupListener(hamburgerSizeRadioGroup, FoodItem.FoodType.HAMBURGER);
        setupSizeRadioGroupListener(frenchfrySizeRadioGroup, FoodItem.FoodType.FRENCH_FRY);
        setupSizeRadioGroupListener(soupSizeRadioGroup, FoodItem.FoodType.SOUP);
        setupSizeRadioGroupListener(coffeeSizeRadioGroup, FoodItem.FoodType.COFFEE);
        setupSizeRadioGroupListener(softDrinkSizeRadioGroup, FoodItem.FoodType.SOFT_DRINK);
    }

    /**
     * 設定餐點 CheckBox 監聽器
     */
    private void setupFoodCheckBoxListener(CheckBox checkBox, FoodItem.FoodType foodType) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> 
            viewModel.setFoodSelected(foodType, isChecked)
        );
    }

    /**
     * 設定大小 RadioGroup 監聽器
     */
    private void setupSizeRadioGroupListener(RadioGroup radioGroup, FoodItem.FoodType foodType) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = findViewById(checkedId);
            if (selectedButton != null) {
                String sizeText = selectedButton.getText().toString();
                FoodItem.Size size = parseSizeFromText(sizeText);
                viewModel.setFoodSize(foodType, size);
            }
        });
    }

    /**
     * 從文字解析大小
     */
    private FoodItem.Size parseSizeFromText(String text) {
        if (text.contains("大")) {
            return FoodItem.Size.LARGE;
        } else if (text.contains("中")) {
            return FoodItem.Size.MEDIUM;
        } else {
            return FoodItem.Size.SMALL;
        }
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察結果文字
        viewModel.getResultText().observe(this, result -> 
            convertResultTextView.setText(result)
        );

        // 觀察錯誤訊息
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        // 觀察餐點圖片可見性
        viewModel.getFoodVisibility().observe(this, visibility -> {
            if (visibility != null) {
                for (Map.Entry<FoodItem.FoodType, Boolean> entry : visibility.entrySet()) {
                    ImageView imageView = foodImageMap.get(entry.getKey());
                    if (imageView != null) {
                        imageView.setVisibility(entry.getValue() ? View.VISIBLE : View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 確認按鈕點擊處理
     */
    private void onConfirmClick() {
        String temperatureInput = userInputEditText.getText().toString();
        viewModel.handleConfirm(temperatureInput);
    }

    /**
     * 清除按鈕點擊處理
     */
    private void onClearClick() {
        // 清除 UI
        userInputEditText.setText("");

        // 清除餐點勾選
        hamburgerCheckBox.setChecked(false);
        frenchfryCheckBox.setChecked(false);
        soupCheckBox.setChecked(false);
        coffeeCheckBox.setChecked(false);
        softDrinkCheckBox.setChecked(false);

        // 重設大小為小
        resetAllSizesToSmall();

        // 通知 ViewModel 清除資料
        viewModel.clearAll();
    }

    /**
     * 將所有大小選項重設為「小」
     */
    private void resetAllSizesToSmall() {
        softDrinkSizeRadioGroup.check(R.id.rb_softdrink_s);
        hamburgerSizeRadioGroup.check(R.id.rb_hamburger_s);
        frenchfrySizeRadioGroup.check(R.id.rb_frenchfry_s);
        soupSizeRadioGroup.check(R.id.rb_soup_s);
        coffeeSizeRadioGroup.check(R.id.rb_coffee_s);
    }
}