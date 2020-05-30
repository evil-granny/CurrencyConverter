package com.example.denys.currencyconverter.presenter;

import com.example.denys.currencyconverter.view.MainView;

public interface IMainPresenter {
    void setView(MainView mainView);
    void update();
    void convert();
    void onDestroy();
}
