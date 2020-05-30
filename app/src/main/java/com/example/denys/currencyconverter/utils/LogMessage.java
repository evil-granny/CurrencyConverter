package com.example.denys.currencyconverter.utils;

import android.widget.Toast;
import com.example.denys.currencyconverter.CurrencyConverterApp;

public class LogMessage {
    public static void toast(String txt) {
        Toast.makeText(CurrencyConverterApp.getContext(), txt, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int str) {
        Toast.makeText(CurrencyConverterApp.getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
