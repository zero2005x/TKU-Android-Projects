package com.liangtinglin.n913410014_w07.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w07.R;
import com.liangtinglin.n913410014_w07.viewmodel.MainViewModel;

import java.text.MessageFormat;

/**
 * MainActivity - 純粹的 View 層
 * 職責：文字大小調整和觸控事件的 UI 互動
 */
public class MainActivity extends AppCompatActivity {

    // ViewModel
    private MainViewModel viewModel;

    // UI Elements
    private Button enlargeButton;
    private Button shrinkButton;
    private TextView resultTextView;
    private View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupWindowInsets();
        initViewModel();
        initViews();
        setupListeners();
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
        resultTextView = findViewById(R.id.resultText);
        enlargeButton = findViewById(R.id.enlargeButton);
        shrinkButton = findViewById(R.id.shrinkButton);
        mainLayout = findViewById(R.id.main);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupListeners() {
        // 單擊事件
        enlargeButton.setOnClickListener(v -> viewModel.enlargeText());
        shrinkButton.setOnClickListener(v -> viewModel.shrinkText());

        // 長按事件
        enlargeButton.setOnLongClickListener(v -> {
            viewModel.enlargeTextFast();
            return true; // 消耗事件，避免觸發 onClick
        });

        shrinkButton.setOnLongClickListener(v -> {
            viewModel.shrinkTextFast();
            return true;
        });

        resultTextView.setOnLongClickListener(v -> {
            viewModel.resetTextSize();
            return true;
        });

        // 觸控事件
        mainLayout.setOnTouchListener((v, event) -> {
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    viewModel.handleTouchEvent("按下", x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    viewModel.handleTouchEvent("彈開", x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    viewModel.handleTouchEvent("移動", x, y);
                    break;
            }
            return true;
        });
    }

    private void observeViewModel() {
        // 觀察文字大小狀態
        viewModel.getTextSizeState().observe(this, textSizeState -> {
            if (textSizeState != null) {
                int size = textSizeState.getSize();
                resultTextView.setTextSize(size);
                String text = MessageFormat.format("{0} {1}", 
                    getString(R.string.resultText), size);
                resultTextView.setText(text);
            }
        });

        // 觀察觸控事件訊息
        viewModel.getTouchEventMessage().observe(this, message -> {
            if (message != null) {
                resultTextView.setText(message);
            }
        });

        // 觀察警告訊息
        viewModel.getWarningMessage().observe(this, warning -> {
            if (warning != null) {
                Toast.makeText(this, warning, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
