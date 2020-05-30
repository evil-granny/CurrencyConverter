package com.example.denys.currencyconverter.model;


public class ValuteGetted {
    public String code;
    public Float value;

    public ValuteGetted(String code, float value) {
        this.code = code;
        this.value = value;
    }

    public ValuteGetted(String code, String value) {
        this.code = code;
        this.value = Float.valueOf(value);
    }
}
