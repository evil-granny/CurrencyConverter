package com.example.denys.currencyconverter.interactors;

import android.content.Context;

import com.example.denys.currencyconverter.repos.IRepoCurrency;
import com.example.denys.currencyconverter.repos.RepoCurrency;

public class Converter implements IConverter {
    private IRepoCurrency repoCurrency;

    public Converter(Context context) {
        repoCurrency = new RepoCurrency(context);
    }

    public float convert(String from, String to, float value) {
        float fromValue = repoCurrency.get(from);
        float toValue = repoCurrency.get(to);

        return (fromValue / toValue) * value;
    }
}
