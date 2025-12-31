package com.liangtinglin.n913410014_w12;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.liangtinglin.n913410014_w12.viewmodel.MainViewModel;

import java.util.Map;

/**
 * 主畫面 Activity
 * View 層 - 只負責 UI 顯示和使用者互動
 * 所有業務邏輯都委託給 ViewModel 處理
 */
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        View.OnLongClickListener,
        View.OnDragListener {

    // ViewModel
    private MainViewModel viewModel;

    // UI 元件
    private Button b1, b2;
    private TextView rr;

    // ID 陣列順序: 0:咖啡, 1:漢堡, 2:薯條, 3:汽水
    private final int[] ch_id = {R.id.ch_c, R.id.ch_h, R.id.ch_f, R.id.ch_s};
    private final int[] im_id = {R.id.im_c, R.id.im_h, R.id.im_f, R.id.im_s};

    private final int[] big = {R.id.ra_cb, R.id.ra_hb, R.id.ra_fb, R.id.ra_sb}; // 大
    private final int[] mid = {R.id.ra_cm, R.id.ra_hm, R.id.ra_fm, R.id.ra_sm}; // 中
    private final int[] sma = {R.id.ra_cs, R.id.ra_hs, R.id.ra_fs, R.id.ra_ss}; // 小

    // Android 圖片 ID 陣列
    private final int[] android_view_id = {R.id.android_1, R.id.android_2, R.id.android_3};

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
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        rr = findViewById(R.id.res);
    }

    /**
     * 設定所有監聽器
     */
    private void setupListeners() {
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        // CheckBox 監聽器
        for (int j : ch_id) {
            CheckBox ch = findViewById(j);
            ch.setOnCheckedChangeListener(this);
        }

        // ImageView 監聽器
        for (int j : im_id) {
            ImageView ig = findViewById(j);
            ig.setOnLongClickListener(this);
            ig.setOnClickListener(this);
        }

        // Android 圖片監聽器
        for (int j : android_view_id) {
            ImageView ig = findViewById(j);
            ig.setOnLongClickListener(this);
            ig.setOnClickListener(this);
            ig.setOnDragListener(this);
        }

        // RadioButton 監聽器 - 設定大小變更
        setupSizeRadioListeners();
    }

    /**
     * 設定大小 RadioButton 監聽器
     */
    private void setupSizeRadioListeners() {
        for (int i = 0; i < 4; i++) {
            final int index = i;
            
            RadioButton rbBig = findViewById(big[i]);
            RadioButton rbMid = findViewById(mid[i]);
            RadioButton rbSma = findViewById(sma[i]);

            rbBig.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.setItemSize(index, 0);
            });
            rbMid.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.setItemSize(index, 1);
            });
            rbSma.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.setItemSize(index, 2);
            });
        }
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察結果文字
        viewModel.getResultText().observe(this, result -> rr.setText(result));

        // 觀察圖片可見性
        viewModel.getImageVisibility().observe(this, visibility -> {
            if (visibility != null) {
                for (Map.Entry<Integer, Boolean> entry : visibility.entrySet()) {
                    int index = entry.getKey();
                    if (index >= 0 && index < im_id.length) {
                        ImageView ig = findViewById(im_id[index]);
                        ig.setVisibility(entry.getValue() ? View.VISIBLE : View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        viewModel.onItemCheckedChanged(buttonView, isChecked);
    }

    @Override
    public void onClick(View v) {
        // 清除按鈕
        if (v == b2) {
            onClearClick();
        }

        // 確定按鈕
        if (v == b1) {
            onConfirmClick();
        }
    }

    /**
     * 確認按鈕點擊處理
     */
    private void onConfirmClick() {
        viewModel.confirmOrder(ch_id, CompoundButton::getId);
    }

    /**
     * 清除按鈕點擊處理
     */
    private void onClearClick() {
        // 重置 UI
        for (int i = 0; i < ch_id.length; i++) {
            // 重置 CheckBox
            CheckBox ch = findViewById(ch_id[i]);
            ch.setChecked(false);

            // 隱藏圖片
            ImageView ig = findViewById(im_id[i]);
            ig.setVisibility(View.GONE);

            // 重置 RadioButton (全部設回大份)
            RadioButton rb = findViewById(big[i]);
            rb.setChecked(true);
        }

        // 通知 ViewModel
        viewModel.clearAll();
    }

    @Override
    public boolean onLongClick(View v) {
        v.startDragAndDrop(null, new View.DragShadowBuilder(v), v, 0);
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        ImageView t = (ImageView) v;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                t.setColorFilter(Color.BLUE);
                viewModel.handleDragEvent(
                    com.liangtinglin.n913410014_w12.model.DragEvent.DragState.STARTED, x, y);
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                t.setColorFilter(Color.RED);
                viewModel.handleDragEvent(
                    com.liangtinglin.n913410014_w12.model.DragEvent.DragState.ENTERED, x, y);
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                t.setColorFilter(Color.GREEN);
                viewModel.handleDragEvent(
                    com.liangtinglin.n913410014_w12.model.DragEvent.DragState.EXITED, x, y);
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                t.clearColorFilter();
                viewModel.handleDragEvent(
                    com.liangtinglin.n913410014_w12.model.DragEvent.DragState.ENDED, x, y);
                break;

            case DragEvent.ACTION_DROP:
                ImageView s = (ImageView) event.getLocalState();
                Drawable img = s.getDrawable();
                s.setImageDrawable(t.getDrawable());
                t.setImageDrawable(img);
                break;
        }

        return true;
    }
}