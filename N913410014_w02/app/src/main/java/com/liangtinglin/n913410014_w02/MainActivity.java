package com.liangtinglin.n913410014_w02;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w02.viewmodel.TextSizeViewModel;

/**
 * 主 Activity (MVVM - View 層)
 * <p>
 * 職責：
 * - 綁定 UI 元件
 * - 觀察 ViewModel 的資料變化並更新 UI
 * - 將使用者操作轉發給 ViewModel
 * <p>
 * 不再包含業務邏輯，所有邏輯都移至 TextSizeViewModel。
 */
public class MainActivity extends AppCompatActivity {

    // UI 元件（使用語意化命名）
    private TextView firstTextView;
    private TextView secondTextView;

    // ViewModel
    private TextSizeViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(TextSizeViewModel.class);

        // 綁定 UI 元件
        setupViews();

        // 觀察 ViewModel 資料變化
        observeViewModel();

        // 設定初始文字
        initializeTexts();
    }

    /**
     * 綁定 UI 元件
     */
    private void setupViews() {
        firstTextView = findViewById(R.id.res);
        secondTextView = findViewById(R.id.Textview);

        // 設定點擊事件（從 XML 的 onClick 移至 Java）
        firstTextView.setOnClickListener(v -> onIncreaseClick());
        secondTextView.setOnClickListener(v -> onDecreaseClick());
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察字型大小變化
        viewModel.getFontSize().observe(this, size -> {
            if (size != null) {
                updateTextViews(size);
            }
        });

        // 觀察第一個文字變化
        viewModel.getFirstText().observe(this, text -> {
            if (text != null) {
                firstTextView.setText(text);
            }
        });

        // 觀察第二個文字變化
        viewModel.getSecondText().observe(this, text -> {
            if (text != null) {
                secondTextView.setText(text);
            }
        });
    }

    /**
     * 設定初始文字
     */
    private void initializeTexts() {
        firstTextView.setText("今天天氣");
        secondTextView.setText("測試");
    }

    /**
     * 處理「放大」點擊事件
     */
    private void onIncreaseClick() {
        viewModel.increaseFontSize();
        // 更新文字顯示當前大小
        viewModel.updateFirstText(
                getString(R.string.prm),
                getString(R.string.size),
                viewModel.getCurrentFontSize()
        );
    }

    /**
     * 處理「縮小」點擊事件
     */
    private void onDecreaseClick() {
        viewModel.decreaseFontSize();
        // 更新文字顯示當前大小
        viewModel.updateSecondText(
                getString(R.string.prm2),
                getString(R.string.size),
                viewModel.getCurrentFontSize()
        );
    }

    /**
     * 更新兩個 TextView 的字型大小
     */
    private void updateTextViews(int size) {
        firstTextView.setTextSize(size);
        secondTextView.setTextSize(size);
    }
}