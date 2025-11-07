package com.liangtinglin.n913410014_w03;

import android.os.Bundle;
import android.text.util.Linkify;
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

import com.liangtinglin.n913410014_w03.model.Contact;
import com.liangtinglin.n913410014_w03.viewmodel.ContactViewModel;

/**
 * 主 Activity (MVVM - View 層)
 * <p>
 * 功能：顯示聯絡人資訊（三個不同佈局區塊）
 * - 區塊 1: LinearLayout 動態生成 (L1)
 * - 區塊 2: RelativeLayout 靜態宣告 (t5-t8)
 * - 區塊 3: LinearLayout 靜態宣告 (t9-t12)
 */
public class MainActivity extends AppCompatActivity {

    // ViewModel
    private ContactViewModel viewModel;

    // UI 元件 - 輸入區
    private EditText nameEditText;
    private Button confirmButton;
    private Button exitButton;

    // UI 元件 - 區塊 1 (動態生成)
    private LinearLayout dynamicLayout;
    private TextView dynamicNameTextView;
    private TextView dynamicTelTextView;
    private TextView dynamicEmailTextView;
    private TextView dynamicWebTextView;

    // UI 元件 - 區塊 2 (RelativeLayout)
    private TextView relativeNameTextView;
    private TextView relativeTelTextView;
    private TextView relativeEmailTextView;
    private TextView relativeWebTextView;

    // UI 元件 - 區塊 3 (LinearLayout)
    private TextView linearNameTextView;
    private TextView linearTelTextView;
    private TextView linearEmailTextView;
    private TextView linearWebTextView;

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
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        // 綁定 UI 元件
        setupViews();

        // 設定區塊 1 (動態生成)
        setupDynamicLayout();

        // 設定按鈕監聽器
        setupListeners();

        // 觀察 ViewModel 資料變化
        observeViewModel();
    }

    /**
     * 綁定 UI 元件
     */
    private void setupViews() {
        // 輸入區
        nameEditText = findViewById(R.id.editTextText);
        confirmButton = findViewById(R.id.button1);
        exitButton = findViewById(R.id.button2);

        // 區塊 1 (動態生成的容器)
        dynamicLayout = findViewById(R.id.L1);

        // 區塊 2 (RelativeLayout)
        relativeNameTextView = findViewById(R.id.t5);
        relativeTelTextView = findViewById(R.id.t6);
        relativeEmailTextView = findViewById(R.id.t7);
        relativeWebTextView = findViewById(R.id.t8);

        // 區塊 3 (LinearLayout)
        linearNameTextView = findViewById(R.id.t9);
        linearTelTextView = findViewById(R.id.t10);
        linearEmailTextView = findViewById(R.id.t11);
        linearWebTextView = findViewById(R.id.t12);
    }

    /**
     * 設定區塊 1 (動態生成 TextView)
     */
    private void setupDynamicLayout() {
        // 建立 4 個 TextView
        dynamicNameTextView = new TextView(this);
        dynamicTelTextView = new TextView(this);
        dynamicEmailTextView = new TextView(this);
        dynamicWebTextView = new TextView(this);

        // 設定自動連結
        dynamicTelTextView.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        dynamicEmailTextView.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        dynamicWebTextView.setAutoLinkMask(Linkify.WEB_URLS);

        // 設定字型大小
        dynamicNameTextView.setTextSize(25);
        dynamicTelTextView.setTextSize(25);
        dynamicEmailTextView.setTextSize(25);
        dynamicWebTextView.setTextSize(25);

        // 加入到 LinearLayout
        dynamicLayout.addView(dynamicNameTextView);
        dynamicLayout.addView(dynamicTelTextView);
        dynamicLayout.addView(dynamicEmailTextView);
        dynamicLayout.addView(dynamicWebTextView);
    }

    /**
     * 設定按鈕監聽器
     */
    private void setupListeners() {
        confirmButton.setOnClickListener(v -> {
            String inputName = nameEditText.getText().toString();
            viewModel.updateContact(inputName);
        });

        exitButton.setOnClickListener(v -> finish());
    }

    /**
     * 觀察 ViewModel 的 LiveData
     */
    private void observeViewModel() {
        // 觀察聯絡人資料變化
        viewModel.getCurrentContact().observe(this, contact -> {
            if (contact != null) {
                updateAllBlocks(contact);
            }
        });

        // 觀察 Toast 訊息
        viewModel.getToastMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更新所有三個區塊的 UI
     */
    private void updateAllBlocks(Contact contact) {
        // 區塊 1 (動態生成)
        updateBlock(
                dynamicNameTextView,
                dynamicTelTextView,
                dynamicEmailTextView,
                dynamicWebTextView,
                contact
        );

        // 區塊 2 (RelativeLayout)
        updateBlock(
                relativeNameTextView,
                relativeTelTextView,
                relativeEmailTextView,
                relativeWebTextView,
                contact
        );

        // 區塊 3 (LinearLayout)
        updateBlock(
                linearNameTextView,
                linearTelTextView,
                linearEmailTextView,
                linearWebTextView,
                contact
        );
    }

    /**
     * 更新單一區塊的 TextView（消除重複程式碼）
     */
    private void updateBlock(
            TextView nameView,
            TextView telView,
            TextView emailView,
            TextView webView,
            Contact contact
    ) {
        nameView.setText(viewModel.formatContactField(
                getString(R.string.name), contact.getName()));

        telView.setText(viewModel.formatContactField(
                getString(R.string.tel), contact.getTelephone()));

        emailView.setText(viewModel.formatContactField(
                getString(R.string.mail), contact.getEmail()));

        webView.setText(viewModel.formatContactField(
                getString(R.string.web), contact.getWebsite()));
    }
}