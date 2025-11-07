package com.liangtinglin.n913410014_04.model;

/**
 * 匯率資料模型
 */
public class ExchangeRate {
    private final double rate;

    public ExchangeRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    /**
     * 美元轉台幣
     */
    public double usdToNtd(double usd) {
        return usd * rate;
    }

    /**
     * 台幣轉美元
     */
    public double ntdToUsd(double ntd) {
        if (rate == 0) {
            throw new ArithmeticException("匯率不能為零");
        }
        return ntd / rate;
    }

    /**
     * 驗證匯率是否有效
     */
    public boolean isValid() {
        return rate > 0;
    }
}
