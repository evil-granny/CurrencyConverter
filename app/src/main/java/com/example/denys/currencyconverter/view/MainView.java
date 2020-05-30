package com.example.denys.currencyconverter.view;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

public interface MainView {
    void setValue(String value);

    EditText getMoneyIn();
    Spinner getSpinner1();
    Spinner getSpinner2();
    Button getUpdateBtn();
    ProgressBar getProgressBar();

}
