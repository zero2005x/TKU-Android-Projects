package com.liangtinglin.n913410014_w05.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w05.R;
import com.liangtinglin.n913410014_w05.viewmodel.MainViewModel;

import java.text.MessageFormat;

/**
 * MainActivity - 純粹的 View 層
 * 職責：
 * 1. 初始化 UI 元件
 * 2. 監聽使用者操作
 * 3. 觀察 ViewModel 的數據變化並更新 UI
 * 4. 不包含任何業務邏輯
 */
public class MainActivity extends AppCompatActivity {

    // ViewModel
    private MainViewModel viewModel;

    // UI Elements
    private EditText heightInput;
    private EditText weightInput;
    private TextView resultTextView;
    private Button calculateButton;
    private LinearLayout dynamicButtonContainer;

    // Static button IDs
    private final int[] staticButtonIds = {
            R.id.button_1, R.id.button_2, R.id.button_3, 
            R.id.button_4, R.id.button_5
    };

    // Dynamic buttons
    private Button[] dynamicButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        setupWindowInsets();
        initViewModel();
        initViews();
        setupStaticButtons();
        setupDynamicButtons();
        observeViewModel();
    }

    /**
     * 設定 Window Insets
     */
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 初始化 ViewModel
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    /**
     * 初始化 UI 元件
     */
    private void initViews() {
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        resultTextView = findViewById(R.id.resultTextView);
        calculateButton = findViewById(R.id.calculateButton);
        dynamicButtonContainer = findViewById(R.id.dynamicButtonContainer);

        // 設定計算按鈕的點擊事件
        calculateButton.setOnClickListener(v -> {
            String height = heightInput.getText().toString();
            String weight = weightInput.getText().toString();
            viewModel.calculateBmi(height, weight);
        });
    }

    /**
     * 設定靜態按鈕（Button 1-5）
     */
    private void setupStaticButtons() {
        for (int id : staticButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                Button clickedButton = (Button) v;
                viewModel.onStaticButtonClicked(clickedButton.getText().toString());
            });
        }
    }

    /**
     * 動態生成 20 個按鈕
     */
    private void setupDynamicButtons() {
        dynamicButtons = new Button[20];
        for (int i = 0; i < 20; i++) {
            dynamicButtons[i] = new Button(this);
            dynamicButtons[i].setText(MessageFormat.format("動態按鈕 {0}", (i + 1)));
            
            final int index = i;
            dynamicButtons[i].setOnClickListener(v -> {
                viewModel.onDynamicButtonClicked(dynamicButtons[index].getText().toString());
            });
            
            dynamicButtonContainer.addView(dynamicButtons[i]);
        }
    }

    /**
     * 觀察 ViewModel 的數據變化
     */
    private void observeViewModel() {
        // 觀察 BMI 計算結果
        viewModel.getBmiResult().observe(this, bmiResult -> {
            if (bmiResult != null) {
                resultTextView.setText(bmiResult.getFormattedResult());
            }
        });

        // 觀察按鈕點擊訊息
        viewModel.getButtonClickMessage().observe(this, message -> {
            if (message != null) {
                resultTextView.setText(message);
            }
        });

        // 觀察錯誤訊息（顯示 Toast）
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
