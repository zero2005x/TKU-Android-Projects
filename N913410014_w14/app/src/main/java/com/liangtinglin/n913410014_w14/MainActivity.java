package com.liangtinglin.n913410014_w14;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liangtinglin.n913410014_w14.viewmodel.MainViewModel;

/**
 * 主畫面 Activity
 * View 層 - 只負責 UI 顯示和使用者互動
 * 所有業務邏輯都委託給 ViewModel 處理
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    // ViewModel
    private MainViewModel viewModel;

    // UI 元件
    private SeekBar sr, sg, sb;
    private Button b1, b2, b3, b4, b5;
    private EditText er, eg, eb, edWeb;
    private TextView rr, tr, tg, tb;
    private ProgressBar pb;
    private WebView wv1, wv2;
    private gem vi;

    // 防止循環更新
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
        sr = findViewById(R.id.s_red);
        sg = findViewById(R.id.s_green);
        sb = findViewById(R.id.s_blue);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);

        eg = findViewById(R.id.ed_green);
        er = findViewById(R.id.ed_red);
        eb = findViewById(R.id.ed_blue);

        rr = findViewById(R.id.res);

        tr = findViewById(R.id.tv_r);
        tg = findViewById(R.id.tv_g);
        tb = findViewById(R.id.tv_b);

        pb = findViewById(R.id.pb);

        wv1 = findViewById(R.id.wv1);
        wv2 = findViewById(R.id.wv2);

        vi = findViewById(R.id.def);

        edWeb = findViewById(R.id.ed_web);

        pb.setVisibility(View.INVISIBLE);

        // 設定 WebView
        wv2.setWebViewClient(new WebViewClient());
        wv2.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * 設定所有監聽器
     */
    private void setupListeners() {
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);

        sr.setOnSeekBarChangeListener(this);
        sg.setOnSeekBarChangeListener(this);
        sb.setOnSeekBarChangeListener(this);
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察顏色值
        viewModel.getRedValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                sr.setProgress(value);
                er.setText(String.valueOf(value));
                tr.setText("紅色：" + value);
                tr.setBackgroundColor(Color.rgb(value, 0, 0));
                tr.setTextColor(Color.WHITE);
                isUpdatingFromViewModel = false;
            }
        });

        viewModel.getGreenValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                sg.setProgress(value);
                eg.setText(String.valueOf(value));
                tg.setText("綠色：" + value);
                tg.setBackgroundColor(Color.rgb(0, value, 0));
                tg.setTextColor(Color.WHITE);
                isUpdatingFromViewModel = false;
            }
        });

        viewModel.getBlueValue().observe(this, value -> {
            if (value != null && !isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true;
                sb.setProgress(value);
                eb.setText(String.valueOf(value));
                tb.setText("藍色：" + value);
                tb.setBackgroundColor(Color.rgb(0, 0, value));
                tb.setTextColor(Color.WHITE);
                isUpdatingFromViewModel = false;
            }
        });

        // 觀察背景顏色
        viewModel.getBackgroundColor().observe(this, color -> {
            if (color != null) {
                rr.setBackgroundColor(color);
                rr.setTextColor(Color.WHITE);
            }
        });

        // 觀察顏色結果文字
        viewModel.getColorResultText().observe(this, text -> {
            if (text != null) {
                rr.setText(text);
            }
        });

        // 觀察進度
        viewModel.getProgress().observe(this, progress -> {
            if (progress != null) {
                pb.setProgress(progress);
            }
        });

        // 觀察進度條可見性
        viewModel.getProgressBarVisible().observe(this, visible -> {
            if (visible != null) {
                pb.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            }
        });

        // 觀察進度按鈕可用性
        viewModel.getProgressButtonEnabled().observe(this, enabled -> {
            if (enabled != null) {
                b3.setEnabled(enabled);
            }
        });

        // 觀察錯誤訊息
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        // 觀察 Toast 訊息
        viewModel.getToastMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // 觀察寶石更新
        viewModel.getGemInvalidate().observe(this, invalidate -> {
            if (invalidate != null && invalidate) {
                vi.invalidate();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.b5) {
            // 更新寶石顏色
            viewModel.invalidateGem();
        }

        if (id == R.id.b4) {
            // 載入網頁
            String url = edWeb.getText().toString().trim();
            if (viewModel.isValidUrl(url)) {
                wv1.setWebViewClient(new WebViewClient());
                wv1.loadData(url, "text/html; charset=utf-8", null);
                wv2.loadUrl(url);
                Toast.makeText(this, "正在開啟: " + url, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "請輸入網址", Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.b3) {
            // 開始進度任務
            String currentText = rr.getText().toString();
            viewModel.startProgressTask(currentText);
        }

        if (id == R.id.b2) {
            // 隨機顏色
            viewModel.generateRandomColor();
        }

        if (id == R.id.b1) {
            // 從輸入框設定顏色
            String redInput = er.getText().toString();
            String greenInput = eg.getText().toString();
            String blueInput = eb.getText().toString();
            viewModel.setColorFromInputs(redInput, greenInput, blueInput);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser || isUpdatingFromViewModel) return;

        int id = seekBar.getId();

        if (id == R.id.s_red) {
            viewModel.setRedFromSeekBar(progress);
        } else if (id == R.id.s_green) {
            viewModel.setGreenFromSeekBar(progress);
        } else if (id == R.id.s_blue) {
            viewModel.setBlueFromSeekBar(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}