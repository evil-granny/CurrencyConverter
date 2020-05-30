package com.example.denys.currencyconverter.model.curse_value;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
class ValuteCurrency {
    @ElementList(entry = "Valute", inline = true)
    private List<Valute> valute;

    public List<Valute> getValute() {
        return valute;
    }

    public Valute getValute(String code) {
        for (Valute valute : valute) {
            if (valute.code.equals(code)) {
                return valute;
            }
        }
        return null;
    }
}
