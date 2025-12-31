package com.liangtinglin.n913410014_w13;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w13.viewmodel.MainViewModel;

/**
 * 主畫面 Activity
 * View 層 - 只負責 UI 顯示和使用者互動
 * 所有業務邏輯都委託給 ViewModel 處理
 */
public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener,
        RatingBar.OnRatingBarChangeListener,
        View.OnClickListener,
        NumberPicker.OnValueChangeListener,
        SeekBar.OnSeekBarChangeListener {

    // ViewModel
    private MainViewModel viewModel;

    // UI 元件
    private TextView rr;
    private EditText ed, edR, edG, edB;
    private RadioGroup rg;
    private ToggleButton tg;
    private RatingBar rb;
    private Button b1, b2;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch sw;
    private NumberPicker np;
    private SeekBar sb, s_red, s_green, s_blue;

    // 防止 LiveData 觸發時的循環更新
    private boolean isUpdatingFromViewModel = false;

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
        rr = findViewById(R.id.res);

        rg = findViewById(R.id.rg1);
        tg = findViewById(R.id.tg);
        sw = findViewById(R.id.sw);
        rb = findViewById(R.id.rBar);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);

        ed = findViewById(R.id.ed_n);
        edR = findViewById(R.id.edR);
        edG = findViewById(R.id.edG);
        edB = findViewById(R.id.edB);

        np = findViewById(R.id.np);

        sb = findViewById(R.id.seekBar);
        s_red = findViewById(R.id.s_red);
        s_green = findViewById(R.id.s_green);
        s_blue = findViewById(R.id.s_blue);

        // 設定 NumberPicker 範圍
        np.setMinValue(0);
        np.setMaxValue(6);
        np.setValue(1);
        np.setWrapSelectorWheel(false);
    }

    /**
     * 設定所有監聯器
     */
    private void setupListeners() {
        sw.setOnCheckedChangeListener(this);
        rg.setOnCheckedChangeListener(this);
        tg.setOnCheckedChangeListener(this);
        rb.setOnRatingBarChangeListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        sb.setOnSeekBarChangeListener(this);
        s_red.setOnSeekBarChangeListener(this);
        s_green.setOnSeekBarChangeListener(this);
        s_blue.setOnSeekBarChangeListener(this);

        np.setOnValueChangedListener(this);
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察結果文字
        viewModel.getResultText().observe(this, result -> {
            if (result != null && !result.isEmpty()) {
                rr.setText(result);
            }
        });

        // 觀察錯誤訊息
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        // 觀察背景顏色
        viewModel.getBackgroundColor().observe(this, color -> {
            if (color != null) {
                rr.setBackgroundColor(color);
                rr.setTextColor(Color.WHITE);
            }
        });

        // 觀察評分
        viewModel.getRating().observe(this, rating -> {
            if (rating != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                rb.setRating(rating);
                ed.setText(String.valueOf(rating));
                isUpdatingFromViewModel = false;
            }
        });

        // 觀察 NumberPicker
        viewModel.getNumberPickerValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                np.setValue(value);
                isUpdatingFromViewModel = false;
            }
        });

        // 觀察評分 SeekBar
        viewModel.getSeekBarProgress().observe(this, progress -> {
            if (progress != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                sb.setProgress(progress);
                isUpdatingFromViewModel = false;
            }
        });

        // 觀察顏色值
        viewModel.getRedValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                s_red.setProgress(value);
                edR.setText(String.valueOf(value));
                isUpdatingFromViewModel = false;
            }
        });

        viewModel.getGreenValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                s_green.setProgress(value);
                edG.setText(String.valueOf(value));
                isUpdatingFromViewModel = false;
            }
        });

        viewModel.getBlueValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                s_blue.setProgress(value);
                edB.setText(String.valueOf(value));
                isUpdatingFromViewModel = false;
            }
        });

        // 觀察性別
        viewModel.getIsMale().observe(this, isMale -> {
            if (isMale != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                tg.setChecked(isMale);
                sw.setChecked(isMale);
                RadioButton radioButton = findViewById(isMale ? R.id.ra_man : R.id.ra_wom);
                radioButton.setChecked(true);
                isUpdatingFromViewModel = false;
            }
        });
    }

    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
        if (isUpdatingFromViewModel) return;
        
        String name = ed.getText().toString();

        if (buttonView == tg || buttonView == sw) {
            viewModel.handleGenderChange(name, isChecked);
        }
    }

    @Override
    public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
        if (isUpdatingFromViewModel) return;
        
        String name = ed.getText().toString();
        boolean isMale = (checkedId == R.id.ra_man);
        viewModel.handleGenderChange(name, isMale);
    }

    @Override
    public void onClick(View v) {
        if (v == b1) {
            // 處理評分輸入
            String input = ed.getText().toString();
            if (!input.isEmpty()) {
                viewModel.setRatingFromInput(input);
            }
        }

        if (v == b2) {
            // 處理顏色輸入
            String inputR = edR.getText().toString();
            String inputG = edG.getText().toString();
            String inputB = edB.getText().toString();

            if (!inputR.isEmpty() && !inputG.isEmpty() && !inputB.isEmpty()) {
                viewModel.setColorFromInputs(inputR, inputG, inputB);
                Toast.makeText(this, "設定成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (fromUser && !isUpdatingFromViewModel) {
            viewModel.setRatingFromRatingBar(rating);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (!isUpdatingFromViewModel) {
            viewModel.setRatingFromNumberPicker(newVal);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser || isUpdatingFromViewModel) return;

        if (seekBar == sb) {
            viewModel.setRatingFromSeekBar(progress);
        } else if (seekBar == s_red) {
            viewModel.setRedFromSeekBar(progress);
        } else if (seekBar == s_green) {
            viewModel.setGreenFromSeekBar(progress);
        } else if (seekBar == s_blue) {
            viewModel.setBlueFromSeekBar(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}