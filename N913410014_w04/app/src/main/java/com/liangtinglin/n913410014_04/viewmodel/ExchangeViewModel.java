package com.liangtinglin.n913410014_04.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liangtinglin.n913410014_04.model.ExchangeRate;

import java.util.Locale;

/**
 * 匯率計算 ViewModel
 */
public class ExchangeViewModel extends ViewModel {

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 美元轉台幣
     */
    public String convertUsdToNtd(String rateStr, String usdStr) {
        try {
            double rate = Double.parseDouble(rateStr);
            double usd = Double.parseDouble(usdStr);

            ExchangeRate exchangeRate = new ExchangeRate(rate);
            if (!exchangeRate.isValid()) {
                errorMessage.setValue("匯率必須大於零");
                return "";
            }

            double result = exchangeRate.usdToNtd(usd);
            return String.format(Locale.getDefault(), "%.2f", result);

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入有效的數字");
            return "";
        }
    }

    /**
     * 台幣轉美元
     */
    public String convertNtdToUsd(String rateStr, String ntdStr) {
        try {
            double rate = Double.parseDouble(rateStr);
            double ntd = Double.parseDouble(ntdStr);

            ExchangeRate exchangeRate = new ExchangeRate(rate);
            if (!exchangeRate.isValid()) {
                errorMessage.setValue("匯率必須大於零");
                return "";
            }

            double result = exchangeRate.ntdToUsd(ntd);
            return String.format(Locale.getDefault(), "%.2f", result);

        } catch (NumberFormatException e) {
            errorMessage.setValue("請輸入有效的數字");
            return "";
        } catch (ArithmeticException e) {
            errorMessage.setValue(e.getMessage());
            return "";
        }
    }

    /**
     * 驗證輸入是否為空
     */
    public boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
