package com.liangtinglin.n913410014_w13.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_w13.model.RatingData;
import com.liangtinglin.n913410014_w13.model.RgbColor;
import com.liangtinglin.n913410014_w13.model.UserProfile;

/**
 * 主畫面 ViewModel
 * ViewModel 層 - 負責管理 UI 相關數據和業務邏輯
 */
public class MainViewModel extends ViewModel {

    // Model 物件
    private final UserProfile userProfile = new UserProfile();
    private final RatingData ratingData = new RatingData();
    private final RgbColor rgbColor = new RgbColor();

    // LiveData
    private final MutableLiveData<String> resultText = new MutableLiveData<>("");
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Integer> backgroundColor = new MutableLiveData<>();
    
    // 評分相關 LiveData
    private final MutableLiveData<Float> rating = new MutableLiveData<>(1f);
    private final MutableLiveData<Integer> numberPickerValue = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> seekBarProgress = new MutableLiveData<>(1);

    // 顏色相關 LiveData
    private final MutableLiveData<Integer> redValue = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> greenValue = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> blueValue = new MutableLiveData<>(0);

    // 性別相關 LiveData
    private final MutableLiveData<Boolean> isMale = new MutableLiveData<>(true);

    public MainViewModel() {
        // 初始化
    }

    // ========== Getters for LiveData ==========

    public LiveData<String> getResultText() {
        return resultText;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Integer> getBackgroundColor() {
        return backgroundColor;
    }

    public LiveData<Float> getRating() {
        return rating;
    }

    public LiveData<Integer> getNumberPickerValue() {
        return numberPickerValue;
    }

    public LiveData<Integer> getSeekBarProgress() {
        return seekBarProgress;
    }

    public LiveData<Integer> getRedValue() {
        return redValue;
    }

    public LiveData<Integer> getGreenValue() {
        return greenValue;
    }

    public LiveData<Integer> getBlueValue() {
        return blueValue;
    }

    public LiveData<Boolean> getIsMale() {
        return isMale;
    }

    // ========== 使用者相關方法 ==========

    /**
     * 設定使用者性別
     */
    public void setGender(boolean isMale) {
        userProfile.setGender(isMale ? UserProfile.Gender.MALE : UserProfile.Gender.FEMALE);
        this.isMale.setValue(isMale);
    }

    /**
     * 更新問候語
     */
    public void updateGreeting(String name) {
        userProfile.setName(name);
        if (userProfile.isNameValid()) {
            resultText.setValue(userProfile.getGreeting());
        } else {
            errorMessage.setValue("有欄位沒有輸!");
        }
    }

    /**
     * 處理性別切換
     */
    public void handleGenderChange(String name, boolean isMale) {
        if (name != null && !name.isEmpty()) {
            setGender(isMale);
            userProfile.setName(name);
            resultText.setValue(userProfile.getGreeting());
        } else {
            errorMessage.setValue("有欄位沒有輸!");
        }
    }

    // ========== 評分相關方法 ==========

    /**
     * 從輸入框設定評分
     */
    public void setRatingFromInput(String input) {
        try {
            float value = Float.parseFloat(input);
            int intValue = (int) value;
            
            ratingData.setRating(value);
            rating.setValue(value);
            
            if (intValue >= 0 && intValue <= 6) {
                numberPickerValue.setValue(intValue);
                seekBarProgress.setValue(intValue);
            }
            
            resultText.setValue(ratingData.getFormattedResult());
        } catch (NumberFormatException e) {
            errorMessage.setValue("輸入格式錯誤");
        }
    }

    /**
     * 從 RatingBar 設定評分
     */
    public void setRatingFromRatingBar(float value) {
        ratingData.setRating(value);
        rating.setValue(value);
        numberPickerValue.setValue((int) value);
        seekBarProgress.setValue((int) value);
        resultText.setValue(ratingData.getFormattedResult());
    }

    /**
     * 從 NumberPicker 設定評分
     */
    public void setRatingFromNumberPicker(int value) {
        ratingData.setRating(value);
        rating.setValue((float) value);
        seekBarProgress.setValue(value);
        resultText.setValue(ratingData.getFormattedResult());
    }

    /**
     * 從 SeekBar 設定評分
     */
    public void setRatingFromSeekBar(int value) {
        ratingData.setRating(value);
        rating.setValue((float) value);
        numberPickerValue.setValue(value);
        resultText.setValue(ratingData.getFormattedResult());
    }

    // ========== 顏色相關方法 ==========

    /**
     * 從輸入框設定顏色
     */
    public void setColorFromInputs(String redInput, String greenInput, String blueInput) {
        try {
            int r = RgbColor.parseValue(redInput);
            int g = RgbColor.parseValue(greenInput);
            int b = RgbColor.parseValue(blueInput);

            // 限制範圍
            if (r > 255) r = 255;
            if (g > 255) g = 255;
            if (b > 255) b = 255;

            rgbColor.setRed(r);
            rgbColor.setGreen(g);
            rgbColor.setBlue(b);

            redValue.setValue(r);
            greenValue.setValue(g);
            blueValue.setValue(b);

            backgroundColor.setValue(rgbColor.getColor());
            resultText.setValue(rgbColor.getFormattedResult());

        } catch (NumberFormatException e) {
            errorMessage.setValue("輸入格式錯誤");
        }
    }

    /**
     * 從 SeekBar 設定紅色
     */
    public void setRedFromSeekBar(int value) {
        rgbColor.setRed(value);
        redValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 從 SeekBar 設定綠色
     */
    public void setGreenFromSeekBar(int value) {
        rgbColor.setGreen(value);
        greenValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 從 SeekBar 設定藍色
     */
    public void setBlueFromSeekBar(int value) {
        rgbColor.setBlue(value);
        blueValue.setValue(value);
        updateColorDisplay();
    }

    /**
     * 更新顏色顯示
     */
    private void updateColorDisplay() {
        backgroundColor.setValue(rgbColor.getColor());
        resultText.setValue(rgbColor.getFormattedResult());
    }

    /**
     * 取得當前的評分資料
     */
    public RatingData getRatingData() {
        return ratingData;
    }

    /**
     * 取得當前的顏色資料
     */
    public RgbColor getRgbColor() {
        return rgbColor;
    }
}
