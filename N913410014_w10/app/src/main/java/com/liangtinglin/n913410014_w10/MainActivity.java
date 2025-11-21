package com.liangtinglin.n913410014_w10;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w10.model.FoodItem;
import com.liangtinglin.n913410014_w10.model.FontStyle;
import com.liangtinglin.n913410014_w10.model.UserInfo;
import com.liangtinglin.n913410014_w10.viewmodel.MainViewModel;

/**
 * 主 Activity (MVVM - View 層)
 * <p>
 * 職責：
 * - 綁定 UI 元件
 * - 觀察 ViewModel 的資料變化並更新 UI
 * - 將使用者操作轉發給 ViewModel
 * <p>
 * 功能：
 * - 圖片點擊顯示食物名稱
 * - 姓名輸入與性別選擇產生問候語
 * - 字體樣式切換
 */
public class MainActivity extends AppCompatActivity {

    // UI 元件
    private TextView displayTextView;
    private EditText nameEditText;
    private RadioGroup genderRadioGroup;
    private RadioGroup fontStyleRadioGroup;

    // ViewModel
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 設定 Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化 ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 綁定 UI 元件
        setupViews();

        // 觀察 ViewModel 資料變化
        observeViewModel();

        // 設定圖片點擊事件
        setupImageClickListeners();
    }

    /**
     * 綁定 UI 元件
     */
    private void setupViews() {
        displayTextView = findViewById(R.id.res);
        nameEditText = findViewById(R.id.enterNameEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        fontStyleRadioGroup = findViewById(R.id.fontStyleRadioGroup);

        // 設定性別選擇監聽器
        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            onGenderChanged(checkedId);
        });

        // 設定字體樣式監聽器
        fontStyleRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            onFontStyleChanged(checkedId);
        });
    }

    /**
     * 設定圖片點擊事件監聽器
     */
    private void setupImageClickListeners() {
        for (FoodItem item : viewModel.getFoodItems()) {
            ImageView imageView = findViewById(item.getImageViewId());
            imageView.setOnClickListener(v -> {
                viewModel.onImageClicked(item.getImageViewId());
            });
        }
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察顯示文字變化
        viewModel.getDisplayText().observe(this, text -> {
            if (text != null) {
                displayTextView.setText(text);
            }
        });

        // 觀察選擇的圖片資源 ID
        viewModel.getSelectedImageResId().observe(this, resId -> {
            if (resId != null) {
                // 可以在這裡處理圖片變化，目前不需要額外處理
            }
        });

        // 觀察字體樣式變化
        viewModel.getFontStyle().observe(this, style -> {
            if (style != null) {
                Typeface currentTypeface = displayTextView.getTypeface();
                displayTextView.setTypeface(currentTypeface, style.getTypefaceValue());
            }
        });

        // 觀察錯誤訊息
        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 處理性別選擇變化
     *
     * @param checkedId 選中的 RadioButton ID
     */
    private void onGenderChanged(int checkedId) {
        String name = nameEditText.getText().toString();
        viewModel.setUserName(name);

        UserInfo.Gender gender;
        if (checkedId == R.id.maleRadioButton) {
            gender = UserInfo.Gender.MALE;
        } else if (checkedId == R.id.femaleRadioButton) {
            gender = UserInfo.Gender.FEMALE;
        } else {
            gender = UserInfo.Gender.UNSPECIFIED;
        }

        viewModel.setUserGender(gender);
    }

    /**
     * 處理字體樣式變化
     *
     * @param checkedId 選中的 RadioButton ID
     */
    private void onFontStyleChanged(int checkedId) {
        FontStyle.Style style;

        if (checkedId == R.id.boldRadioButton) {
            style = FontStyle.Style.BOLD;
        } else if (checkedId == R.id.italicRadioButton) {
            style = FontStyle.Style.ITALIC;
        } else if (checkedId == R.id.boldItalicRadioButton) {
            style = FontStyle.Style.BOLD_ITALIC;
        } else {
            style = FontStyle.Style.NORMAL;
        }

        viewModel.setFontStyle(style);
    }
}