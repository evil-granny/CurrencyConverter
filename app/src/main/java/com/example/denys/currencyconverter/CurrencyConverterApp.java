package com.example.denys.currencyconverter;

import android.app.Application;

public class CurrencyConverterApp extends Application {
    private static CurrencyConverterApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CurrencyConverterApp getContext() {
        return instance;
    }
}
