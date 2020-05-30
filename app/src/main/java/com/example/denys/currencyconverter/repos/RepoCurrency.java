package com.example.denys.currencyconverter.repos;

import android.content.ContentValues;
import android.content.Context;

import com.example.denys.currencyconverter.model.CurrencyRequest;
import com.example.denys.currencyconverter.model.ValuteGetted;
import com.example.denys.currencyconverter.model.curse_value.Valute;
import com.example.denys.currencyconverter.model.database.CurrencyDB;
import com.example.denys.currencyconverter.utils.LogMessage;

import java.util.HashMap;
import java.util.List;

public class RepoCurrency implements IRepoCurrency, IRepoCallback<Valute> {
    private static HashMap<String, Float> course = new HashMap<>();
    private static IRepoCallbackData callback;
    private CurrencyDB currencyDB;
    private CurrencyRequest currencyRequest;

    public RepoCurrency(Context context) {
        currencyDB = new CurrencyDB(context);

        if (course.isEmpty()) {
            for (ValuteGetted v : currencyDB.getAll()) {
                course.put(v.code, v.value);
            }
        }
    }

    @Override
    public float get(String key) {
        return course.get(key);
    }

    @Override
    public String[] getCurrencies() {
        return currencyDB.getCurrencies();
    }

    @Override
    public void update() {
        currencyRequest = new CurrencyRequest(this);
    }

    @Override
    public void update(IRepoCallbackData callback) {
        this.callback = callback;
        update();
    }

    @Override
    public void update(List<Valute> list) {
        course.clear();
        put("RUB", "1", 1, 0);

        for (Valute valute : list) {
            if (valute.code.equals("EUR") || valute.code.equals("USD")) {
                put(valute.code, valute.value, valute.nominal, 1);
            } else {
                put(valute.code, valute.value, valute.nominal, 2);
            }
        }
        if (callback != null) {
            callback.onFinish();
            callback = null;
        }
    }

    @Override
    public void onFinish() {
        if (callback != null) {
            callback.onFinish();
            callback = null;
        }
    }

    @Override
    public void log(String str) {
        LogMessage.toast(str);
    }

    @Override
    public void log(int str) {
        LogMessage.toast(str);
    }


    private void put(String code, String value, float nominal, float order) {
        float val = (Float.valueOf(value.replace(",", ".")) / nominal);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyDB.FeedEntry.KEY_NAME, code);
        contentValues.put(CurrencyDB.FeedEntry.KEY_VALUE, val);
        contentValues.put(CurrencyDB.FeedEntry.KEY_ORDER, order);

        currencyDB.createOrUpdate(code, contentValues);
        course.put(code, val);
    }

    @Override
    public void onDestroy() {
        if (currencyRequest != null) {
            currencyRequest.onDestroy();
        }
    }
}
