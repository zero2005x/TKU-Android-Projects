package com.liangtinglin.n913410014_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_04.viewmodel.BmiViewModel;
import com.liangtinglin.n913410014_04.viewmodel.ExchangeViewModel;

import java.util.Locale;

/**
 * 主 Activity (MVVM - View 層)
 * 功能：匯率計算器（兩組） + BMI 計算器
 */
public class MainActivity extends AppCompatActivity {

    // ViewModels
    private ExchangeViewModel exchangeViewModel;
    private BmiViewModel bmiViewModel;

    // UI 元件
    private EditText exchange1, edtUSD1, edtNTD1;
    private Button btnUS2NT1, btnNT2US1, btnClear1, btnExit1;
    private EditText exchange2, edtUSD2, edtNTD2;
    private Button btnUS2NT2, btnNT2US2, btnClear2, btnExit2;
    private EditText edtHeight, edtWeight;
    private Button btnCalculateBmi;

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

        // 綁定 UI 元件
        setupViews();
        // 設定按鈕監聽器
        setupListeners();
    }

    // 將所有 findViewById 集中管理
    private void setupViews() {
        // 初始化 ViewModels
        exchangeViewModel = new ViewModelProvider(this).get(ExchangeViewModel.class);
        bmiViewModel = new ViewModelProvider(this).get(BmiViewModel.class);

        // 第一組 (匯率)
        btnUS2NT1 = findViewById(R.id.btnUS2NT1);
        btnNT2US1 = findViewById(R.id.btnNT2US1);
        btnClear1 = findViewById(R.id.btnClear1);
        btnExit1 = findViewById(R.id.btnExit1);
        exchange1 = findViewById(R.id.exchange1);
        edtUSD1 = findViewById(R.id.usDollorEditText_1);
        edtNTD1 = findViewById(R.id.edtNTD1);

        // 第二組 (匯率)
        btnUS2NT2 = findViewById(R.id.btnUS2NT2);
        btnNT2US2 = findViewById(R.id.btnNT2US2);
        btnClear2 = findViewById(R.id.btnClear2);
        btnExit2 = findViewById(R.id.btnExit2);
        exchange2 = findViewById(R.id.exchange2);
        edtUSD2 = findViewById(R.id.edtUSD2);
        edtNTD2 = findViewById(R.id.edtNTD2);

        // === BMI 計算元件綁定 (新增部分) ===
        edtHeight = findViewById(R.id.heightEditText);
        edtWeight = findViewById(R.id.weightEditText);
        btnCalculateBmi = findViewById(R.id.btnCalculateBmi);
        // ===================================

        // 觀察 ViewModel 的錯誤訊息和結果
        observeViewModels();
    }

    // 設定所有按鈕的點擊事件
    private void setupListeners() {
        // --- 第一組的邏輯 (使用 ViewModel) ---
        btnUS2NT1.setOnClickListener(v -> {
            String rate = exchange1.getText().toString();
            String usd = edtUSD1.getText().toString();
            
            if (!exchangeViewModel.validateInput(rate, usd)) {
                Toast.makeText(this, "請輸入匯率和美金金額", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String result = exchangeViewModel.convertUsdToNtd(rate, usd);
            if (!result.isEmpty()) {
                edtNTD1.setText(result);
            }
        });

        btnNT2US1.setOnClickListener(v -> {
            String rate = exchange1.getText().toString();
            String ntd = edtNTD1.getText().toString();
            
            if (!exchangeViewModel.validateInput(rate, ntd)) {
                Toast.makeText(this, "請輸入匯率和台幣金額", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String result = exchangeViewModel.convertNtdToUsd(rate, ntd);
            if (!result.isEmpty()) {
                edtUSD1.setText(result);
            }
        });

        btnClear1.setOnClickListener(v -> {
            exchange1.setText("");
            edtUSD1.setText("");
            edtNTD1.setText("");
        });

        // --- 第二組的邏輯 (使用 ViewModel) ---
        btnUS2NT2.setOnClickListener(v -> {
            String rate = exchange2.getText().toString();
            String usd = edtUSD2.getText().toString();
            
            if (!exchangeViewModel.validateInput(rate, usd)) {
                Toast.makeText(this, "請輸入匯率和美金金額", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String result = exchangeViewModel.convertUsdToNtd(rate, usd);
            if (!result.isEmpty()) {
                edtNTD2.setText(result);
            }
        });

        btnNT2US2.setOnClickListener(v -> {
            String rate = exchange2.getText().toString();
            String ntd = edtNTD2.getText().toString();
            
            if (!exchangeViewModel.validateInput(rate, ntd)) {
                Toast.makeText(this, "請輸入匯率和台幣金額", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String result = exchangeViewModel.convertNtdToUsd(rate, ntd);
            if (!result.isEmpty()) {
                edtUSD2.setText(result);
            }
        });

        btnClear2.setOnClickListener(v -> {
            exchange2.setText("");
            edtUSD2.setText("");
            edtNTD2.setText("");
        });

        // --- 離開按鈕的邏輯 ---
        View.OnClickListener exitListener = v -> finish();
        btnExit1.setOnClickListener(exitListener);
        btnExit2.setOnClickListener(exitListener);

        // === BMI 計算按鈕監聽器 (使用 ViewModel) ===
        btnCalculateBmi.setOnClickListener(v -> {
            String height = edtHeight.getText().toString();
            String weight = edtWeight.getText().toString();
            bmiViewModel.calculateBmi(height, weight);
        });
        // ===================================
    }

    // 觀察 ViewModel 的 LiveData
    private void observeViewModels() {
        // 觀察匯率計算的錯誤訊息
        exchangeViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        // 觀察 BMI 計算結果
        bmiViewModel.getBmiResult().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, result.format(), Toast.LENGTH_LONG).show();
            }
        });

        // 觀察 BMI 計算的錯誤訊息
        bmiViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}