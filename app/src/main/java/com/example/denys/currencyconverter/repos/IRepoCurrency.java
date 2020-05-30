package com.example.denys.currencyconverter.repos;

public interface IRepoCurrency {
    float get(String key);

    String[] getCurrencies();

    void update();

    void update(IRepoCallbackData callback);

    void onDestroy();

}
