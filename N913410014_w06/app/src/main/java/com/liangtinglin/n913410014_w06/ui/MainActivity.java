package com.liangtinglin.n913410014_w06.ui;

import android.graphics.Color;
import android.os.Bundle;
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

import com.liangtinglin.n913410014_w06.R;
import com.liangtinglin.n913410014_w06.model.ColorState;
import com.liangtinglin.n913410014_w06.viewmodel.MainViewModel;

import java.text.MessageFormat;

/**
 * MainActivity - 純粹的 View 層
 * 職責：RGB 顏色選擇器的 UI 互動
 */
public class MainActivity extends AppCompatActivity {

    // ViewModel
    private MainViewModel viewModel;

    // UI Elements
    private TextView resultTextView;
    private TextView redTextView;
    private TextView greenTextView;
    private TextView blueTextView;
    private EditText redInput;
    private EditText greenInput;
    private EditText blueInput;
    private Button autoChangeButton;
    private Button manualChangeButton;
    private LinearLayout dynamicButtonContainer;

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
        setupDynamicButtons();
        observeViewModel();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void initViews() {
        resultTextView = findViewById(R.id.resultTextView);
        redTextView = findViewById(R.id.redTextView);
        greenTextView = findViewById(R.id.greenTextView);
        blueTextView = findViewById(R.id.blueTextView);
        redInput = findViewById(R.id.redInput);
        greenInput = findViewById(R.id.greenInput);
        blueInput = findViewById(R.id.blueInput);
        autoChangeButton = findViewById(R.id.autoChangeButton);
        manualChangeButton = findViewById(R.id.manualChangeButton);
        dynamicButtonContainer = findViewById(R.id.dynamicButtonContainer);

        // 設定按鈕點擊事件
        autoChangeButton.setOnClickListener(v -> viewModel.generateRandomColor());

        manualChangeButton.setOnClickListener(v -> {
            String red = redInput.getText().toString();
            String green = greenInput.getText().toString();
            String blue = blueInput.getText().toString();
            viewModel.setManualColor(red, green, blue);
        });
    }

    private void setupDynamicButtons() {
        dynamicButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            dynamicButtons[i] = new Button(this);
            dynamicButtons[i].setText(MessageFormat.format("Button{0}", i));

            final int index = i;
            dynamicButtons[i].setOnClickListener(v -> {
                viewModel.onDynamicButtonClicked(dynamicButtons[index].getText().toString());
            });

            dynamicButtonContainer.addView(dynamicButtons[i]);
        }
    }

    private void observeViewModel() {
        // 觀察顏色狀態變化
        viewModel.getColorState().observe(this, colorState -> {
            if (colorState != null) {
                updateColorUI(colorState);
            }
        });

        // 觀察按鈕點擊訊息
        viewModel.getButtonClickMessage().observe(this, message -> {
            if (message != null) {
                resultTextView.setText(message);
            }
        });

        // 觀察錯誤訊息
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                resetColorUI();
            }
        });
    }

    /**
     * 更新所有顏色相關的 UI
     */
    private void updateColorUI(ColorState colorState) {
        int r = colorState.getRed();
        int g = colorState.getGreen();
        int b = colorState.getBlue();

        // 更新 EditText
        redInput.setText(String.valueOf(r));
        greenInput.setText(String.valueOf(g));
        blueInput.setText(String.valueOf(b));

        // 更新 Red TextView
        redTextView.setText(MessageFormat.format("{0}{1}", getString(R.string.red), r));
        redTextView.setBackgroundColor(Color.rgb(r, 0, 0));
        redTextView.setTextColor(Color.WHITE);

        // 更新 Green TextView
        greenTextView.setText(MessageFormat.format("{0}{1}", getString(R.string.green), g));
        greenTextView.setBackgroundColor(Color.rgb(0, g, 0));
        greenTextView.setTextColor(Color.WHITE);

        // 更新 Blue TextView
        blueTextView.setText(MessageFormat.format("{0}{1}", getString(R.string.blue), b));
        blueTextView.setBackgroundColor(Color.rgb(0, 0, b));
        blueTextView.setTextColor(Color.WHITE);

        // 更新結果 TextView
        String hexColor = colorState.getHexString();
        resultTextView.setText(MessageFormat.format("{0}{1}", getString(R.string.color), hexColor));
        resultTextView.setBackgroundColor(colorState.getColorInt());
        resultTextView.setTextColor(Color.WHITE);
    }

    /**
     * 重置顏色 UI（發生錯誤時）
     */
    private void resetColorUI() {
        resultTextView.setText(getString(R.string.err));
        resultTextView.setBackgroundColor(Color.TRANSPARENT);
        redTextView.setBackgroundColor(Color.TRANSPARENT);
        greenTextView.setBackgroundColor(Color.TRANSPARENT);
        blueTextView.setBackgroundColor(Color.TRANSPARENT);
    }
}
